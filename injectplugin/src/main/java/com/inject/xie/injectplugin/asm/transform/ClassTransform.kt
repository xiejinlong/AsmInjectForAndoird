package com.inject.xie.injectplugin.asm.transform

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.ASM5
import java.io.InputStream

class ClassTransform {

    fun transform(inputStream: InputStream): ClassWriter {
        val classReader = ClassReader(inputStream)
        val classWriter = ClassWriter(classReader, ASM5)
        val classAdapter = AddTimerClassAdapter(classWriter)
        classReader.accept(classAdapter, 0)
        return classWriter
    }

}