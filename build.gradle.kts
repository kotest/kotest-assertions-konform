import org.gradle.api.tasks.testing.logging.TestExceptionFormat

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
   kotlin("multiplatform").version(Libs.kotlinVersion)
}

repositories {
   mavenLocal()
   mavenCentral()
   maven {
      url = uri("https://oss.sonatype.org/content/repositories/snapshots")
   }
}

group = Libs.org
version = Ci.version

kotlin {
   targets {
      jvm {
         compilations.all {
            kotlinOptions {
               jvmTarget = "1.8"
            }
         }
      }
      js {
         browser()
         nodejs()
      }
   }

   sourceSets {
      val commonMain by getting {
         dependencies {
            implementation(Libs.Kotest.AssertionsCore)
            implementation(Libs.Kotest.AssertionsShared)
            implementation(Libs.Konform.Konform)
         }
      }
      val jvmTest by getting {
         dependencies {
            implementation(Libs.Kotest.api)
            implementation(Libs.Kotest.junit5)
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

apply("./publish.gradle.kts")
