plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

tasks.matching { it.name == "kotlinWasmNodeJsSetup" || it.name == "kotlinNodeJsSetup" }.configureEach {
    doLast {
        val baseDir = java.io.File("/data/data/com.termux/files/home/.gradle/nodejs")
        if (baseDir.exists() && baseDir.isDirectory) {
            baseDir.listFiles()?.forEach { dir ->
                if (dir.isDirectory && dir.name.startsWith("node-v")) {
                    val nodeExec = java.io.File(dir, "bin/node")
                    if (nodeExec.exists()) {
                        nodeExec.delete()
                    }
                    try {
                        java.nio.file.Files.createSymbolicLink(
                            nodeExec.toPath(),
                            java.io.File("/data/data/com.termux/files/usr/bin/node").toPath()
                        )
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }
}