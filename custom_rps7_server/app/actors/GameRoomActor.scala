package actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import akka.event.LoggingReceive
import play.libs.Akka
import etc._

case class GamerDetailInfo(rank:Int, Nick:String, Score:Int, item_list:Seq[Int], last_item:Int)

class GameRoomActor(gamers: Set[GamerInfo]) extends Actor with ActorLogging {
  var turn_count = 0;
  var joined_gamer = 0;
   override def postStop() {
    //joined_gamer -= 1;
  }

  var gamers_detail_info =  Map[String, GamerDetailInfo]()
      
  def receive = LoggingReceive {
    case Cast(item) => {
    // TODO : 아이템 받아서 처리해야함.      
    }
    case JoinGameRoom => {
      joined_gamer += 1
      if(joined_gamer >= gamers.size)
      {
        for(g <- gamers)
        {   
          gamers_detail_info += (g.uid -> GamerDetailInfo(0, g.nick, 0, Seq(0,1,2,3,4,5,6), 0))
        } 
        self ! SendTurnInfo
      }
    }
    case SendTurnInfo => {
     turn_count += 1
     var index = 0;
     var packet:String = "!" + turn_count + "!"
     for(g <- gamers)
     {
       val detail_info = gamers_detail_info(g.uid)
       packet += detail_info.rank + "," + detail_info.Nick + "," + detail_info.Score + ","
       for(i <- 0 until detail_info.item_list.length)
       {
         val item = detail_info.item_list(i)
         packet += item
         if(i < detail_info.item_list.length - 1){ packet +="^" }
       }
       packet += ","
       packet += detail_info.last_item
       if(index < gamers.size - 1){ packet += ";"}    
       index += 1
       g.gamerOutActor ! Notification(packet)
       //packet = ""
     }
     gamers foreach { _.gamerOutActor ! Notification(packet) }
    }
    case str: String => log.info(gamers.size.toString())
    case other =>
      log.error("unhandled: " + other)
  }
}

object GameRoomActor {
	def props(ready_users: Set[GamerInfo]) = Props(new GameRoomActor(ready_users))
  //lazy val game_room = Akka.system().actorOf(Props[GameRoomActor])
  //def apply() = game_room
}