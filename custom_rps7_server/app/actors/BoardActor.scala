package actors

import akka.actor.Actor
import akka.actor.ActorLogging

class BoardActor extends Actor with ActorLogging {
  def receive: Actor.Receive = {
    case _ => {}
  }
}