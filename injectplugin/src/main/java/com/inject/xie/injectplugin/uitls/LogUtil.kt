package com.inject.xie.injectplugin.uitls

import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileWriter

class LogUtil {



    companion object {
        var isDebug = true
        const val TAG = "Inject-Plugin"
        val logger = LoggerFactory.getLogger("Inject-Plugin")
        var fileWriter: FileWriter? = null

        fun initFileLogger(project: Project) {
            val outputPath = project.buildDir.absolutePath + "/outputs"
            val outputFile = File(outputPath)
            if (!outputFile.exists()) {
                FileUtils.forceMkdir(outputFile)
            }
            val loggerFilePath = "$outputPath/logs"
            val loggerFile = File(loggerFilePath)
            if (!outputFile.exists()) {
                FileUtils.forceMkdir(loggerFile)
            }
            val destFile = File("$loggerFilePath/inject-plugin.log")
            if (!destFile.exists()) {
                FileUtils.touch(destFile)
            }
            fileWriter = FileWriter(destFile)
        }

        private fun writeLogFile(message: String?) {
            fileWriter?.write(message)
            fileWriter?.write("\n")
            fileWriter?.flush()
        }

        @JvmStatic
        fun debug(message: String?) {
            if (isDebug) {
                println("$TAG: $message")
                logger.debug(message)

            }
            writeLogFile("$TAG, debug: $message")
        }

        @JvmStatic
        fun warn(message: String?) {
            if (isDebug) {
                println("$TAG: $message")
                logger.warn(message)
            }
            writeLogFile("$TAG, warn: $message")
        }

        @JvmStatic
        fun error(message: String?) {
            if (isDebug) {
                println("$TAG: $message")
                logger.error(message)
            }
            writeLogFile("$TAG, error: $message")
        }

        @JvmStatic
        fun info(message: String?) {
            if (isDebug) {
                println("$TAG: $message")
                logger.info(message)
            }
            writeLogFile("$TAG, info: $message")
        }
    }
}