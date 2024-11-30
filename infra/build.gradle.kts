dependencies {
    implementation(project(":core"))

    implementation("io.jsonwebtoken:jjwt-api")
    runtimeOnly("io.jsonwebtoken:jjwt-impl")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
}
