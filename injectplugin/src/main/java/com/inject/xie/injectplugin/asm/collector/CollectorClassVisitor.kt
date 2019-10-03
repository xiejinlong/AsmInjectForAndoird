package com.inject.xie.injectplugin.asm.collector

import com.inject.xie.injectplugin.uitls.LogUtil
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class CollectorClassVisitor(classVisitor: ClassVisitor) :
    ClassVisitor(Opcodes.ASM5, classVisitor) {

    var injectClass = false
    var collectorClassName: String? = null

    companion object {
        const val INJECT_DESC = "Lcom/inject/xie/annotation/Inject;"
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        if (desc == INJECT_DESC) {
            injectClass = true
            LogUtil.debug("find desc is $desc")
        }
        return super.visitAnnotation(desc, visible)
    }

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        collectorClassName = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        desc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val injectMethod = InjectMethod().apply {
            this.className = collectorClassName
            this.access = access
            this.desc = desc
            this.signature = signature
            this.name = name
            this.exceptions = exceptions
        }

        return CollectorMethodVisitor(collectorClassName, injectMethod)
    }

}