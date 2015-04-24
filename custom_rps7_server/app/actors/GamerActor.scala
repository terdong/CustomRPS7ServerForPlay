package actors

import akka.actor.Actor
import akka.actor.ActorLogging

class GamerActor extends Actor with ActorLogging {
  def receive: Actor.Receive = {
    case "test" => { log.debug("test") }
    case _ => { }
  }
}