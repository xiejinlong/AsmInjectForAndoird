package com.inject.xie.injectplugin.asm.transform

import com.android.build.api.transform.*
import com.android.utils.FileUtils
import com.inject.xie.injectplugin.asm.collector.AnnotationCollector
import com.inject.xie.injectplugin.uitls.LogUtil
import org.apache.commons.io.IOUtils
import org.gradle.api.file.Directory
import java.io.*
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

object InputTransformHelper {

    var transformInvocation: TransformInvocation? = null

    fun registerInvocation(transformInvocation: TransformInvocation) {
        this.transformInvocation = transformInvocation
    }

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

    private fun generateFromFile(input: DirectoryInput) {
        val file = input.file
        val dest = transformInvocation!!.outputProvider.getContentLocation(
            input.name,
            input.contentTypes,
            input.scopes,
            Format.DIRECTORY
        )
        file.walk().iterator().forEach {
            if (needContinueCollection(it.name)) {
                val source = ClassTransform().transform(FileInputStream(it)).toByteArray()
                val fileOutPutFile = it.parentFile.absolutePath + File.separator + it.name
                LogUtil.debug("fileOutPutFile is -> $fileOutPutFile")
                val fos =  FileOutputStream(
                    it.parentFile.absolutePath + File.separator + it.name)
                fos.write(source)
                fos.close()
            }

        }
        LogUtil.debug("copy out put file is -> $dest")
        org.apache.commons.io.FileUtils.forceMkdir(dest)
        copyFileOrDir(input.file, dest)



    }

    private fun generateFromJar(jarInput: JarInput) {
        LogUtil.debug("generateFromJar -> ${jarInput.name}")
        var jarInputName = jarInput.name
        val tmpFile =  File(jarInput.file.parent + File.separator + "classes_temp.jar")
        //避免上次的缓存被重复插入
        if (tmpFile.exists()) {
            tmpFile.delete()
        }
        val jarFile = JarFile(jarInput.file)
        val jarOutputStream = JarOutputStream(FileOutputStream(tmpFile))
        if (jarInput.file.absolutePath.endsWith(TAG_END_JAR)) {

            var enumeration = jarFile.entries()
            //tempFile = File(jarInput.file.parent + File.separator + )

            while (enumeration.hasMoreElements()) {

                val entry = enumeration.nextElement()
                val zipEntry = ZipEntry(entry.name)
                jarOutputStream.putNextEntry(zipEntry)
                val inputStream = jarFile.getInputStream(entry)
                if (needContinueCollection(entry.name)) {
                    jarOutputStream.write(ClassTransform().transform(inputStream).toByteArray())
                } else {
                    jarOutputStream.write(IOUtils.toByteArray(inputStream))
                }
                jarOutputStream.closeEntry()
            }

        }
        //重命名输出文件（同目录 copyFile 会冲突）
        var jarName = jarInput.name
        var md5Name = jarInput.file.hashCode()
        if(jarName.endsWith(".jar")){
            jarName = jarName.substring(0, jarName.length - 4)
        }
        jarOutputStream.close()
        jarFile.close()
        val dest = transformInvocation!!.outputProvider.getContentLocation(
            jarName + md5Name,
            jarInput.contentTypes,
            jarInput.scopes,
            Format.JAR
        )

        copyFileOrDir(tmpFile, dest)

    }

    private fun needContinueCollection(fileName: String): Boolean {
        return fileName.endsWith(".class") && !fileName.contains("R\$")
                && !fileName.contains("R.class") && !fileName.contains("BuildConfig.class")
    }


    @Throws(IOException::class)
    fun copyFileOrDir(src: File, dest: File) {
        if (src.isFile) {
            org.apache.commons.io.FileUtils.copyFile(src, dest)
        } else if (src.isDirectory) {
            org.apache.commons.io.FileUtils.copyDirectory(src, dest)
        }
    }


}