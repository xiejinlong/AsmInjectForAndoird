package inject.asm.entry

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.inject.xie.injectplugin.InjectExtension
import com.inject.xie.injectplugin.InjectTransform
import com.inject.xie.injectplugin.uitls.LogUtil
import org.gradle.api.Plugin
import org.gradle.api.Project

class InjectEntryExtension implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("codeInject", InjectExtension)

        if (!project.plugins.hasPlugin("com.android.application")) {
            LogUtil.info("this project does not android ...")
            System.out.println("this project does not android ...")
            return
        }

        if (project.plugins.hasPlugin(AppPlugin.class)) {
            AppExtension extension = project.getExtensions().getByType(AppExtension)
            extension.registerTransform(new InjectTransform(project))
            LogUtil.debug("now you register the transform ... ")

        }
    }



}