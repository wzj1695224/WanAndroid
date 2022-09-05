package com.zlx.plugin_lifecycle

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager


class RootTransform extends Transform {
    @Override
    String getName() {
        return "LifecyclePlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }


    @Override
    void transform(TransformInvocation transformInvocation) {
        println '--------------- LifecyclePlugin visit start --------------- '
        def startTime = System.currentTimeMillis()

        transformImpl(transformInvocation)

        def cost = (System.currentTimeMillis() - startTime) / 1000
        println '--------------- LifecyclePlugin visit end --------------- '
        println "LifecyclePlugin cost ： $cost s"
    }


    static void transformImpl(TransformInvocation invocation) {
        TransformOutputProvider outputProvider = invocation.outputProvider

        // clean previous build
        if (outputProvider != null)
            outputProvider.deleteAll()

        new ClassTransformWrapper(invocation)
                .filter(TransformConfig.&shouldTransform)
                .transform(TransformConfig.&doTransform)
    }

}
