package com.inject.xie.injectplugin.uitls.collection

import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInput
import com.inject.xie.injectplugin.uitls.LogUtil
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

object InputCollectionHelper {



    fun generateInput(inputs: List<TransformInput>) {
        //工程目录下，所有输入
        inputs.forEach { input ->
            input.jarInputs.forEach {
                it.file
            }
            input.directoryInputs.forEach {

            }

        }
    }

    val TAG_END_JAR = ".jar"

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
                if (needContinueCollection(entry)) {

                }
            }
        }
    }

    private fun needContinueCollection(entry: ZipEntry): Boolean {
        return entry.name.endsWith(".class") && !entry.name.contains("R\$")
                && !entry.name.contains("R.class") && !entry.name.contains("BuildConfig.class")
    }

}