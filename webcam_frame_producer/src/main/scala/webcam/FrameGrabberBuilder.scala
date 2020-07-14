package webcam

import org.bytedeco.javacpp.opencv_core.CV_8U
import org.bytedeco.javacv.{FrameGrabber, OpenCVFrameGrabber}
import org.bytedeco.javacv.FrameGrabber.ImageMode

object FrameGrabberBuilder {
  def build(deviceId: String,
            dimensions: Dimensions,
            bitsPerPixel: Int = CV_8U,
            imageMode: ImageMode = ImageMode.COLOR
           ): FrameGrabber = synchronized {
    val g = OpenCVFrameGrabber.createDefault(deviceId)
    g.setImageWidth(dimensions.width)
    g.setImageHeight(dimensions.height)
    g.setBitsPerPixel(bitsPerPixel)
    g.setImageMode(imageMode)
    g.start()
    g
  }
}
