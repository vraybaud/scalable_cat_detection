name := "webcam_frame_producer"

version := "0.1"

scalaVersion := "2.12.11"

libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % "2.6.6"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.6"

scalacOptions += "-target:jvm-1.8"
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.3.0"
libraryDependencies += "com.google.code.gson" % "gson" % "2.2"
libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5")

Compile / mainClass := Some("producer.FrameCollector")
