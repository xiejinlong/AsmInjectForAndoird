package com.inject.xie.injectplugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project
import com.android.build.api.transform.TransformInput
import com.android.build.gradle.tasks.factory.AbstractCompilesUtil.isIncremental
import com.android.build.api.transform.TransformOutputProvider
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
        if (!isIncremental) {
            outputProvider.deleteAll()
        }

        //collector
        InputCollectionHelper.generateInput(inputs as MutableList<TransformInput>)
        //try inject
        LogUtil.debug("xjl...........2")
    }


    override fun getName(): String {
        return NAME
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        return true
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

}