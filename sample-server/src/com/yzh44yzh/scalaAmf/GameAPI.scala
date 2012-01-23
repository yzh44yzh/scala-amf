package com.yzh44yzh.scalaAmf

import org.slf4j.LoggerFactory
import com.yzh44yzh.scalaRpc.{Client, RPCCall}

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class GameAPI
{
	val log = LoggerFactory.getLogger(getClass)

	def getColor(call : RPCCall, client : Client) : Int =
	{
		log.info(client + " getColor")
		Game.getColor()
	}

	def newCircle(call : RPCCall, client : Client)
	{
		// message {a=newCircle, d={y=472, x=357, radius=20}}
		val y = call.params.get("y").asInstanceOf[Int]
		val x = call.params.get("x").asInstanceOf[Int]
		val radius = call.params.get("radius").asInstanceOf[Int]

		log.info(client + " newCircle " + x + " " + y + " " + radius)
	}
}
