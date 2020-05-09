rootProject.name = "swagger-01"

include(
  "v2:java-spring:project",
  "v3:kotlin-spring-flux"
)

pluginManagement {
  repositories {
    maven { url = uri("https://repo.spring.io/milestone") }
    mavenCentral()
    gradlePluginPortal()
  }
}
