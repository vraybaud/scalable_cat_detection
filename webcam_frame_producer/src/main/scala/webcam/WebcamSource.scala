package webcam

import akka.NotUsed
import akka.stream.scaladsl.Source
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.stream.{Attributes, Outlet, SourceShape}
import org.bytedeco.javacv.{Frame, FrameGrabber}

/**
 * Actor that backs the Akka Stream source
 */
case class WebcamSource(cameraId: String, dimensions: Dimensions) extends GraphStage[SourceShape[Frame]] {

  val out: Outlet[Frame] = Outlet("FrameSource")

  lazy val frameGrabber: FrameGrabber = FrameGrabberBuilder.build(cameraId, dimensions)

  // Define the shape of this stage, which is SourceShape with the port we defined above
  override val shape: SourceShape[Frame] = SourceShape(out)

  // This is where the actual (possibly stateful) logic will live
  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {

    setHandler(out, new OutHandler {
      override def onPull(): Unit = {
        push(out, grabFrame())
      }
    })

    private def grabFrame(): Frame = {
      frameGrabber.grab()
    }
  }

  def toSource(): Source[Frame, NotUsed] = {
    Source.fromGraph(this)
  }
}
