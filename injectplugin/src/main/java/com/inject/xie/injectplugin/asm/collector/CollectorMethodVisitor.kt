package com.inject.xie.injectplugin.asm.collector

import com.inject.xie.injectplugin.uitls.LogUtil
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASM5

class CollectorMethodVisitor(var injectMethod: InjectMethod): MethodVisitor(ASM5) {

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor? {
        if (CollectorAnnotationUtil.shouldCollectorSource(desc)) {
            LogUtil.debug("start visit annotation.,...")
            return CollectorAnnotationVisitor(injectMethod.apply {
                this.annotationDesc = desc

            })
        }
        return super.visitAnnotation(desc, visible)
    }





}