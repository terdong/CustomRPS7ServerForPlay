package actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.event.LoggingReceive
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import akka.actor.ActorRef
import akka.actor.Props
import scala.xml.Utility
import akka.actor.Terminated
import play.libs.Akka
import etc._
import org.msgpack.ScalaMessagePack

class GamerActor(nick: String, uid: String, board: ActorRef, out: ActorRef) extends Actor with ActorLogging {
  override def preStart() = {
    board ! Subscribe
  }
  override def postStop() {
    log.info("postStop " + nick)
  }
  def receive = LoggingReceive {
    case binary:Array[Byte] =>{
      
      val deserialized : Map[String,String] = ScalaMessagePack.read[Map[String,String]](binary)
      if(deserialized isDefinedAt "compact")
        println(deserialized("compact"))
      }
    case item:Int =>board ! Cast(item)
    case str:String => str match {
      case str if str.indexOf('/').equals(0) => board ! Command(nick, uid, str.replace("/",""))
      case _ => board ! Message(nick, str) 
      }
      println(str)
    case Notification(s) => out ! s 
    case Message(nick, s) if sender == board =>
      out ! nick + ": " + s
      //val js = Json.obj("type" -> "message", "uid" -> muid, "msg" -> s)
      //out ! js
    //case js: JsValue =>
    //  (js \ "msg").validate[String] map { Utility.escape(_) } foreach { board ! Message(nick, _) }
    //case RequestReturn => sender ! uid
    case RequestGetUID(game_room_actor) => sender ! RespondGetUID(game_room_actor, nick, uid)
    case other =>
      log.error("unhandled: " + other)
  }
}

object GamerActor {
  def props(board: ActorRef, nick: String, uid: String)(out: ActorRef) = Props(new GamerActor(nick, uid, board, out))
}



