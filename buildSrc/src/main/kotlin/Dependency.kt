object Dependency {
    const val KOTLIN_VERSION = "1.9.22"
    const val PAPER_VERSION = "1.20.4"
    const val PAPER_API_VERSION = "1.20"
    private const val COMMANDAPI_VERSION = "9.3.0"
    private const val STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${KOTLIN_VERSION}"
    private const val REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${KOTLIN_VERSION}"
    private const val COMMANDAPI_BUKKIT_SHADE = "dev.jorel:commandapi-bukkit-shade:${COMMANDAPI_VERSION}"
    private const val COMMANDAPI_BUKKIT_KOTLIN = "dev.jorel:commandapi-bukkit-kotlin:${COMMANDAPI_VERSION}"
    val Repositories = arrayListOf("https://repo.codemc.org/repository/maven-public")
    val Libraries = arrayListOf(
        STDLIB,
        REFLECT,
        COMMANDAPI_BUKKIT_SHADE,
        COMMANDAPI_BUKKIT_KOTLIN
    )
    val PaperLibraries = arrayListOf(
        STDLIB,
        REFLECT,
        COMMANDAPI_BUKKIT_SHADE
    )
}
