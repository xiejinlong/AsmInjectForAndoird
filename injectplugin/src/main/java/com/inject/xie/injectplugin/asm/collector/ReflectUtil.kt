package com.inject.xie.injectplugin.asm.collector

import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.AppExtension
import com.inject.xie.injectplugin.uitls.collection.InputCollectionHelper
import org.gradle.api.Project
import org.objectweb.asm.Type
import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.net.URLClassLoader

object ReflectUtil {

    var classLoader: URLClassLoader? = null

    fun initClassLoader(transformInvocation: TransformInvocation?, project: Project) {
        transformInvocation?: return
        val url = mutableListOf<URL>()
        transformInvocation.referencedInputs.forEach {
            it.directoryInputs.forEach { directoryInput ->
                directoryInput.file.walk().iterator().forEach {
                    url.add(it.toURI().toURL())
                }
            }
            it.jarInputs.forEach { jarInput ->
                url.add(jarInput.file.toURI().toURL())
            }
        }
        transformInvocation.inputs.forEach { input ->
            input.directoryInputs.forEach { directoryInput ->
                directoryInput.file.walk().iterator().forEach {
                    url.add(it.toURI().toURL())
                }
            }
            input.jarInputs.forEach { jarInput ->
                url.add(jarInput.file.toURI().toURL())
            }
        }
        val appExtension = project.extensions.getByType(AppExtension::class.java)
        val androidJarPath = "${appExtension.sdkDirectory}/platforms/${appExtension.compileSdkVersion}/android.jar"
        url.add(File(androidJarPath).toURI().toURL())
        classLoader = URLClassLoader(url.toTypedArray())

    }

    fun getClass(absClassName: String): Class<*>? {
        return classLoader!!.loadClass(absClassName)
    }

    fun getSuperClass(absClassName: String): Class<*>? {
        return getClass(absClassName.replace('/', '.'))?.superclass
    }

    fun containMethod(clazz: Class<*>?, methodName: String, methodDesc: String): Boolean {
        clazz?.declaredMethods?.forEach {
            if (methodName == it.name) {
                if (methodDesc == Type.getMethodDescriptor(it)) {
                    return true
                }
            }

        }
        return false
    }

    fun containMethod(absClassName: String, methodName: String, methodDesc: String): Boolean {
        val clazz = getClass(absClassName)?.superclass
        clazz?.declaredMethods?.forEach {
            if (methodName == it.name) {
                if (methodDesc == Type.getMethodDescriptor(it)) {
                    return true
                }
            }

        }
        return false
    }

}