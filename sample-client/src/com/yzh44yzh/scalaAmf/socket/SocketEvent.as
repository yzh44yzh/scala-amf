/**
 * @author Yura Zhloba
 */
package com.yzh44yzh.scalaAmf.socket
{
import flash.events.Event;

public class SocketEvent extends Event
{
	static public const CONNECTED : String = 'socketConnected';
	static public const DISCONNECTED : String = 'socketDisconnected';
	static public const DATA : String = 'socketData';
	static public const ERROR : String = 'socketError';

	public var queryID : int;
	public var action : String;
	public var data : Object;
	public var error : Object;

	public function SocketEvent(type : String,
								bubbles : Boolean = true,
								cancelable : Boolean = false)
	{
		super(type, bubbles, cancelable);
	}

	public function init(data : Object, error : Object) : SocketEvent
	{
		this.data = data;
		this.error = error;
		
		return this;
	}

	public override function clone() : Event
	{
		return new SocketEvent(type, bubbles, cancelable).init(data, error);
	}
	
	public override function toString() : String
	{ return "SocketEvent"; }
}
}
