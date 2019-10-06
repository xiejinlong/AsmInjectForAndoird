package com.inject.xie.injectplugin.asm.transform.adapter

import com.inject.xie.annotation.TryCatch
import com.inject.xie.injectplugin.asm.collector.CollectorContainer
import com.inject.xie.injectplugin.asm.collector.InjectMethodContainer
import com.inject.xie.injectplugin.asm.transform.MethodData
import com.inject.xie.injectplugin.uitls.LogUtil
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class AddTryCatchMethodAdapter(var methodData: MethodData, mv: MethodVisitor) :
    MethodVisitor(ASM5, mv) {

    var injectMethod: InjectMethodContainer? = null

    init {
        injectMethod = CollectorContainer.getMethodFromSource(methodData.generateAbsName())

    }

    var label1: Label? = null
    var label2: Label? = null
    var label3: Label? = null
    var isTryCatchBlock = false
    var invokeClassName: String? = null
    var invokeMethodName: String? = null

    override fun visitCode() {
        super.visitCode()
        injectMethod?.injectMethods?.forEach {
            if (TypeUtil.getDesc(TryCatch::class.java) != it.annotationDesc) {
                return
            }
            isTryCatchBlock = true
            invokeClassName = it.className
            invokeMethodName = it.name
            LogUtil.debug("innerMethodEnter")
            val label0 = Label()
            label1 = Label()
            label2 = Label()
            visitTryCatchBlock(label0, label1, label2, "java/lang/Exception")
            visitLabel(label0)
        }


    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        if (!isTryCatchBlock) {
            super.visitMaxs(maxStack, maxLocals)
            return
        }

        visitLabel(label1)
        label3 = Label()
        visitJumpInsn(GOTO, label3)
        visitLabel(label2)

        val frames = arrayOf<Any>(1)
        frames[0] = "java/lang/Exception"
        visitFrame(F_SAME1, 0, null, 1, frames)
        visitVarInsn(ASTORE, 1)
        try {
            visitVarInsn(ALOAD, 1)
            visitMethodInsn(
                INVOKESTATIC, invokeClassName,
                invokeMethodName, "(Ljava/lang/Exception;)V", false
            )
        } catch (e: Exception) {
            LogUtil.debug("invoke try catch method exception, ${e.message}")
        }
        visitLabel(label3)
        visitFrame(F_SAME, 0, null, 0, null)
        visitInsn(RETURN)
    }

}