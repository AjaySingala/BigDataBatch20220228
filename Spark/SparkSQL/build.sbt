import Dependencies._

//ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / scalaVersion     := "2.11.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "SparkScala",
    libraryDependencies += scalaTest % Test
    // ,libraryDependencies += "org.apache.spark" %  "spark-sql_2.11" % "2.0.0"
    ,libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.3"
    ,libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.3"
    ,libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.4.8"

    // // For kafka.
    // ,libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.3.4"

  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
