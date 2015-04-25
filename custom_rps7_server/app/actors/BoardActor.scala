package actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.event.LoggingReceive
import akka.actor.ActorRef
import akka.actor.Terminated
import play.libs.Akka
import akka.actor.Props
import etc._
import akka.actor.PoisonPill
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout
import scala.concurrent.Await
class BoardActor extends Actor with ActorLogging {
  var users = Set[ActorRef]()
  var ready_users = Set[GamerInfo]()
  var game_rooms = Set[ActorRef]()
  var map_user_game_room = Map[String, ActorRef]()
  
  def receive = LoggingReceive {
    case c :Cast => game_rooms foreach { _ ! c}
      //log.info("item = {}", item)
    case Command(nick, uid, cmd) =>
        cmd match
        {
          case "help" => sender ! Notification("명령어 : /user_count, /ranking")
          case "user_count" => sender ! Notification(users.size.toString())
          case "ranking" => //sender ! Notification("준비중")
          case "ready" => 
            val gamer_info = GamerInfo(nick, uid, sender)
            ready_users += gamer_info
            
            self ! Notification(nick + "님이 게임 준비를 했습니다.(현재 " + ready_users.size + "/" + users.size + "명)")
            
            if(users.size == ready_users.size && users.size > 1)
            {
              self ! Notification("!go_game_scene") 
              val game_room_actor = Akka.system().actorOf(GameRoomActor.props(ready_users))
              game_rooms += game_room_actor
              //sender ! PoisonPill
              for(g <- ready_users)
              {
                map_user_game_room += ( g.uid -> game_room_actor)
              }
              ready_users = Set[GamerInfo]()
              
            }
            log.info( "users.size = {}, ready_user.size = {}",users.size.toString(), ready_users.size.toString())
          case "join" => map_user_game_room(uid) ! JoinGameRoom
          //case "start_game" => map_user_game_room(uid) ! SendTurnInfo
          case _ => sender ! Notification("명령어가 잘못 되었습니다.(" + cmd + ")") 
        }
    //case r: RespondGetUID => map_user_game_room += (r.uid -> r)
    //log.info( "map_user_game_room.size = {}",map_user_game_room.size.toString())
    case m: Message =>
      users foreach { _ ! m }
    case n: Notification =>
      users foreach { _ ! n }
    case Subscribe =>
      users += sender
      log.info( "users.size = {}, ready_user.size = {}",users.size.toString(), ready_users.size.toString())
      context watch sender
    case Terminated(user) =>
      users -= user
      val remove_user_info: Option[etc.GamerInfo] = ready_users.find( (x: etc.GamerInfo) => x.gamer_actor == user)
      remove_user_info.map(g =>  ready_users -= g)
      
      log.info( "users.size = {}, ready_user.size = {}",users.size.toString(), ready_users.size.toString())
  }
}

object BoardActor {
  lazy val board = Akka.system().actorOf(Props[BoardActor])
  def apply() = board
}

