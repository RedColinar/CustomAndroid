package com.fooww.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PqApkPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create("packageConfig", PackageExtension)
        project.android.applicationVariants.all { variant ->
            variant.outputs.all {
                // variantName ThirdRelease，包含了 flavor
                def variantName = variant.name.capitalize()
                println "---" + variantName

                PackageTask task = project.tasks.create("assemble${variantName}Package", PackageTask.class)

                if (variantName.contains('Release')) {
                    task.group = 'fooww'
                    task.description = '冠名打包加固'
                }
                task.targetProject = project
                task.variant = variant
                task.doFirst {
                    println '>>>默认包全部生成，开始打加固签名渠道包。。。'
                }

                //依赖assemble，需要先编译出所有的该variant的包
                task.dependsOn variant.assemble

                println "appName>>>$it.outputFileName"
            }
        }
    }
}
