package com.pq.pqplugin

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class PackageTask extends DefaultTask {
    @Input
    public Project targetProject

    @Input
    public ApplicationVariant variant

    def appVersion
    /** app module 路径 */
    def APP_PATH
    /** 360加固工具存放目录 */
    def JIAGU_ROOT_PATH
    /** 加固完成后存放apk的目录 */
    def JIAGU_COMPLETED_PATH
    /** 用来存放渠道包的目录 */
    def OUTPUT_CHANNEL_APK_PATH

    @TaskAction
    void packageTask() {
        String os = System.getProperties().getProperty("os.name")
        println "os is: $os"
        APP_PATH = new File("").getAbsolutePath()
        // buildSrc 模块的路径 ./buildSrc/
        JIAGU_ROOT_PATH = APP_PATH.replace("app", "buildSrc")

        println JIAGU_ROOT_PATH

        if (os.toLowerCase().startsWith('mac')) {
            JIAGU_ROOT_PATH += '/tools/360/mac/jiagu'
        } else if (os.toLowerCase().startsWith('linux')) {
            JIAGU_ROOT_PATH += '/tools/360/linux/jiagu'
        } else if (os.toLowerCase().startsWith('windows')) {
            JIAGU_ROOT_PATH += '/tools/360/windows/jiagu'
        } else {
            throw new RuntimeException('os not support')
        }

        appVersion = targetProject.packageConfig.appVersion
        JIAGU_COMPLETED_PATH = "$JIAGU_ROOT_PATH/completed"
        OUTPUT_CHANNEL_APK_PATH = "./build/outputs/jiagu"

        def out = new StringBuilder()
        def err = new StringBuilder()
        // 登录
        def login = "java -jar $JIAGU_ROOT_PATH/jiagu.jar -login 18017001513 teRqZyGXAPJfZj7".execute()
        login.waitForProcessOutput(out, err)
        println "=== login >$out>$err"
        clearStringBuilder(out, err)

        // 导入签名
        def importSign = "java -jar $JIAGU_ROOT_PATH/jiagu.jar -importsign $APP_PATH/key-store.jks abc123_ pq abc123_".execute()
        importSign.waitForProcessOutput(out, err)
        println "=== importSign >$out>$err"
        clearStringBuilder(out, err)

        variant.outputs.all {
            def apkFile = it.outputFile
            if (apkFile == null || !apkFile.exists()) {
                throw new GradleException("$apkFile doesn't exists!!!")
            }

            if (!new File(OUTPUT_CHANNEL_APK_PATH).exists()) {
                "mkdir -p $OUTPUT_CHANNEL_APK_PATH".execute().waitForProcessOutput(out, err)
            }
            // "rm -rf $OUTPUT_CHANNEL_APK_PATH".execute().waitForProcessOutput(out, err)
            "chmod 777 $OUTPUT_CHANNEL_APK_PATH".execute().waitForProcessOutput(out, err)

            def cmd = "java -jar $JIAGU_ROOT_PATH/jiagu.jar -jiagu $apkFile $OUTPUT_CHANNEL_APK_PATH -autosign".execute()
            cmd.in.eachLine {
                println "===>>>$it"
            }
        }
    }

    static void clearStringBuilder(StringBuilder out, StringBuilder err) {
        out.delete(0, out.length())
        err.delete(0, err.length())
    }

    //获取当前系统的时间
    static def releaseTime() {
        return new Date().format("MM.dd", TimeZone.getTimeZone("UTC"))
    }

    static def getProductName() {
        return "CustomAndroid"
    }
    // 重命名文件
    static def renameFile(File file, String newName) {
        String originPath = file.getAbsolutePath()
        String originParent = originPath.substring(0, originPath.lastIndexOf(File.separator))
        println "renameFile: " + originPath + " newPath: " + newName
        file.renameTo(originParent + File.separator + newName)
    }
}
