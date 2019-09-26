package com.inject.xie.injectplugin.asm.collector

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASM5

class CollectorMethodVisitor(var injectMethod: InjectMethod): MethodVisitor(ASM5) {

    var collectSource = false

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        if (CollectorAnnotationUtil.shouldCollectorSource(desc)) {
            return CollectorAnnotationVisitor(desc, visible)
        }
        return super.visitAnnotation(desc, visible)
    }





}