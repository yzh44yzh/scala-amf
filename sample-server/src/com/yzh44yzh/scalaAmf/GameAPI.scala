package com.yzh44yzh.scalaAmf

import org.slf4j.LoggerFactory
import com.yzh44yzh.scalaRpc.{ConnectDisconnect, Client, RPCCall}
import scala.collection.mutable.Map
import java.util.{Date, Random}

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class GameAPI extends ConnectDisconnect
{
	val log = LoggerFactory.getLogger(getClass)

	val clients = Map.empty[Int, Client]
	val colorLimit = scala.math.pow(2, 24).toInt
	val rand = new Random

	def onConnect(client : Client)
	{
		clients += (client.id -> client)
		log.info("connect " + client + " total: " + clients.size)
	}

	def onDisconnect(client : Client)
	{
		clients -= client.id
		log.info("disconnect " + client + " total: " + clients.size)
	}

	def getColor(call : RPCCall, client : Client) : Int =
	{
		rand.setSeed(new Date().getTime)

		client.color = rand.nextInt(colorLimit)
		client.color
	}

	def newCircle(call : RPCCall, client : Client)
	{
		val data = new AmfClass
		data.put("x", call.params.get("x"))
		data.put("y", call.params.get("y"))
		data.put("radius", call.params.get("radius"))
		data.put("color", client.color)

		for((id, otherClient) <- clients if id != client.id) otherClient.invoke("onNewCircle", data)
	}
}
