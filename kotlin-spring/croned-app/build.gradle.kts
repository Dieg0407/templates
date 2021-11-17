import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "promart.dispatch"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	implementation("org.springframework.boot:spring-boot-starter-webflux") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.5")
	runtimeOnly("com.oracle.database.jdbc:ojdbc8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
