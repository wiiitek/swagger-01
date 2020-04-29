import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode

plugins {
  java
  id("org.springframework.boot") version "2.1.13.RELEASE"
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
  id("org.hidetake.swagger.generator") version "2.18.2"
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
}

dependencies {
  "swaggerCodegen"("io.swagger:swagger-codegen-cli:2.4.13")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")

  implementation("io.springfox:springfox-swagger2:2.9.2")
  implementation("io.springfox:springfox-swagger-ui:2.9.2")

  runtimeOnly("com.h2database:h2")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

/* ===============================================
*               Swagger Tasks
*  =============================================== */

val swaggerInputDir = "$rootDir/api-specification/v2/"
val generatedModule = "$projectDir/../generated"
val packagePrefix = "pl.kubiczak.test.swagger.v2.java.spring.generated."

swaggerSources.apply {

  create("ModelV1").apply {

    setInputFile(file(swaggerInputDir + "model-v1.yaml"))

    code(closureOf<GenerateSwaggerCode> {
      language = "spring"
      outputDir = file("$generatedModule/model-v1")
      components = listOf("models")
      additionalProperties = mapOf(
        "modelPackage" to packagePrefix + "model",
        "dateLibrary" to "java8"
      )
    })
  }
  create("TasksV1").apply {

    setInputFile(file(swaggerInputDir + "tasks-v1.yaml"))

    code(closureOf<GenerateSwaggerCode> {
      language = "spring"
      outputDir = file("$generatedModule/tasks-v1")
      components = kotlin.collections.listOf("apis")
      additionalProperties = kotlin.collections.mapOf(
        "modelPackage" to packagePrefix + "model",
        "apiPackage" to packagePrefix + "tasks",
        "dateLibrary" to "java8"
      )
    })
  }
  create("ImagesV1").apply {

    setInputFile(file(swaggerInputDir + "images-v1.yaml"))

    code(closureOf<GenerateSwaggerCode> {
      language = "spring"
      outputDir = file("$generatedModule/images-v1")
      components = kotlin.collections.listOf("apis")
      additionalProperties = kotlin.collections.mapOf(
        "modelPackage" to packagePrefix + "model",
        "apiPackage" to packagePrefix + "images",
        "dateLibrary" to "java8"
      )
    })
  }
}



tasks["generateSwaggerCodeTasksV1"].finalizedBy(
  tasks.create<Delete>("deleteTasksControllers") {
    delete(
      fileTree(generatedModule + "/tasks-v1").matching {
        include("**/*Controller.java")
      })
  }
)

tasks["generateSwaggerCodeImagesV1"].finalizedBy(
  tasks.create<Delete>("deleteImagesControllers") {
    delete(
      fileTree(generatedModule + "/images-v1").matching {
        include("**/*Controller.java")
      })
  }
)

sourceSets {
  main {
    java {
      srcDir("../generated/model-v1/src/main/java")
      srcDir("../generated/tasks-v1/src/main/java")
      srcDir("../generated/images-v1/src/main/java")
    }
  }
}

tasks["compileJava"].dependsOn("generateSwaggerCode")
// clean should also delete generated code

tasks.create<Delete>("cleanGeneratedSwaggerCode") {
  delete = setOf(
    files(generatedModule)
  )
}
tasks["clean"].dependsOn("cleanGeneratedSwaggerCode")
