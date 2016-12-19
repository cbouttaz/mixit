import com.github.jengelman.gradle.plugins.shadow.ShadowExtension
import org.junit.platform.gradle.plugin.EnginesExtension
import org.junit.platform.gradle.plugin.JUnitPlatformExtension
import java.util.concurrent.TimeUnit

buildscript {
	val kotlinVersion = "1.0.5-2"
	val junitPlatformVersion = "1.0.0-M3"
	extra["kotlinVersion"] = kotlinVersion
	extra["junitPlatformVersion"] = junitPlatformVersion

	repositories {
		mavenCentral()
		jcenter()
	}

	dependencies {
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
		classpath("com.github.jengelman.gradle.plugins:shadow:1.2.4")
		classpath("org.junit.platform:junit-platform-gradle-plugin:$junitPlatformVersion")
	}
}

apply {
	plugin("kotlin")
	plugin("application")
	plugin("org.junit.platform.gradle.plugin")
	plugin("com.github.johnrengelman.shadow")
}

version = "1.0.0-SNAPSHOT"

repositories {
	mavenCentral()
	maven { setUrl("https://repo.spring.io/snapshot") }
	maven { setUrl("https://repo.spring.io/milestone") }
}

configure<JavaPluginConvention> {
	setSourceCompatibility(1.8)
	setTargetCompatibility(1.8)
}

configure<ApplicationPluginConvention> {
	mainClassName = "mixit.MainKt"
}

configure<ShadowExtension> {
	version = null
}

configure<JUnitPlatformExtension> {
	engines {
		include("spek")
	}
}

configurations.all {
	it.resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}

val kotlinVersion = extra["kotlinVersion"] as String
val springVersion = "5.0.0.BUILD-SNAPSHOT"
val jacksonVersion = "2.8.4"
val reactorVersion = "3.0.4.BUILD-SNAPSHOT"
val junitPlatformVersion= extra["junitPlatformVersion"] as String
val junitJupiterVersion = "5.0.0-M3"
val spekVersion = "1.0.89"

dependencies {
	compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
	compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

	compile("org.springframework:spring-web-reactive:$springVersion")
	compile("com.github.jknack:handlebars:4.0.6")

	compile("io.projectreactor:reactor-core:$reactorVersion")
	compile("io.projectreactor.ipc:reactor-netty:0.6.0.BUILD-SNAPSHOT")
	testCompile("io.projectreactor.addons:reactor-test:$reactorVersion")

	compile("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
	compile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

	compile("commons-logging:commons-logging:1.2")
	compile("org.slf4j:slf4j-api:1.7.21")
	compile("ch.qos.logback:logback-classic:1.1.7")

	compile("org.springframework.data:spring-data-mongodb:2.0.0.BUILD-SNAPSHOT")
	compile("org.mongodb:mongodb-driver-reactivestreams:1.2.0")

	testCompile("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
	testCompile("org.junit.platform:junit-platform-runner:$junitPlatformVersion")
	testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
	testCompile("org.jetbrains.spek:spek-api:$spekVersion")
	testRuntime("org.jetbrains.spek:spek-junit-platform-engine:$spekVersion")
}

// extension for configuration
fun JUnitPlatformExtension.engines(setup: EnginesExtension.() -> Unit) {
	when (this) {
		is ExtensionAware -> extensions.getByType(EnginesExtension::class.java).setup()
		else -> throw Exception("${this::class} must be an insance of ExtensionAware")
	}
}

