package com.yzh44yzh.scalaRpc

import org.slf4j.LoggerFactory
import org.apache.mina.core.session.IoSession
import com.yzh44yzh.scalaAmf.AmfClass

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class Client(_id : Int, _session : IoSession)
{
	private val log = LoggerFactory.getLogger(toString())

	var color : Int = 0

	def id : Int = _id

	def invoke(action : String, data : AmfClass)
	{
		val answer = new AmfClass
		answer.put("a", action)
		answer.put("d", data)
		_session.write(answer)
	}

	override def toString : String = "Client " + _id
}
