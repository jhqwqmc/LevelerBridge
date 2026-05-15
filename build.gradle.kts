plugins {
    id("java")
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") // paper
        maven("https://jitpack.io") // aureliumskills
        maven("https://repo.auxilor.io/repository/maven-public/") // eco
        maven("https://nexus.neetgames.com/repository/maven-releases/") // mcmmo
        maven("https://nexus.phoenixdevt.fr/repository/maven-public/") // mmocore
    }

    extensions.create<PublishExtension>("publication")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
        withSourcesJar()
        disableAutoTargetJvm()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:-options"))
        options.release.set(8)
        dependsOn(tasks.clean)
    }
}