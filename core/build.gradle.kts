dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-data-mongodb")

    api(project(":business"))
    api(project(":common"))
}
