package com.inject.xie.injectplugin.asm.transform

import org.objectweb.asm.Attribute
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.ClassWriter.COMPUTE_MAXS
import java.io.InputStream

class ClassTransform {

    fun transform(inputStream: InputStream): ClassWriter {
        val classReader = ClassReader(inputStream)
        val classWriter = ClassWriter(classReader, COMPUTE_MAXS)
        val classAdapter = TransformClassVisitor(classWriter)
        classReader.accept(classAdapter, ClassReader.EXPAND_FRAMES)
        return classWriter
    }

}