package com.zlx.plugin_lifecycle

import com.android.build.api.transform.*
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.apache.http.util.Asserts
import org.objectweb.asm.ClassReader

import java.util.function.BiFunction
import java.util.function.Predicate
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry


/**
 * Do transform for all classes in TransformInvocation
 */
class ClassTransformWrapper {

    private TransformInvocation invocation
    private Predicate<String> filter


    ClassTransformWrapper(TransformInvocation invocation) {
        this.invocation = invocation
    }


    ClassTransformWrapper filter(Predicate<String> filter) {
        this.filter = filter
        return this
    }


    void transform(BiFunction<String, ClassReader, byte[]> transform) {
        Predicate<String> filter = this.filter != null ? this.filter : { true }

        doTransform(this.invocation, filter) { name, cr ->
            println '----------- do transform for <' + name + '> -----------'
            return transform.apply(name, cr)
        }
    }




    private static void doTransform(TransformInvocation invocation,
                                    Predicate<String> filter,
                                    BiFunction<String, ClassReader, byte[]> transform) {
        invocation.inputs.each {
            // handle directory
            it.directoryInputs.each {DirectoryInput directory ->
                doTransformInDirectory(invocation, directory, filter, transform)
            }

            // handle jar
            it.jarInputs.each {JarInput jar ->
                doTransformInJar(invocation, jar, filter, transform)
            }
        }
    }


    private static void doTransformInDirectory(TransformInvocation invocation,
                                               DirectoryInput directory,
                                               Predicate<String> filter,
                                               BiFunction<String, ClassReader, byte[]> transform) {
        Asserts.check(directory.file.isDirectory(), "Something wrong")
        TransformOutputProvider outputProvider = invocation.outputProvider

        directory.file.eachFileRecurse {File file ->
            def name = file.name

            // filter
            if (!filter.test(name))
                return

            // do transform
            ClassReader cr = new ClassReader(file.bytes)
            byte[] code = transform.apply(name, cr)
            if (code == null)
                return

            // override write
            FileOutputStream fos = new FileOutputStream(file.absolutePath)
            fos.write(code)
            fos.close()
        }

        // 处理完输入文件之后，要把输出给下一个任务
        def output = outputProvider.getContentLocation(directory.name, directory.contentTypes, directory.scopes, Format.DIRECTORY)
        FileUtils.copyDirectory(directory.file, output)
    }


    private static void doTransformInJar(TransformInvocation invocation,
                                         JarInput jar,
                                         Predicate<String> filter,
                                         BiFunction<String, ClassReader, byte[]> transform) {
        Asserts.check(jar.file.getAbsolutePath().endsWith(".jar"), "Something wrong")
        TransformOutputProvider outputProvider = invocation.outputProvider

        // create a temp file for write
        File tmpFile = new File(jar.file.getParent() + File.separator + "classes_temp.jar")
        if (tmpFile.exists()) {
            tmpFile.delete()
        }
        JarOutputStream jos = new JarOutputStream(new FileOutputStream(tmpFile))

        // iterate all entries
        JarFile jarFile = new JarFile(jar.file)
        for (JarEntry entry : jarFile.entries()) {
            def name = entry.name
            def is = jarFile.getInputStream(entry)

            // transform class & get bytes
            byte[] code
            if (filter.test(name)) {
                def cr = new ClassReader(is)
                code = transform.apply(name, cr)
            } else
                code = IOUtils.toByteArray(is)

            // write into temp jar file
            jos.putNextEntry(new ZipEntry(name))
            jos.write(code)
            jos.closeEntry()
        }

        // clean
        jos.close()
        jarFile.close()

        // 处理完输入文件之后，要把输出给下一个任务
        def destName = jar.name + '.' + DigestUtils.md5Hex(jar.file.absolutePath)
        def dest = outputProvider.getContentLocation(destName,
                jar.contentTypes, jar.scopes, Format.JAR)
        FileUtils.copyFile(tmpFile, dest)
        tmpFile.delete()
    }

}