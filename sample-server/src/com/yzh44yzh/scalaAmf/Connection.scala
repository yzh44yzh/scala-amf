package com.yzh44yzh.scalaAmf

import org.apache.mina.core.session.IoSession
import org.slf4j.LoggerFactory
import org.apache.mina.core.service.IoHandlerAdapter

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class Connection extends IoHandlerAdapter
{
	val log = LoggerFactory.getLogger(getClass)

	override def messageReceived(session : IoSession, message : Any)
	{
		log.info("messageReceived {}", message)

		val query = message.asInstanceOf[AmfClass]

		val res = new AmfClass
		res.put("q", query.get("q"))
		res.put("a", query.get("a"))
		res.put("d", "got: " + query.get("d"))
		session.write(res)
	}

	override def sessionClosed(session : IoSession)
	{
		super.sessionClosed(session)

		log.info("sessionClosed {}", session)

		// Client client = session.getAttribute("client");
	}

	override def exceptionCaught(session : IoSession, cause : Throwable)
	{
		log.error("exceptionCaught", cause)
	}
}