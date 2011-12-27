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

		session.write("got " + message.toString)
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