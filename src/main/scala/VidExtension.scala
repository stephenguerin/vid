package org.nlogo.extensions.vid

import org.nlogo.api._

import javafx.embed.swing.JFXPanel


trait VideoSourceContainer {
  def videoSource: Option[VideoSource]
  def videoSource_=(source: Option[VideoSource]): Unit
}

class VidExtension(files: MovieFactory, cameras: CameraFactory, player: Player)
  extends DefaultClassManager with VideoSourceContainer {

  def this() = this(Movie, Camera, null)

  var panel = Option.empty[JFXPanel]

  override def runOnce(em: ExtensionManager): Unit = {
    //initialize javafx
    panel = Some(new JFXPanel())
  }

  override def load(manager: PrimitiveManager) = {
    manager.addPrimitive("camera-names",  new CameraNames(cameras))
    manager.addPrimitive("camera-open",   new CameraOpen(this, cameras))
    manager.addPrimitive("capture-image", new CaptureImage(this))
    manager.addPrimitive("close",         new CloseVideoSource(this))
    manager.addPrimitive("hide-player",   new HidePlayer(player))
    manager.addPrimitive("movie-open",    new MovieOpen(this, files))
    manager.addPrimitive("set-time",      new SetTime(this))
    manager.addPrimitive("show-player",   new ShowPlayer(player, this, panel.get))
    manager.addPrimitive("start",         new StartSource(this))
    manager.addPrimitive("status",        new ReportStatus(this))
    manager.addPrimitive("stop",          new StopSource(this))
  }

  var videoSource: Option[VideoSource] = None
}
