
ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "jkugiya"
ThisBuild / organizationName := "jkugiya"

lazy val root = (project in file("."))
  .settings(
    name := "scala-slick-partial-results",
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    ),
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.4.0-M1",
      "com.h2database" % "h2" % "1.4.200"
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
