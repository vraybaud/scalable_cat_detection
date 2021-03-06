name := "cat_detection"

version := "0.1"

scalaVersion := "2.12.11"

scalacOptions += "-target:jvm-1.8"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.6"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.6"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.6"
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.3.0"
libraryDependencies += "ai.djl" % "api" % "0.6.0"
libraryDependencies += "ai.djl" % "repository" % "0.4.1"
// Using MXNet Engine
libraryDependencies += "ai.djl.mxnet" % "mxnet-model-zoo" % "0.6.0"
libraryDependencies += "ai.djl.mxnet" % "mxnet-native-auto" % "1.6.0"
libraryDependencies += "ai.djl.pytorch" % "pytorch-model-zoo" % "0.6.0"
libraryDependencies += "ai.djl.pytorch" % "pytorch-native-auto" % "1.5.0"

