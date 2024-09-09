import org.gradle.kotlin.dsl.`kotlin-dsl`

repositories {
   mavenLocal()
   mavenCentral()
   maven {
      url = uri("https://oss.sonatype.org/content/repositories/snapshots")
   }
}

plugins {
   `kotlin-dsl`
}
