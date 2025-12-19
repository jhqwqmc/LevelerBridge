plugins {
    id("java")
}

dependencies {
    compileOnly(libs.platform.paper.legacy)
    compileOnly(libs.bundles.hooks) { isTransitive = false }
    compileOnly(project(":api"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(17)
    dependsOn(tasks.clean)
}