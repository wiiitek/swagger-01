group = "pl.kubiczak.test.swagger"
version = "0.0.1-SNAPSHOT"

subprojects {
  repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "6.3"
  distributionType = Wrapper.DistributionType.ALL
}
