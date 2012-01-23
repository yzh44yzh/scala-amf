package com.yzh44yzh.scalaAmf

import org.apache.mina.core.session.IoSession
import org.slf4j.LoggerFactory
import com.yzh44yzh.scalaRpc.RPCCall

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class GameAPI
{
	val log = LoggerFactory.getLogger(getClass)

	def getColor(call : RPCCall, session : IoSession) : Int =
	{
		Game.getColor()
	}

	def newCircle(call : RPCCall, session : IoSession) =
	{
		// message {a=newCircle, d={y=472, x=357, radius=20}}
		val y = call.params.get("y").asInstanceOf[Int]
		val x = call.params.get("x").asInstanceOf[Int]
		val radius = call.params.get("radius").asInstanceOf[Int]

		log.info("newCircle " + x + " " + y + " " + radius)
	}
}
