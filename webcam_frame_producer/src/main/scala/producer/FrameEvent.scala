package producer

case class FrameEvent(cameraId: String,
                      timestamp: String,
                      rows: Int,
                      cols: Int,
                      `type`: Int,
                      data: String)