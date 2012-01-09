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

	protected var callbacks : Object = {};

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

	public override function send(action : String, data : Object = null, callback : Function = null) : void
	{
		if(!_connected)
		{
			log.error("can't send, socket is not connected");
			return;
		}

		try
		{
			if(callback != null)
			{
				var id : Number = ++nextQueryID;
				log.debug("send [" + id + "] [" + action + "]");
				callbacks[id] = callback;
				binSocket.writeObject({q:id, a:action, d:data});
			}
			else
			{
				log.debug("send [" + action + "]");
				binSocket.writeObject({a:action, d:data});
			}
			binSocket.flush();
		}
		catch(e : Error)
		{
			log.error("send " + e.message + " " + e.getStackTrace());
		}
	}

	protected var cache : ByteArray = new ByteArray();

	protected function onData(ev : ProgressEvent) : void
	{
		if(!_connected) return;

		var tmp : ByteArray = new ByteArray;

		// copy cached data to tmp
		cache.readBytes(tmp);
		tmp.position = cache.position;

		// add binSocket data to tmp
		while(binSocket.bytesAvailable) tmp.writeByte(binSocket.readByte());

		tmp.position = 0;
		while(tmp.bytesAvailable > 0) // read objects from tmp one by one
		{
			var data : Object;

			try
			{
				// keep tmp data in cache before readObject
				cache = new ByteArray();
				var prevPosition : int = tmp.position;
				tmp.readBytes(cache);
				tmp.position = prevPosition;

				data = tmp.readObject();

				// readObject was success, clear cache now
				cache = new ByteArray();
			}
			catch(e : RangeError)
			{
				// readObject wasn't success, wait for more data
				return;
			}
			catch(e : EOFError)
			{
				// readObject wasn't success, wait for more data
				return;
			}
			catch(e : Error)
			{
				log.error("onData " + e.message + " " + e.getStackTrace());
			}

			log.debug("onData [" + data.q + "] [" + data.a + "]");

			if(data.q) // find callback for this answer
			{
				var callback : Function = callbacks[data.q];
				if(callback != null) callback(data.d);
				else notifyData(data.q, data.a, data.d);
			}
			else // active push from server
			{
				notifyData(-1, data.a, data.d);
			}
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