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
import org.msgpack.template.Template
import play.api.libs.json.JsObject
import play.api.libs.json.JsArray
import play.api.libs.json.JsNull
import play.api.libs.json.JsString
import play.api.libs.json.JsNumber
import com.teamgehem.Protobuf.PacketMessage._
import PacketType._

class GamerActor(nick: String, uid: String, board: ActorRef, out: ActorRef) extends Actor with ActorLogging {
  override def preStart() = {
    board ! Subscribe
  }
  override def postStop() {
    log.info("postStop " + nick)
  }
  
  val myInfo:GamerInfo = GamerInfo(uid, nick, out)
  
  def receive = LoggingReceive {
//    case g_protocol: GehemProtocol => {
//      g_protocol.packetType match {
//        case OutputChat => out ! g_protocol.toByteArray
//        case other => {}
//      }
//    }
    case binary: Array[Byte] => {
      
      val g_protocol = GehemProtocol.parseFrom(binary);
      g_protocol.packetType match {
        case NONE => {}
        case ConnectionSuccess =>{
          //out ! g_protocol.update(_.someInt32List :+= 999).toByteArray
          board ! myInfo
        } 
        case InputChat => {
          board ! g_protocol
        }
        
        case other => {}
      }
      
      /*val deserialized: Map[String, String] = ScalaMessagePack.read[Map[String, String]](binary)
      if (deserialized isDefinedAt "compact") {
        println(deserialized("compact"))
        val json: JsValue = JsObject(Seq(
          "name" -> JsString("Watership Down"),
          "location" -> JsObject(Seq("lat" -> JsNumber(51.235685), "long" -> JsNumber(-1.309197))),
          "residents" -> JsArray(Seq(
            JsObject(Seq(
              "name" -> JsString("Fiver"),
              "age" -> JsNumber(4),
              "role" -> JsNull)),
            JsObject(Seq(
              "name" -> JsString("Bigwig"),
              "age" -> JsNumber(6),
              "role" -> JsString("Owsla")))))))
        val json_string = Json.toJson(deserialized)
        val serialized: Array[Byte] = ScalaMessagePack.write(json.toString())
        out ! serialized
      }*/
    }
    case item: Int => board ! Cast(item)
    case str: String =>
      str match {
        case str if str.indexOf('/').equals(0) => board ! Command(nick, uid, str.replace("/", ""))
        case _                                 => board ! Message(nick, str)
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



