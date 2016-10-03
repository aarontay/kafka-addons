import sbt.Resolver

name := "kafka.connect.twitter.decahose"

version := "1.0"

organization := "com.landoop"

scalaVersion := "2.11.8"

val kafkaVersion = "0.10.0.0"
val json4sVersion = "3.4.1"
val avroVersion = "1.8.0"
val guavaVersion = "19.0"
val logbackVersion = "1.0.1"
val scalaTestVersion = "2.2.4"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "connect-api" % kafkaVersion, // provided
  "org.json4s" %% "json4s-native" % json4sVersion,
  "com.google.guava" % "guava" % guavaVersion,
  "org.apache.avro" % "avro" % avroVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion, // by using logback, we are actually using SLF4J
  "org.json4s" %% "json4s-native" % json4sVersion % "test",
  "org.json4s" %% "json4s-jackson" % json4sVersion % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)

resolvers += Resolver.mavenLocal

// Errors creating spark contexts otherwise
parallelExecution in Test := false