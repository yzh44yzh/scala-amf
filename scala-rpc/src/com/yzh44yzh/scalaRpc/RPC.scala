package com.yzh44yzh.scalaRpc

import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IoSession
import org.slf4j.LoggerFactory
import com.yzh44yzh.scalaAmf.AmfClass
import java.lang.reflect.Method

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class RPC(apiClass : AnyRef) extends IoHandlerAdapter
{
	private val log = LoggerFactory.getLogger(toString)

	override def messageReceived(session : IoSession, message : Any)
	{
		log.debug("messageReceived {}", message)

		try
		{
			val call = new RPCCall(message.asInstanceOf[AmfClass])

			val method: Method = apiClass.getClass.getDeclaredMethod(call.action, classOf[RPCCall], classOf[IoSession])
			method.invoke(apiClass, call, session)

			if(call.callbackID > 0) session.write(call.answer())
		}
		catch
		{
			case e : Exception =>
				log.error("message {}", message)
				log.error("error processing message from client", e)
		}
	}

	override def sessionClosed(session : IoSession)
	{

	}

	override def exceptionCaught(session : IoSession, cause : Throwable)
	{
		log.error("exceptionCaught", cause);
	}
}
