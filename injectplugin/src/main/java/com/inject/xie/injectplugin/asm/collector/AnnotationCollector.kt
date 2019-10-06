package com.inject.xie.injectplugin.asm.collector

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassReader.EXPAND_FRAMES
import org.objectweb.asm.ClassWriter
import java.io.InputStream

class AnnotationCollector {

    fun collectFromInputStream(input: InputStream) {
        val classReader = ClassReader(input)
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        val collector = CollectorClassVisitor(classWriter)
        classReader.accept(collector, EXPAND_FRAMES)
    }
}