pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "Scissors"

include("aswm-api", "aswm-core", "Scissors-API", "Scissors-Server")
