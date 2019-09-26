package com.inject.xie.injectplugin.uitls.collection

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInput
import com.inject.xie.injectplugin.asm.collector.AnnotationCollector
import com.inject.xie.injectplugin.uitls.LogUtil
import org.gradle.api.file.Directory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

object InputCollectionHelper {



    fun generateInput(inputs: MutableList<TransformInput>) {
        //工程目录下，所有输入
        inputs.forEach { input ->
            input.jarInputs.forEach {
                generateFromJar(it)
            }
            input.directoryInputs.forEach {
                generateFromFile(it)
            }

        }
    }

    val TAG_END_JAR = ".jar"

    fun generateFromFile(input: DirectoryInput) {
        val file = input.file
        file.walk().iterator().forEach {
            if (needContinueCollection(it.name)) {
                AnnotationCollector().collectFromInputStream(FileInputStream(it))
            }
        }
    }

    fun generateFromJar(jarInput: JarInput) {
        LogUtil.debug("generateFromJar -> ${jarInput.name}")
        var jarInputName = jarInput.name
        var tempFile: File? = null
        if (jarInput.file.absolutePath.endsWith(TAG_END_JAR)) {
            val jarFile = JarFile(jarInput.file)
            var enumeration = jarFile.entries()
            //tempFile = File(jarInput.file.parent + File.separator + )
            while (enumeration.hasMoreElements()) {
                val entry = enumeration.nextElement()
                val zipEntry = ZipEntry(entry.name)
                val inputStream = jarFile.getInputStream(entry)
                if (needContinueCollection(entry.name)) {
                    AnnotationCollector().collectFromInputStream(inputStream)
                }
            }
        }
    }

    private fun needContinueCollection(fileName: String): Boolean {
        return fileName.endsWith(".class") && !fileName.contains("R\$")
                && !fileName.contains("R.class") && !fileName.contains("BuildConfig.class")
    }

}