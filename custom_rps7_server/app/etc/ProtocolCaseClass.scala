package etc

import akka.actor.ActorRef

case class Notification(message: String)
case class Message(nick: String, s: String)
case class Command(nick: String, uid: String, cmd: String)
case class RequestGetUID(game_room:ActorRef)
case class RespondGetUID(game_room:ActorRef, nick: String, uid: String)
case class GamerInfo(nick: String, uid: String, gamer_actor: ActorRef)

case class Cast(item: Int)

object JoinGameRoom
object SendTurnInfo
object RequestReturn
object Subscribe