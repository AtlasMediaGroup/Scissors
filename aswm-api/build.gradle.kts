plugins {
    `java-library`
     `maven-publish`
    signing
}

dependencies {
    api("com.flowpowered:flow-nbt:2.0.2")
    api("org.jetbrains:annotations:23.0.0")

    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
    withJavadocJar()
}
