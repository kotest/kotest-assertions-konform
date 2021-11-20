object Libs {

   const val kotlinVersion = "1.4.31"
   const val org = "io.kotest.extensions"

   object Kotest {
      private const val version = "4.6.3"
      const val AssertionsShared = "io.kotest:kotest-assertions-shared:$version"
      const val AssertionsCore = "io.kotest:kotest-assertions-core:$version"
      const val api = "io.kotest:kotest-framework-api:$version"
      const val junit5 = "io.kotest:kotest-runner-junit5-jvm:$version"
   }

   object Konform {
      const val Konform = "io.konform:konform:0.3.0"
      const val KonformJs = "io.konform:konform-js:0.3.0"
      const val KonformJvm = "io.konform:konform-jvm:0.3.0"
   }
}
