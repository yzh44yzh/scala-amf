package com.yzh44yzh.scalaAmf

import org.slf4j.LoggerFactory
import com.yzh44yzh.scalaRpc.{ConnectDisconnect, Client, RPCCall}
import scala.collection.mutable.Map

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class GameAPI extends ConnectDisconnect
{
	val log = LoggerFactory.getLogger(getClass)

	val clients = Map.empty[Int, Client]

	def onConnect(client : Client)
	{
		log.info("connect " + client)
		clients += (client.id -> client)

		log.info("total clients " + clients.size)
	}

	def onDisconnect(client : Client)
	{
		log.info("disconnect " + client)
		clients -= client.id

		log.info("total clients " + clients.size)
	}

	def getColor(call : RPCCall, client : Client) : Int =
	{
		log.info(client + " getColor")
		Game.getColor()
	}

	def newCircle(call : RPCCall, client : Client)
	{
		val y = call.params.get("y").asInstanceOf[Int]
		val x = call.params.get("x").asInstanceOf[Int]
		val radius = call.params.get("radius").asInstanceOf[Int]

		log.info(client + " newCircle " + x + " " + y + " " + radius)
	}
}
