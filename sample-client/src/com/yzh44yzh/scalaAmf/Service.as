/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf
{
import com.yzh44yzh.scalaAmf.socket.AmfSocket;
import com.yzh44yzh.scalaAmf.socket.SocketEvent;

import mx.logging.ILogger;
import mx.logging.Log;

public class Service
{
	private var log : ILogger = Log.getLogger("Service");

	private var socket : AmfSocket = new AmfSocket();
	
	public function connect(host : String, port : int) : void
	{
		log.info("connect to {0} {1}", host, port);

		socket.dispatcher.addEventListener(SocketEvent.CONNECTED, onConnect);
		socket.dispatcher.addEventListener(SocketEvent.DATA, onDisconnect);
		socket.dispatcher.addEventListener(SocketEvent.DISCONNECTED, onData);
		socket.connect(host, port);
	}

	private function onConnect(event : SocketEvent) : void
	{
		log.info("onConnect");

		socket.send("getColor", null, onColor);
	}

	private function onColor(color : int) : void
	{
		log.info("onColor [{0}]", color);
	}

	private function onDisconnect(event : SocketEvent) : void
	{
		log.info("onDisconnect");
	}

	private function onData(event : SocketEvent) : void
	{
		log.info("onData {0}", event.data);
	}
}
}
