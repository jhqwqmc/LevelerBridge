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
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:-options"))
        dependsOn(tasks.clean)
    }

    val allowed = listOf("compileClasspath", "runtimeClasspath", "testCompileClasspath", "testRuntimeClasspath")
    plugins.withType<JavaPlugin> {
        configurations.matching {
            it.isCanBeResolved && it.name in allowed
        }.all {
            attributes {
                attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, Int.MAX_VALUE)
            }
        }
    }
}