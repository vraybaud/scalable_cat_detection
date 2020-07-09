package webcam

import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacv.{Frame, OpenCVFrameConverter}

object MatConverter {
  private val frameToMatConverter: ThreadLocal[OpenCVFrameConverter.ToMat] = ThreadLocal.withInitial(() => new OpenCVFrameConverter.ToMat)

  def toMat(frame: Frame): Mat = {
    frameToMatConverter.get().convert(frame)
  }
}
