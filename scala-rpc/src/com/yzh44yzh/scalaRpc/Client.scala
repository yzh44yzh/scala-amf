package com.yzh44yzh.scalaRpc

import org.slf4j.LoggerFactory

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class Client(id : Int)
{
	private val log = LoggerFactory.getLogger(toString())

	log.info(toString + " created")

	override def toString : String = "Client " + id
}
