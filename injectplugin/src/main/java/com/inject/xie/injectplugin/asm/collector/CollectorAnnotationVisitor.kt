package com.inject.xie.injectplugin.asm.collector

import com.inject.xie.injectplugin.uitls.LogUtil
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes.ASM5

class CollectorAnnotationVisitor(desc: String?, visible: Boolean): AnnotationVisitor(ASM5) {

    override fun visitAnnotation(name: String?, desc: String?): AnnotationVisitor {
        return super.visitAnnotation(name, desc)
    }

    override fun visit(p0: String?, p1: Any?) {
        LogUtil.debug("visit -> $p0 : $p1")
        super.visit(p0, p1)
    }

    override fun visitArray(p0: String?): AnnotationVisitor {
        LogUtil.debug("visitArray -> $p0")
        return super.visitArray(p0)
    }
}