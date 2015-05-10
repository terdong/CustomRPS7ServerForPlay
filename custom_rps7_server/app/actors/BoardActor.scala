package actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.event.LoggingReceive
import akka.actor.ActorRef
import akka.actor.Terminated
import play.libs.Akka
import etc._
import akka.actor.PoisonPill
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout
import scala.concurrent.Await
import com.teamgehem.Protobuf.PacketMessage._
import PacketType._
import akka.actor.ActorPath
import akka.actor.Props

class BoardActor extends Actor with ActorLogging {
  var waitingForGamers = Set[ActorPath]()
  var allGamersActor = Map[ActorPath, GamerInfo]()

  var users = Set[ActorRef]()
  var readyUsers = Set[GamerInfo]()
  var gameRooms = Set[ActorRef]()
  var mapUserGameRoom = Map[String, ActorRef]()

  def receive = LoggingReceive {
    case gamerInfo: GamerInfo => {
      log.info("uid = " + sender.path.toString())
      waitingForGamers += sender.path
      allGamersActor += sender.path -> gamerInfo
    }
    case g_protocol: GehemProtocol =>
      {
        g_protocol.packetType match {
          case InputChat => {
            val out_protocol = g_protocol.update(
                _.packetType := PacketType.OutputChat,
                _.someString := allGamersActor(sender.path).nick + " : " + g_protocol.someString.getOrElse("test") 
                )
            waitingForGamers foreach((uid : ActorPath) => {
            allGamersActor(uid).gamerOutActor ! out_protocol.toByteArray
            })
          }
          case other => {

          }
        }
      }

    case c: Cast => gameRooms foreach { _ ! c }
    //log.info("item = {}", item)
    case Command(nick, uid, cmd) =>
      cmd match {
        case "help"       => sender ! Notification("명령어 : /user_count, /ranking")
        case "user_count" => sender ! Notification(users.size.toString())
        case "ranking"    => //sender ! Notification("준비중")
        case "ready" =>
          val gamer_info = GamerInfo(uid, nick, sender)
          readyUsers += gamer_info

          self ! Notification(nick + "님이 게임 준비를 했습니다.(현재 " + readyUsers.size + "/" + users.size + "명)")

          if (users.size == readyUsers.size && users.size > 1) {
            self ! Notification("!go_game_scene")
            val game_room_actor = Akka.system().actorOf(GameRoomActor.props(readyUsers))
            gameRooms += game_room_actor
            //sender ! PoisonPill
            for (g <- readyUsers) {
              mapUserGameRoom += (g.uid -> game_room_actor)
            }
            readyUsers = Set[GamerInfo]()

          }
          log.info("users.size = {}, ready_user.size = {}", users.size.toString(), readyUsers.size.toString())
        case "join" => mapUserGameRoom(uid) ! JoinGameRoom
        //case "start_game" => map_user_game_room(uid) ! SendTurnInfo
        case _      => sender ! Notification("명령어가 잘못 되었습니다.(" + cmd + ")")
      }
    //case r: RespondGetUID => map_user_game_room += (r.uid -> r)
    //log.info( "map_user_game_room.size = {}",map_user_game_room.size.toString())
    case m: Message =>
      users foreach { _ ! m }
    case n: Notification =>
      users foreach { _ ! n }
    case Subscribe =>
      users += sender
      log.info("users.size = {}, ready_user.size = {}", users.size.toString(), readyUsers.size.toString())
      context watch sender
    case Terminated(user) =>
      waitingForGamers -= user.path
      allGamersActor -= user.path
      
      
      //users -= user
      //val remove_user_info: Option[etc.GamerInfo] = readyUsers.find((x: etc.GamerInfo) => x.gamer_actor == user)
      //remove_user_info.map(g => readyUsers -= g)

//      log.info("users.size = {}, ready_user.size = {}", users.size.toString(), readyUsers.size.toString())
      log.info("users.size = {}, ready_user.size = {}", allGamersActor.size, readyUsers.size.toString())
  }
}

object BoardActor {
  lazy val board = Akka.system().actorOf(Props[BoardActor])
  def apply() = board
}

