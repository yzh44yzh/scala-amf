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

	private var game : Game;

	public function Service(game : Game)
	{
		this.game = game;

		game.addEventListener(GameEvent.NEW_CIRCLE, onNewCircle);
	}

	public function connect(host : String, port : int) : void
	{
		log.info("connect to {0} {1}", host, port);
		game.showMsg("connecting to " + host + ":" + port);

		socket.dispatcher.addEventListener(SocketEvent.CONNECTED, onConnect);
		socket.dispatcher.addEventListener(SocketEvent.DISCONNECTED, onDisconnect);
		socket.dispatcher.addEventListener(SocketEvent.DATA, onData);
		socket.connect(host, port);
	}

	private function onConnect(event : SocketEvent) : void
	{
		log.info("onConnect");
		game.showMsg("ready");

		socket.send("getColor", null, onColor);
	}

	private function onColor(color : int) : void
	{
		log.info("onColor [{0}]", color);
		game.showMsg("Your color is #" + color.toString(16));
		game.color = color;
	}

	private function onNewCircle(event : GameEvent) : void
	{
		log.info("onNewCircle {0}", event.circle);
		
		socket.send("newCircle", event.circle);
	}

	private function onDisconnect(event : SocketEvent) : void
	{
		log.info("onDisconnect");
	}

	private function onData(event : SocketEvent) : void
	{
		log.info("onData {0}", event.action);

		if(event.action == "onNewCircle")
		{
			with(event.data)
			{
				game.onNewCircle(x, y, radius, color);
			}
		}
	}
}
}
