dependencies {
    compileOnly(libs.platform.paper)
    compileOnly(libs.bundles.hooks) { isTransitive = false }
    compileOnly(files("${project.rootDir}/libs/AureliumSkills-api.jar"))
    compileOnly(project(":api"))
}
