package com.inject.xie.injectplugin.asm.transform

import com.inject.xie.injectplugin.asm.collector.CollectorContainer
import com.inject.xie.injectplugin.asm.collector.InjectMethod
import com.inject.xie.injectplugin.asm.collector.InjectMethodContainer
import com.inject.xie.injectplugin.uitls.LogUtil
import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

abstract class BaseMethodAdapter(var methodData: MethodData, mv: MethodVisitor)
    : AdviceAdapter(Opcodes.ASM5, mv, methodData.methodAccess,
    methodData.methodName, methodData.methodDes) {

    var sourceMethod: InjectMethodContainer? = null
    init {
        if(methodData.onwerName == "com/inject/xie/asminjectforandroid/MainActivity") {
            LogUtil.debug("total name is - > ${methodData.generateAbsName()}")
        }
        sourceMethod = CollectorContainer.getMethodFromSource(methodData.generateAbsName())
    }



    abstract fun targetHandle(): String
    abstract fun innerMethodExit(opcode: Int, method: InjectMethod)
    abstract fun innerMethodEnter(method: InjectMethod)

    final override fun onMethodExit(opcode: Int) {
        sourceMethod?.injectMethods?.forEach {
            if (targetHandle() == it.annotationDesc) {
                innerMethodExit(opcode, it)
            }
        }

    }

    final override fun onMethodEnter() {
        sourceMethod?.injectMethods?.forEach {
            if (targetHandle() == it.annotationDesc) {
                innerMethodEnter(it)
            }
        }
    }
}