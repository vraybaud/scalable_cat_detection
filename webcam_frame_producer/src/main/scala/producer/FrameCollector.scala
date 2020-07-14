package producer

import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer
import webcam.{Dimensions, WebcamSource}

object FrameCollector {

  def main(args: Array[String]): Unit = {
    val loader = Thread.currentThread().getContextClassLoader;
    val kafkaProperties: Properties = new Properties
    kafkaProperties.load(loader.getResourceAsStream("kafka.properties"))


    sys.env.find(pair => pair._1 == "TOPIC").foreach(pair => kafkaProperties.setProperty("topic", pair._2))
    sys.env.find(pair => pair._1 == "KAFKA_BROKER_URL").foreach(pair => kafkaProperties.setProperty("bootstrap.servers", pair._2))

    // generate event
    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](kafkaProperties)

    val cameraProperties: Properties = new Properties
    cameraProperties.load(loader.getResourceAsStream("camera.properties"))

    sys.env.find(pair => pair._1 == "CAMERA_ID").foreach(pair => cameraProperties.setProperty("camera.id", pair._2))

    val webcamSource: WebcamSource = WebcamSource(cameraProperties.getProperty("camera.id"), Dimensions(640, 480))
    new FrameEventProducer(webcamSource, producer, kafkaProperties.getProperty("topic")).produceFrames()
  }
}