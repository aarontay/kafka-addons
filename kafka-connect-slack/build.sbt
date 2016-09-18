import sbt.Resolver

name := "kafka.connect.slack"

version := "1.0"

organization := "com.landoop"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.3"
val sparkVersion = "2.0.0"
val kafkaVersion = "0.10.0.0"
val guavaVersion = "19.0"
val slackBotVersion = "0.2.2"

libraryDependencies ++= Seq(
  "io.scalac" %% "slack-scala-bot-core" % "0.2.2"
    from "file:///./lib/slack-scala-bot-core_2.11-0.2.2.jar",
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "io.spray" %% "spray-json" % "1.3.1",
  "io.spray" %% "spray-client" % "1.3.1",
  "io.spray" %% "spray-can" % "1.3.2",
  "com.wandoulabs.akka" %% "spray-websocket" % "0.1.4",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "org.slf4j" % "slf4j-log4j12" % "1.7.5",
  "org.apache.kafka" % "connect-api" % kafkaVersion, // provided
  "com.twitter" % "hbc-twitter4j" % "2.2.0", // hosebird version
  "org.apache.avro" % "avro" % "1.8.0",
  "com.google.guava" % "guava" % guavaVersion,
  "org.slf4j" % "slf4j-simple" % "1.7.13",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

resolvers += Resolver.mavenLocal
