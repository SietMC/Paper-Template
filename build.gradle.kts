import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private val paperVersion = Dependency.PAPER_VERSION
plugins {
    kotlin("jvm") version Dependency.KOTLIN_VERSION
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}
repositories {
    mavenCentral()
    Dependency.Repositories.forEach { maven(it) }
}
dependencies {
    paperweight.paperDevBundle("${paperVersion}-R0.1-SNAPSHOT")
    Dependency.apply {
        Libraries.forEach { implementation(it) }
        PaperLibraries.forEach { paperLibrary(it) }
    }
}
paper {
    apiVersion = Dependency.PAPER_API_VERSION
    name = rootProject.name
    version = project.version.toString()
    val title = name!!.replace("-", "")
    "${group}.${title.lowercase()}.plugin.".apply {
        main = "${this}${title}Plugin"
        loader = "${this}loader.${title}PluginLoader"
    }
    generateLibrariesJson = true
}
tasks {
    withType<KotlinCompile> { kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString() }
    processResources { filteringCharset = Charsets.UTF_8.toString() }
    runServer {
        minecraftVersion(paperVersion)
        jvmArgs = arrayListOf("-Dcom.mojang.eula.agree=true")
    }
}
