/**
 * @author Yura Zhloba
 */
package com.yzh44yzh.scalaAmf.socket
{
import flash.errors.EOFError;
import flash.events.ErrorEvent;
import flash.events.Event;
import flash.events.IOErrorEvent;
import flash.events.ProgressEvent;
import flash.events.SecurityErrorEvent;
import flash.net.Socket;
import flash.utils.ByteArray;

public class AmfSocket extends BaseSocket
{
	protected static var nextQueryID : Number = 0;

	protected var binSocket : Socket;

	protected var queries : Array = [];

	protected var busy : Boolean = false;

	public override function connect(host : String, port : int) : void
	{
		log.info("connect [" + host + "] [" + port + "]");
		try
		{
			super.connect(host, port);

			if(binSocket == null)
			{
				binSocket = new Socket();
				binSocket.addEventListener(Event.CONNECT, onConnect);
				binSocket.addEventListener(Event.CLOSE, onClose);
				binSocket.addEventListener(ProgressEvent.SOCKET_DATA, onData);
				binSocket.addEventListener(ErrorEvent.ERROR, onError);
				binSocket.addEventListener(IOErrorEvent.IO_ERROR, onError);
				binSocket.addEventListener(SecurityErrorEvent.SECURITY_ERROR, onError);
			}

			if(!_connected) binSocket.connect(host, port);
		}
		catch(e : Error)
		{
			log.error("connect " + e.message + " " + e.getStackTrace());
		}
	}

	public override function disconnect() : void
	{
		log.info("disconnect");
		binSocket.close();
		_connected = false;
	}

	public override function send(action : String, data : Object = null, callback : Function = null) : uint
	{
		try
		{
			var id : Number = ++nextQueryID;
			log.debug("send [" + id + "] [" + action + "]");
			queries.push([id, action, data, callback]);

			sendNextQuery();
		}
		catch(e : Error)
		{
			log.error("send " + e.message + " " + e.getStackTrace());
		}

		return id;
	}

	protected function sendNextQuery() : void
	{
		if(busy) return;
		if(!_connected) return;
		if(queries.length == 0) return;

		busy = true;

		try
		{
			var query : Array = queries[0];
			log.debug("sendNextQuery [" + query[0] + "] [" + query[1] + "]");
			binSocket.writeObject({q:query[0], a:query[1], d:query[2]});
			binSocket.writeByte(0);
			binSocket.flush();
		}
		catch(e : Error)
		{
			log.error("sendNextQuery " + e.message + " " + e.getStackTrace());
		}
	}

	protected var cache : ByteArray = new ByteArray();

	protected function onData(ev : ProgressEvent) : void
	{
		if(!_connected) return;

		try
		{
			var tmp : ByteArray = new ByteArray;
			while(cache.bytesAvailable) tmp.writeByte(cache.readByte());
			while(binSocket.bytesAvailable) tmp.writeByte(binSocket.readByte());
			tmp.position = 0;
			tmp.readBytes(cache);
			tmp.position = 0;
			cache.position = 0;

			while(tmp.bytesAvailable > 0)
			{
				try
				{
					var data : Object = tmp.readObject();
				}
				catch(e : EOFError)
				{
					// wait for more data
					return;
				}

				cache = new ByteArray();

				log.debug("onData [" + data.q + "] [" + data.a + "]");

				if(data.q) // answer to query
				{
					var query : Array = queries.shift();
					if(query[0] != data.q) throw new Error("invalid queryID");
					if(query[1] != data.a) throw new Error("invalid action");

					var callback : Function = query[3];
					if(callback != null) callback(data.d);
					else notifyData(data.q, data.a, data.d);

					busy = false;
					sendNextQuery();
				}
				else // active push from server
				{
					notifyData(-1, data.a, data.d);
				}
			}
		}
		catch(e : Error)
		{
			log.error(this + ".onData " + e.message + " " + e.getStackTrace());
		}
	}

	protected override function onClose(ev : Event) : void
	{
		log.info("onClose");
		binSocket.close();
		super.onClose(ev);
	}

	override protected function onError(ev : ErrorEvent) : void
	{
		super.onError(ev);
		binSocket.close();
	}

	public override function toString() : String { return 'AmfSocket'; }
}
}