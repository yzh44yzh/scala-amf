package com.yzh44yzh.scalaRpc

import org.slf4j.LoggerFactory

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class Client(_id : Int)
{
	private val log = LoggerFactory.getLogger(toString())

	log.info(toString + " created")

	def id : Int = _id

	override def toString : String = "Client " + _id
}
