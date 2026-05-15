plugins {
    id("levelerbridge-publish")
}

dependencies {
    compileOnly(libs.jetbrains.annotations)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "cn.gtemc"
            artifactId = "levelerbridge-api"
            version = rootProject.properties["project_version"].toString()
            from(components["java"])
            publication.applyCommonPom(this, "LevelerBridge-API")
        }
    }
}
