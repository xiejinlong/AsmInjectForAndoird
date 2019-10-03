package com.inject.xie.injectplugin.asm.transform

import org.objectweb.asm.MethodVisitor

class MethodVisitorChain {
    companion object {
        fun handleVisitor(methodData: MethodData, mv: MethodVisitor): MethodVisitor {
            val addTimer = AddTimerMethodAdapter(methodData, mv)
            val tryCatch = AddTryCatchMethodAdapter(methodData, addTimer)
            val replace = ReplaceMethodAdapter(methodData, tryCatch)
            return replace
        }
    }

}