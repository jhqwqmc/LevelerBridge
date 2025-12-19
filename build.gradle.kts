plugins {
    id("java")
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") // paper
        maven("https://jitpack.io") // aureliumskills
        maven("https://repo.auxilor.io/repository/maven-public/") // eco
        maven("https://nexus.neetgames.com/repository/maven-releases/") // mcmmo
        maven("https://nexus.phoenixdevt.fr/repository/maven-public/") // mmocore
    }
}