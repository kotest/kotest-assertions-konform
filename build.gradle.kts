import org.gradle.api.tasks.testing.logging.TestExceptionFormat

buildscript {
   repositories {
      jcenter()
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
            implementation(Libs.Kotest.AssertionsShared)
            implementation(Libs.Konform.Konform)
         }
      }
      val jvmTest by getting {
         dependencies {
            implementation(Libs.Kotest.junit5)
            implementation(Libs.Kotest.AssertionsCore)
            implementation("io.kotest:kotest-runner-junit5-jvm:4.6.3")
         }
      }
   }
}

allprojects {

   group = Libs.org
   version = Ci.version

   tasks.named<Test>("test") {
      useJUnitPlatform()
      testLogging {
         showExceptions = true
         showStandardStreams = true
         exceptionFormat = TestExceptionFormat.FULL
      }
   }

   tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
      kotlinOptions.jvmTarget = "1.8"
   }

   repositories {
      mavenLocal()
      mavenCentral()
      maven {
         url = uri("https://oss.sonatype.org/content/repositories/snapshots")
      }
   }
}

apply("./publish.gradle.kts")
