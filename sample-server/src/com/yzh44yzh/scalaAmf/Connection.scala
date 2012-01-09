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
		val queryId = query.get("q")
		val action = query.get("a")
		val data = query.get("d")

		val result : Any = action match
		{
			case "getColor" => Game.getColor()
			case _ => null
		}

		if(queryId != null)
		{
			val answer = new AmfClass
			answer.put("q", queryId)
			answer.put("d", result)
			session.write(answer)
		}
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