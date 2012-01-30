package com.yzh44yzh.scalaAmf.mina

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apache.mina.core.filterchain.{IoFilter, IoFilterAdapter}

class FlashPolicyRequestFilter(val allowedPort : Int) extends IoFilterAdapter
{
	private val log : Logger = LoggerFactory.getLogger(classOf[FlashPolicyRequestFilter])

	private val response = new StringBuffer().append("<?xml version=\"1.0\"?>")
			.append("<cross-domain-policy>")
			.append("<site-control permitted-cross-domain-policies=\"all\"/>")
			.append("<allow-access-from domain=\"*\" to-ports=\"")
			.append(allowedPort)
			.append("\" /></cross-domain-policy>")
			.toString

	override def messageReceived(nextFilter : IoFilter.NextFilter, session : IoSession, message : AnyRef)
	{
		val buf = message.asInstanceOf[IoBuffer]
		if(checkBuffer(buf))
		{
			log.debug("send cross domain policy")
			session.write(response)
		}
		else nextFilter.messageReceived(session, message)
	}

	private def checkBuffer(in : IoBuffer) : Boolean =
	{
		// check is in buffer contains "<policy-file-request/>\0"

		if(in.get(0) != 0x3c) return false
		if(in.get(1) != 0x70) return false
		if(in.get(2) != 0x6f) return false
		if(in.get(3) != 0x6c) return false
		if(in.get(4) != 0x69) return false
		if(in.get(5) != 0x63) return false
		if(in.get(6) != 0x79) return false
		if(in.get(7) != 0x2d) return false
		if(in.get(8) != 0x66) return false
		if(in.get(9) != 0x69) return false
		if(in.get(10) != 0x6c) return false
		if(in.get(11) != 0x65) return false
		if(in.get(12) != 0x2d) return false
		if(in.get(13) != 0x72) return false
		if(in.get(14) != 0x65) return false
		if(in.get(15) != 0x71) return false
		if(in.get(16) != 0x75) return false
		if(in.get(17) != 0x65) return false
		if(in.get(18) != 0x73) return false
		if(in.get(19) != 0x74) return false
		if(in.get(20) != 0x2f) return false
		if(in.get(21) != 0x3e) return false
		if(in.get(22) != 0x0) return false

		true
	}
}
