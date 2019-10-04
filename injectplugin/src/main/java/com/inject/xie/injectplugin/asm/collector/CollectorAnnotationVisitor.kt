package com.inject.xie.injectplugin.asm.collector

import com.inject.xie.injectplugin.uitls.LogUtil
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes.ASM5

class CollectorAnnotationVisitor(var method: InjectMethod): AnnotationVisitor(ASM5) {

    companion object {
        const val TYPE_AFTER = "after"
        const val TYPE_EXTEND = "extend"
        const val TYPE_EXCEPTION = "exceptionDesc"
    }

    private var after: Boolean = false
    private var exceptionDesc: String? = null
    private var targets: MutableList<String?> = mutableListOf()
    private var extend: Boolean = false

    override fun visit(p0: String?, p1: Any?) {
        LogUtil.debug("CollectorAnnotationVisitor visit -> $p0 : $p1")
        when(p0) {
            TYPE_AFTER -> {
                after = p1 as Boolean
            }
            TYPE_EXCEPTION -> {
                exceptionDesc = p1 as String?
            }
            TYPE_EXTEND  -> {
                this.extend = p1 as Boolean
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
                extend = this@CollectorAnnotationVisitor.extend
                after = this@CollectorAnnotationVisitor.after
                exceptionDesc = this@CollectorAnnotationVisitor.exceptionDesc
            })

            if (extend) {
                //同时添加父类
                splitAbsString(it)
            }

        }
        LogUtil.debug("CollectorAnnotationVisitor visitEnd...")
    }

    private fun splitAbsString(absName: String?) {
        absName?:return
        val splitArray = absName.split('.')
        val methodSplit = splitArray[1].split('(')
        var superClass = ReflectUtil.getSuperClass(splitArray[0].replace('/', '.'))

        while (ReflectUtil.containMethod(superClass,
                methodSplit[0], "(${methodSplit[1]}")) {
            CollectorContainer.put(genearateSaveName(superClass!!.name, methodSplit[0], "(${methodSplit[1]}"), method.apply {
                extend = this@CollectorAnnotationVisitor.extend
                after = this@CollectorAnnotationVisitor.after
                exceptionDesc = this@CollectorAnnotationVisitor.exceptionDesc
            })
            superClass = superClass.superclass
        }
    }

    private fun genearateSaveName(className: String, methodName: String, methodDesc: String): String {
        return className.replace('.', '/') +  "." + methodName + methodDesc
    }



}