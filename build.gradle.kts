import io.papermc.paperweight.util.constants.*

plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("io.papermc.paperweight.patcher") version "1.2.0"
}

val spigotDecompiler: Configuration by configurations.creating

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        content {
            onlyForConfigurations(
                configurations.paperclip.name,
                spigotDecompiler.name,
            )
        }
    }
}

dependencies {
    remapper("net.fabricmc:tiny-remapper:0.8.1:fat")
    decompiler("net.minecraftforge:forgeflower:1.5.498.22")
    spigotDecompiler("io.papermc:patched-spigot-fernflower:0.1+build.4")
    paperclip("io.papermc:paperclip:2.0.1")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

subprojects {
    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
    }
    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

paperweight {
    serverProject.set(project(":Scissors-Server"))

    remapRepo.set("https://maven.fabricmc.net/")
    decompileRepo.set("https://files.minecraftforge.net/maven/")

    usePaperUpstream(providers.gradleProperty("paperRef")) {
        withPaperPatcher {
            apiPatchDir.set(layout.projectDirectory.dir("patches/api"))
            apiOutputDir.set(layout.projectDirectory.dir("Scissors-API"))

            serverPatchDir.set(layout.projectDirectory.dir("patches/server"))
            serverOutputDir.set(layout.projectDirectory.dir("Scissors-Server"))
        }
    }
}

tasks.generateDevelopmentBundle {
    apiCoordinates.set("me.totalfreedom.scissors:scissors-api")
    mojangApiCoordinates.set("io.papermc.paper:paper-mojangapi")
    libraryRepositories.set(
        listOf(
            "https://repo.maven.apache.org/maven2/",
            "https://repo.papermc.io/repository/maven-public/",
            "https://repo.scissors.gg/repository/scissors-snapshot/",
        )
    )
}

allprojects {
    // Publishing API:
    // ./gradlew :Scissors-API:publish[ToMavenLocal]
    publishing {
        repositories {
            maven {
                name = "scissors-snapshots"
                url = uri("https://repo.scissors.gg/repository/scissors-snapshot/")
                credentials {
                    username = System.getenv("scissorsUser")
                    password = System.getenv("scissorsPassword")
                }
            }
        }
    }
}