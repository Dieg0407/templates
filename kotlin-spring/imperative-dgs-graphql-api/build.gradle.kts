import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.sonarqube") version "3.3"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
	kotlin("plugin.jpa") version "1.6.0"
}

group = "azoth.graphql"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
extra["kotlin.version"] = "1.4.31"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
	implementation("org.flywaydb:flyway-core")

	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
/*
tasks.create<Test>("unitTest") {
	useJUnitPlatform {
		includeTags("unit")
	}
}

tasks.create<Test>("integrationTest") {
	useJUnitPlatform {
		includeTags("integration")
	}
}
*/
tasks.test {
	val type = if (project.hasProperty("type"))
		project.property("type")
	else
		null

	if (type == null) {
		useJUnitPlatform()
	}
	else if (type == "unit") {
		useJUnitPlatform {
			includeTags("unit")
		}
	}
	else if (type == "integration") {
		useJUnitPlatform {
			includeTags("integration")
		}
	}
	else if (type == "smoke") {
		useJUnitPlatform {
			includeTags("smoke")
		}
	}
}