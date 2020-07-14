package producer

import java.sql.Timestamp
import java.util.Base64

import akka.actor.ActorSystem
import com.google.gson.Gson
import org.apache.kafka.clients.producer.{Producer, ProducerRecord, RecordMetadata}
import org.bytedeco.javacpp.opencv_core.Mat
import org.slf4j.{Logger, LoggerFactory}
import webcam.{MatConverter, WebcamSource}

class FrameEventProducer(webcamSource: WebcamSource,
                         producer: Producer[String, String],
                         topic: String) {

  val logger: Logger = LoggerFactory.getLogger(classOf[FrameEventProducer])

  def produceFrames(): Unit = {
    implicit val system: ActorSystem = ActorSystem()
    val gson: Gson = new Gson
    val cameraId = webcamSource.cameraId
    webcamSource.toSource().map(MatConverter.toMat).runForeach({ mat: Mat =>
      val cols = mat.cols
      val rows = mat.rows
      val `type` = mat.`type`
      val data = new Array[Byte]((mat.total * mat.channels).asInstanceOf[Int])
      val timestamp = new Timestamp(System.currentTimeMillis).toString

      val frameEvent = FrameEvent(cameraId, timestamp, rows, cols, `type`, Base64.getEncoder.encodeToString(data))

      val json = gson.toJson(frameEvent)
      producer.send(
        new ProducerRecord[String, String](topic, cameraId, json),
        (metadata: RecordMetadata, exception: Exception) => {
          if (metadata != null) {
            logger.info("cameraId=" + cameraId + " partition=" + metadata.partition())
          }
          if (exception != null) {
            logger.error("error cameraId=" + cameraId, exception)
          }
        })

      logger.info("Generated events for cameraId=" + cameraId + " timestamp=" + timestamp)
    })
  }
}
