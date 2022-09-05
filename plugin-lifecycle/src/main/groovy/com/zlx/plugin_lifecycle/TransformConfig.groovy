package com.zlx.plugin_lifecycle

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES


class TransformConfig {

    static Set<String> LIFECYCLE_CLASS = [
            "androidx/fragment/app/FragmentActivity.class",
            "androidx/fragment/app/Fragment.class",
            "com/zlx/module_home/fragment/HomeFg.class",
    ]


    static boolean shouldTransform(String name) {
        return LIFECYCLE_CLASS.contains(name)
    }


    static byte[] doTransform(String name, ClassReader reader) {
        if (!LIFECYCLE_CLASS.contains(name))
            return null;

        def cw = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
        def visitor = new LifecycleClassVisitor(cw)
        reader.accept(visitor, EXPAND_FRAMES)
        return cw.toByteArray()
    }

}
