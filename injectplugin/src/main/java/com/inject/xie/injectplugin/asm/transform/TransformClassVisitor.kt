package com.inject.xie.injectplugin.asm.transform

import com.android.dx.dex.file.AnnotationUtils
import com.inject.xie.injectplugin.asm.collector.CollectorAnnotationUtil
import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.ASM5

class TransformClassVisitor(classVisitor: ClassVisitor):
    ClassVisitor(ASM5, classVisitor) {

    override fun visitMethod(
        access: Int,
        name: String?,
        desc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        return super.visitMethod(access, name, desc, signature, exceptions)
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

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitField(access: Int, name: String?, desc: String?, signature: String?, value: Any?): FieldVisitor {
        return super.visitField(access, name, desc, signature, value)
    }

    override fun visitEnd() {
        super.visitEnd()
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
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