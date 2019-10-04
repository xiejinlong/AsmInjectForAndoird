package com.inject.xie.injectplugin.asm.transform

import com.inject.xie.annotation.Inject
import com.inject.xie.injectplugin.uitls.LogUtil
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.ASM5

class TransformClassVisitor(classVisitor: ClassVisitor):
    ClassVisitor(ASM5, classVisitor) {

    var owner: String? = null
    var isInterface: Boolean = false
    var isInjectClass = false

    override fun visitMethod(
        access: Int,
        name: String?,
        desc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val mv = super.visitMethod(access, name, desc, signature, exceptions)
        if (isInjectClass) {
            LogUtil.debug("this class is inject class, just continue")
            return mv
        }
        return MethodVisitorChain.handleVisitor(MethodData().apply {
            onwerName = owner
            methodName = name
            methodDes = desc
            methodAccess = access
        }, mv)
    }

    override fun visitInnerClass(name: String?, outerName: String?, innerName: String?, access: Int) {
        super.visitInnerClass(name, outerName, innerName, access)
    }

    override fun visitSource(source: String?, debug: String?) {
        super.visitSource(source, debug)
    }

    override fun visitOuterClass(owner: String?, name: String?, desc: String?) {
        super.visitOuterClass(owner, name, desc)
    }

    override fun visit(p0: Int, p1: Int, p2: String?, p3: String?, p4: String?, p5: Array<out String>?) {
        this.owner = p2
        this.isInterface = p1 and Opcodes.ACC_INTERFACE != 0
        super.visit(p0, p1, p2, p3, p4,p5)
    }

    override fun visitField(access: Int, name: String?, desc: String?, signature: String?, value: Any?): FieldVisitor {
        return super.visitField(access, name, desc, signature, value)
    }

    override fun visitEnd() {
        super.visitEnd()
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        isInjectClass = desc == TypeUtil.getDesc(Inject::class.java)
        return super.visitAnnotation(desc, visible)
    }

    override fun visitTypeAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        desc: String?,
        visible: Boolean
    ): AnnotationVisitor {
        return super.visitTypeAnnotation(typeRef, typePath, desc, visible)
    }

    override fun visitAttribute(attr: Attribute?) {
        super.visitAttribute(attr)
    }
}