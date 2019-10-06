package com.inject.xie.injectplugin.asm.collector

import com.inject.xie.injectplugin.uitls.LogUtil
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes.ASM5
import org.objectweb.asm.Type.getDescriptor
import java.lang.reflect.Array
import java.lang.reflect.Method

class CollectorAnnotationVisitor(var method: InjectMethod) : AnnotationVisitor(ASM5) {

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
        when (p0) {
            TYPE_AFTER -> {
                after = p1 as Boolean
            }
            TYPE_EXCEPTION -> {
                exceptionDesc = p1 as String?
            }
            TYPE_EXTEND -> {
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
            it?:throw IllegalArgumentException("target config name error...")
            val targetName = tranfromTargetName(it)
            CollectorContainer.put(targetName, method.apply {
                extend = this@CollectorAnnotationVisitor.extend
                after = this@CollectorAnnotationVisitor.after
                exceptionDesc = this@CollectorAnnotationVisitor.exceptionDesc
            })

            if (extend) {
                //同时添加父类
                splitAbsString(generateClassName(it), generateMethodName(it), generateMethodDesc(it))
            }

        }
        LogUtil.debug("CollectorAnnotationVisitor visitEnd...")
    }

    private fun generateClassName(name: String): String {
        val trimName = name.trim()
        val splitArray = trimName.split('|')
        if (splitArray.size != 3) {
            LogUtil.debug("error config .....")
        }
        val className = splitArray[0]
        val replaceClassName = className.replace('.', '/')
        return replaceClassName
    }

    private fun tranfromTargetName(name: String): String {
        val trimName = name.trim()
        val splitArray = trimName.split('|')
        if (splitArray.size != 3) {
            LogUtil.debug("error config .....")
        }
        val className = splitArray[0]
        val replaceClassName = className.replace('.', '/')
        val methodName = splitArray[1]
        val returnName = splitArray[2]
        return replaceClassName + "." + getMethodDescriptor(methodName, returnName)

    }

    private fun generateMethodName(name: String): String {
        val trimName = name.trim()
        val splitArray = trimName.split('|')
        if (splitArray.size != 3) {
            LogUtil.debug("error config .....")
        }
        val methodName = splitArray[1]
        val leftIndex = methodName.indexOf('(')
        return methodName.substring(0, leftIndex)
    }

    private fun generateMethodDesc(name: String): String {
        val trimName = name.trim()
        val splitArray = trimName.split('|')
        if (splitArray.size != 3) {
            LogUtil.debug("error config .....")
        }
        val methodName = splitArray[1]
        val leftIndex = methodName.indexOf('(')
        val rightIndex = methodName.indexOf(')')
        val buf = StringBuilder()
        buf.append('(')
        if (leftIndex == rightIndex - 1) {
            buf.append(')')
        } else {
            val parameterString = methodName.substring(leftIndex + 1, rightIndex)
            val parameterTypeSplitArray = parameterString.split(',')
            parameterTypeSplitArray.forEach {
                val clazz = ReflectUtil.getClass(it)
                getDescriptor(buf, clazz!!)
            }
        }
        buf.append(')')
        if (splitArray[2]!!.trim() == "void") {
            getDescriptor(buf, Void.TYPE)
        } else {
            getDescriptor(buf, ReflectUtil.getClass(splitArray[2])!!)
        }
        return buf.toString()

    }

    private fun getMethodDescriptor(methodName: String, returnName: String): String {
        val leftIndex = methodName.indexOf('(')
        val rightIndex = methodName.indexOf(')')
        val buf = StringBuilder()
        buf.append(methodName.substring(0, leftIndex))
        buf.append('(')
        if (leftIndex != rightIndex - 1) {

            val parameterString = methodName.substring(leftIndex + 1, rightIndex)
            val parameterTypeSplitArray = parameterString.split(',')
            parameterTypeSplitArray.forEach {
                val clazz = ReflectUtil.getClass(it)
                getDescriptor(buf, clazz!!)
            }
        }
        buf.append(')')
        if (returnName.trim() == "void") {
            getDescriptor(buf, Void.TYPE)
        } else {
            getDescriptor(buf, ReflectUtil.getClass(returnName)!!)
        }
        return buf.toString()
    }

    private fun getDescriptor(buf: StringBuilder, c: Class<*>) {
        var d = c
        while (true) {
            when {
                d.isPrimitive -> {
                    val car: Char = when (d) {
                        Integer.TYPE -> 'I'
                        Void.TYPE -> 'V'
                        java.lang.Boolean.TYPE -> 'Z'
                        java.lang.Byte.TYPE -> 'B'
                        Character.TYPE -> 'C'
                        java.lang.Short.TYPE -> 'S'
                        java.lang.Double.TYPE -> 'D'
                        java.lang.Float.TYPE -> 'F'

                        /* if (d == Long.TYPE) */
                        else -> 'J'
                    }
                    buf.append(car)
                    return
                }
                d.isArray -> {
                    buf.append('[')
                    d = d.componentType
                }
                else -> {
                    buf.append('L')
                    val name = d.name
                    val len = name.length
                    for (i in 0 until len) {
                        val car = name[i]
                        buf.append(if (car == '.') '/' else car)
                    }
                    buf.append(';')
                    return
                }
            }
        }
    }


    private fun splitAbsString(className: String, methodName: String,
                               methodDesc: String) {
        var superClass = ReflectUtil.getSuperClass(className.replace('/', '.'))

        while (ReflectUtil.containMethod(
                superClass,
                methodName, methodDesc
            )
        ) {
            CollectorContainer.put(
                generateSaveName(superClass!!.name, methodName, methodDesc),
                method.apply {
                    extend = this@CollectorAnnotationVisitor.extend
                    after = this@CollectorAnnotationVisitor.after
                    exceptionDesc = this@CollectorAnnotationVisitor.exceptionDesc
                })
            superClass = superClass.superclass
        }
    }

    private fun generateSaveName(className: String, methodName: String, methodDesc: String): String {
        return className.replace('.', '/') + "." + methodName + methodDesc
    }


}