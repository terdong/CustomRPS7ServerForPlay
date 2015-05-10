package controllers

import scala.Left
import scala.Right
import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import play.api.mvc.WebSocket
import play.api.libs.iteratee._
import actors.GamerActor
import actors.BoardActor
import play.api.libs.json._
import play.api.mvc.WebSocket.FrameFormatter
import java.util.UUID

object Application extends Controller {
  val NICK = "nick"
  val UID = "uid"
  var counter = 0
  
  val board = BoardActor()
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
//  def connect(nick: AnyRef) = WebSocket.tryAcceptWithActor[String, String] { implicit request =>
//  	Future.successful(nick match {
  def connect = WebSocket.tryAcceptWithActor[Array[Byte], Array[Byte]] { implicit request =>
     val uid: String = request.session.get(UID).getOrElse {
      counter += 1
      val uuid = UUID.randomUUID();
      request.session + (UID -> uuid.toString())
      uuid.toString
    }
     println("uid = " + uid)
    Future.successful(request.getQueryString(NICK) match {
      case None      => Left(Forbidden)
      case Some(nick) => Right(GamerActor.props(board, nick, uid))
    })
  }
  
/*  def connect = WebSocket.using[String] { request =>
    request.getQueryString("nick") match {
      case None      => Left(Forbidden)
      case Some(nick) => println(nick + " connect!!");
    }
    println(request.getQueryString("nick") + " connect!!");
    // Log events to the console
    val in = Iteratee.foreach[String](println).map { msg =>
      println("Disconnectedaa " + msg)
    }
    // Send a single 'Hello!' message
    val out = Enumerator("Hello!")
    (in, out)
  }*/

}