package com.fooww.plugin

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

    /** 版本信息 */
    def appVersion
    /** 360 登录用户名 */
    def username
    /** 360 登录密码 */
    def password
    /** keystore 路径 */
    def keystorePath
    /** 输出路径 */
    def outputPath
    /** keystore 密码 */
    def keystorePassword = ''
    /** keystore 别名 */
    def keystoreAlias = ''
    /** keystore 别名密码 */
    def keystoreAliasPassword = ''

    /** 项目根路径 */
    def projectPath
    /** app module 路径 */
    def appPath
    /** 360加固工具存放目录 */
    def jiaguRootPath
    def separator = File.separator
    /** windows 下要指定 java.exe */
    def binDir

    @TaskAction
    void packageTask() {
        String os = System.getProperties().getProperty("os.name").toLowerCase()
        println "操作系统: $os"

        if (os.contains("windows")) {
            projectPath = new File("").getAbsolutePath()
            jiaguRootPath = """${projectPath}${separator}buildSrc${separator}tools${separator}360${separator}jiagu"""
            jiaguRootPath = jiaguRootPath + separator + "windows"
            binDir = new File("${jiaguRootPath}${separator}java${separator}bin")
        } else if (os.contains("linux")) {
            appPath = new File("").getAbsolutePath()
            projectPath = appPath.substring(0, appPath.lastIndexOf(separator))
            jiaguRootPath = """${projectPath}${separator}buildSrc${separator}tools${separator}360${separator}jiagu"""
            jiaguRootPath = jiaguRootPath + separator + "linux"
        }

        appVersion = targetProject.packageConfig.appVersion
        username = targetProject.packageConfig.username
        password = targetProject.packageConfig.password
        outputPath = targetProject.packageConfig.outputPath
        keystorePath = targetProject.packageConfig.keystorePath
        keystorePassword = targetProject.packageConfig.keystorePassword
        keystoreAlias = targetProject.packageConfig.keystoreAlias
        keystoreAliasPassword = targetProject.packageConfig.keystoreAliasPassword

        if (isEmpty(username)
                || isEmpty(password)
                || isEmpty(keystorePath)
                || isEmpty(keystorePassword)
                || isEmpty(keystoreAlias)
                || isEmpty(keystoreAliasPassword)) {
            throw new GradleException("参数缺失，必要的参数为：\nusername\npassword\nkeystorePath\nkeystorePassword\nkeystoreAlias\nkeystoreAliasPassword")
        }
        projectPath = shiftSeparator(projectPath)
        jiaguRootPath = shiftSeparator(jiaguRootPath)
        keystorePath = shiftSeparator(keystorePath)
        outputPath = shiftSeparator(outputPath)

        outputPath = projectPath + outputPath.subSequence(1, outputPath.length())
        keystorePath = projectPath + keystorePath.subSequence(1, keystorePath.length())

        println "项目根目录为 $projectPath"
        println "加固工具目录为 $jiaguRootPath"
        println "密钥目录为 $keystorePath"
        println "输出目录为 $outputPath"

        def out = new StringBuilder()
        def err = new StringBuilder()
        // 登录 登录信息已经存到了 jiagu.db 中，不必每次加固都登陆
        def login = "java.exe -jar $jiaguRootPath${separator}jiagu.jar -login $username $password".execute(null, binDir)
        login.waitForProcessOutput(out, err)
        println "=== login >$out>$err"
        clearStringBuilder(out, err)

        // 导入签名
        def importSign = "java.exe -jar $jiaguRootPath${separator}jiagu.jar -importsign $keystorePath $keystorePassword $keystoreAlias $keystoreAliasPassword".execute(null, binDir)
        importSign.waitForProcessOutput(out, err)
        println "java.exe -jar $jiaguRootPath${separator}jiagu.jar -importsign $keystorePath $keystorePassword $keystoreAlias $keystoreAliasPassword"
        println "=== importSign >$out>$err"
        clearStringBuilder(out, err)

        variant.outputs.all {
            def apkFile = it.outputFile
            if (apkFile == null || !apkFile.exists()) {
                throw new GradleException("$apkFile doesn't exists!!!")
            }

            if (!new File(outputPath).exists()) {
                new File(outputPath).mkdirs()
                // "mkdir -p $outputPath".execute(null, binDir).waitForProcessOutput(out, err)
            }
            // "rm -rf outputPath".execute().waitForProcessOutput(out, err)
            // "chmod 777 $outputPath".execute(null, binDir).waitForProcessOutput(out, err)

            def cmd = "java.exe -jar ${jiaguRootPath}${separator}jiagu.jar -jiagu $apkFile $outputPath -autosign".execute(null, binDir)
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

    // 重命名文件
    static def renameFile(File file, String newName) {
        String originPath = file.getAbsolutePath()
        String originParent = originPath.substring(0, originPath.lastIndexOf(File.separator))
        println "renameFile: " + originPath + " newPath: " + newName
        file.renameTo(originParent + File.separator + newName)
    }

    static boolean isEmpty(CharSequence s) {
        if (s == null) {
            return true
        } else {
            return s.length() == 0
        }
    }

    static String shiftSeparator(String path) {
        String os = System.getProperties().getProperty("os.name").toLowerCase()
        if (os.contains("windows")) {
            path = path.replace("/", File.separator)
        } else if (os.contains("linux")) {
            path = path.replace("\\", File.separator)
        }
        return path
    }
}
