/**
 * @author Yura Zhloba
 */

package com.yzh44yzh.scalaAmf.socket
{

import flash.events.ErrorEvent;
import flash.events.Event;
import flash.events.EventDispatcher;
import flash.events.IEventDispatcher;

import mx.logging.ILogger;
import mx.logging.Log;

public class BaseSocket
{
	protected var log : ILogger = Log.getLogger(toString());

	protected var _connected : Boolean = false;

	public function get connected() : Boolean
	{ return _connected; }



	protected var _dispatcher : IEventDispatcher = new EventDispatcher();

	public function get dispatcher() : IEventDispatcher
	{ return _dispatcher; }

	public function set dispatcher(value : IEventDispatcher) : void
	{ _dispatcher = value; }


	
	// abstract
	public function connect(host : String, port : int) : void {}

	// abstract
	public function disconnect() : void {}

	// abstract
	public function send(action : String, data : Object = null, callback : Function = null) : uint
	{ return 0; }

	protected function onConnect(ev : Event) : void
	{
		log.info("onConnect");
		_connected = true;
		_dispatcher.dispatchEvent(new SocketEvent(SocketEvent.CONNECTED));
	}

	protected function onClose(ev : Event) : void
	{
		log.info("onClose");
		_connected = false;
		_dispatcher.dispatchEvent(new SocketEvent(SocketEvent.DISCONNECTED));
	}

	protected function onError(ev : ErrorEvent) : void
	{
		log.error("onError " + ev.text);
		var e : SocketEvent = new SocketEvent(SocketEvent.ERROR);
		e.error = ev;
		_dispatcher.dispatchEvent(e);
	}

	protected function notifyData(queryID : int, action : String, data : Object) : void
	{
		var e : SocketEvent = new SocketEvent(SocketEvent.DATA);
		e.queryID = queryID;
		e.action = action;
		e.data = data;
		_dispatcher.dispatchEvent(e);
	}

	public function toString() : String { return 'BaseSocket'; }
}
}
