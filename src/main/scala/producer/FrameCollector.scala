package producer

import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer
import webcam.{Dimensions, WebcamSource}

object FrameCollector {

  def main(args: Array[String]): Unit = { // set producer properties
    val loader = Thread.currentThread().getContextClassLoader;
    val properties: Properties = new Properties
    properties.load(loader.getResourceAsStream("kafka.properties"))
    properties.load(loader.getResourceAsStream("camera.properties"))
    // generate event
    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](properties)
    val webcamSource: WebcamSource = WebcamSource(properties.getProperty("camera.id"), Dimensions(640, 480))
    new FrameEventProducer(webcamSource, producer, properties.getProperty("topic")).produceFrames()
  }
}