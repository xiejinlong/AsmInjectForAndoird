package com.inject.xie.injectplugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project
import com.inject.xie.injectplugin.asm.collector.AnnotationCollector
import com.inject.xie.injectplugin.asm.transform.ClassTransform
import com.inject.xie.injectplugin.asm.transform.InputTransformHelper
import com.inject.xie.injectplugin.uitls.LogUtil
import com.inject.xie.injectplugin.uitls.collection.InputCollectionHelper


class InjectTransform(var project: Project) : Transform() {


    companion object {
        const val NAME = "inject-asm"
    }


    override fun transform(transformInvocation: TransformInvocation?) {
        LogUtil.debug("xjl........1")
        super.transform(transformInvocation)
        transformInvocation ?: return
        val outputProvider = transformInvocation.outputProvider
        val inputs = transformInvocation.inputs
        val isIncremental = transformInvocation.isIncremental
        //如果非增量，则清空旧的输出内容
//        if (!isIncremental) {
//            outputProvider.deleteAll()
//        }

        //collector
        InputCollectionHelper.generateInput(inputs as MutableList<TransformInput>)
        //try inject
        LogUtil.debug("xjl...........2")
        InputTransformHelper.registerInvocation(transformInvocation)
        InputTransformHelper.generateInput(inputs)

    }


    override fun getName(): String {
        return NAME
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

}