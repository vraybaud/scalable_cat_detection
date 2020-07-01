package detection.djl

import java.awt.image.BufferedImage

import ai.djl.Application
import ai.djl.modality.Classifications
import ai.djl.repository.zoo.{Criteria, ModelZoo}
import ai.djl.training.util.ProgressBar
import javax.imageio.ImageIO
import org.apache.spark.{SparkConf, SparkContext}

object CatDetection {

  val conf = new SparkConf()
    .setAppName("Cat Detection")
    .setMaster("local[*]")
    .setExecutorEnv("MXNET_ENGINE_TYPE", "NaiveEngine")
  val sc = new SparkContext(conf)

  val partitions = sc.binaryFiles("images/*")

  // Start assign work for each worker node
  val result = partitions.mapPartitions(partition => {
    // before classification
    val criteria = Criteria.builder
      .optApplication(Application.CV.OBJECT_DETECTION)
      .setTypes(classOf[BufferedImage], classOf[Classifications])
      .optFilter("dataset", "imagenet")
      .optFilter("layers", "50")
      .optProgress(new ProgressBar)
      .build
    val model = ModelZoo.loadModel(criteria)
    val predictor = model.newPredictor()

    //TODO get kafka event and process it
    partition
  })
}
