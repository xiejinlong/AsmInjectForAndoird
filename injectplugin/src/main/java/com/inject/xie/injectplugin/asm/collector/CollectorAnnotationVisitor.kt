package com.inject.xie.injectplugin.asm.collector

import com.inject.xie.injectplugin.uitls.LogUtil
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes.ASM5

class CollectorAnnotationVisitor(var method: InjectMethod): AnnotationVisitor(ASM5) {

    private var after: Boolean = false
    private var exceptionDesc: String? = null
    private var targets: MutableList<String?> = mutableListOf()

    override fun visit(p0: String?, p1: Any?) {
        LogUtil.debug("CollectorAnnotationVisitor visit -> $p0 : $p1")
        when(p0) {
            "after" -> {
                after = p1 as Boolean
            }
            "exceptionDesc" -> {
                exceptionDesc = p1 as String?
            }
        }
        super.visit(p0, p1)
    }

    override fun visitArray(p0: String?): AnnotationVisitor? {
        LogUtil.debug("CollectorAnnotationVisitor, visitArray -> $p0")
        return CollectorArrayAnnotationVisitor(p0, targets)
    }

    override fun visitEnd() {
        super.visitEnd()
        //注解解析在这里结束

        targets.forEach {
            CollectorContainer.put(it, method.apply {
                after = this@CollectorAnnotationVisitor.after
                exceptionDesc = this@CollectorAnnotationVisitor.exceptionDesc
            })

        }
        LogUtil.debug("CollectorAnnotationVisitor visitEnd...")
    }
}