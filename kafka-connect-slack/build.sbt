//import sbt.Resolver
//
//name := "kafka.connect.slack"
//
//version := "1.0"
//
//organization := "com.landoop"
//
//scalaVersion := "2.11.8"
//
//val sparkVersion = "2.0.0"
//val kafkaVersion = "0.10.0.0"
//val guavaVersion = "19.0"
//
//libraryDependencies ++= Seq(
//  "org.apache.kafka" % "connect-api" % kafkaVersion, // provided
//  "com.twitter" % "hbc-twitter4j" % "2.2.0", // hosebird version
//  "org.apache.avro" % "avro" % "1.8.0",
//  "com.google.guava" % "guava" % guavaVersion,
//  "org.slf4j" % "slf4j-simple" % "1.7.13",
//  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
//)
//
//resolvers += Resolver.mavenLocal
//
//// Errors creating spark contexts otherwise
//parallelExecution in Test := false