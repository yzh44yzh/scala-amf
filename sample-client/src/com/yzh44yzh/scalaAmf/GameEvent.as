/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf
{
import flash.events.Event;

public class GameEvent extends Event
{
	static public const NEW_CIRCLE : String = "newCircleEvent";

	public var circle : Circle;

	public function GameEvent(type:String, bubbles:Boolean = false, cancelable:Boolean = false)
	{
		super(type, bubbles, cancelable);
	}

	static public function newCircleEvent(circle : Circle) : GameEvent
	{
		var event : GameEvent = new GameEvent(NEW_CIRCLE);
		event.circle = circle;
		return event;
	}
}
}
