package com.inject.xie.injectplugin.asm.collector

import com.inject.xie.injectplugin.uitls.LogUtil
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes.ASM5

class CollectorArrayAnnotationVisitor(var arrayName: String?,
                                      var targets: MutableList<String?>): AnnotationVisitor(ASM5) {

    companion object {
        const val ARRAY_TAG = "target"
    }

    override fun visitAnnotation(p0: String?, p1: String?): AnnotationVisitor {
        LogUtil.debug("visitAnnotation in array annotation -> $p0, $p1")
        return super.visitAnnotation(p0, p1)
    }

    override fun visit(p0: String?, p1: Any?) {
        LogUtil.debug("visit in array annotation -> $p0, $p1")
        if (continueCollector()) {
            targets.add(p1 as? String)
        }
        super.visit(p0, p1)
    }
    private fun continueCollector(): Boolean {
        return when(arrayName) {
            ARRAY_TAG -> true
            else -> false
        }
    }
}