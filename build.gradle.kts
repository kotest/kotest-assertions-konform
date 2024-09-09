import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

buildscript {
   repositories {
      mavenCentral()
      maven {
         url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
      }
      maven {
         url = uri("https://plugins.gradle.org/m2/")
      }
   }
}

plugins {
   java
   `java-library`
   signing
   `maven-publish`
   alias(libs.plugins.kotlin.multiplatform)
   alias(libs.plugins.kotest.multiplatform)
}

repositories {
   mavenLocal()
   mavenCentral()
   maven {
      url = uri("https://oss.sonatype.org/content/repositories/snapshots")
   }
}

group = "io.kotest.extensions"
version = Ci.version

kotlin {

   sourceSets.all {
      languageSettings {
         apiVersion = libs.versions.kotlinApiTarget.get()
         languageVersion = libs.versions.kotlinApiTarget.get()
      }
   }
   jvmToolchain {
      languageVersion.set(JavaLanguageVersion.of(libs.versions.gradleDaemonJvm.get()))
   }

   jvm {
      @OptIn(ExperimentalKotlinGradlePluginApi::class)
      compilerOptions {
         jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvmMinTarget.get()))
      }
   }
   js(IR) {
      browser()
      nodejs()
   }

   sourceSets {
      val commonMain by getting {
         dependencies {
            implementation(libs.kotest.assertions.core)
            implementation(libs.konform)
         }
      }
      val commonTest by getting {
         dependencies {
            implementation(libs.kotest.framework.engine)
         }
      }
      val jvmTest by getting {
         dependencies {
            implementation(libs.kotest.runner.junit5)
         }
      }
   }
}

tasks.named<Test>("jvmTest") {
   useJUnitPlatform()
   testLogging {
      showExceptions = true
      showStandardStreams = true
      exceptionFormat = TestExceptionFormat.FULL
   }
}

apply(from = "./publish-mpp.gradle.kts")
