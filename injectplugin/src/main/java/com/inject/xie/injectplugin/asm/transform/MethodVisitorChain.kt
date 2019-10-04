package com.inject.xie.injectplugin.asm.transform

import com.inject.xie.injectplugin.asm.transform.adapter.AddTimerMethodAdapter
import com.inject.xie.injectplugin.asm.transform.adapter.AddTryCatchMethodAdapter
import com.inject.xie.injectplugin.asm.transform.adapter.InjectMethodAdapter
import com.inject.xie.injectplugin.asm.transform.adapter.ReplaceMethodAdapter
import org.objectweb.asm.MethodVisitor

class MethodVisitorChain {
    companion object {
        fun handleVisitor(methodData: MethodData, mv: MethodVisitor): MethodVisitor {
            val addTimer = AddTimerMethodAdapter(methodData, mv)
            val tryCatch =
                AddTryCatchMethodAdapter(methodData, addTimer)
            val replace = ReplaceMethodAdapter(methodData, tryCatch)
            val inject = InjectMethodAdapter(methodData, replace)
            return inject
        }
    }

}