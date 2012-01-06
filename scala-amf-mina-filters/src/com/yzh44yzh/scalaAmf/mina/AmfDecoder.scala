package com.yzh44yzh.scalaAmf.mina

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolDecoder
import org.apache.mina.filter.codec.ProtocolDecoderOutput
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.BufferUnderflowException
import com.yzh44yzh.scalaAmf._

class AmfDecoder extends ProtocolDecoder
{
	private val log : Logger = LoggerFactory.getLogger(classOf[AmfDecoder])

	def decode(session : IoSession, in : IoBuffer, out : ProtocolDecoderOutput)
	{
		if(log.isDebugEnabled)
		{
			log.debug(" %%% decode {}", in.limit)
			log.debug(BufUtils.toString(in, true))
		}

		val cache : IoBuffer = session.getAttribute("ioCache").asInstanceOf[IoBuffer]
		val input = BufUtils.merge(cache, in)
		in.position(in.limit)

		var inputProcessed = false
		while(!inputProcessed)
		{
			val res = getData(input)
			val data : AmfClass = res._1
			val left : IoBuffer = res._2

			if(log.isDebugEnabled)
			{
				log.debug(" data {}", data)
				log.debug(" left {}", left)
			}

			if(data != null) out.write(data)
			else inputProcessed = true

			if(left == null || !left.hasRemaining) inputProcessed = true

			if(inputProcessed) session.setAttribute("ioCache", left)
		}
	}

	def getData(input : IoBuffer) : (AmfClass, IoBuffer) =
	{
		try
		{
			val data : AmfClass = Amf.decode(input).asInstanceOf[AmfClass]
			val left = BufUtils.getRest(input)
			return (data, left)
		}
		catch
		{
			case e1 : BufferUnderflowException => true
			case e2 : IllegalArgumentException => true
			case e3 : Exception => log.error("unknown exception while reading data from buffer", e3)
		}

		input.position(0)
		(null, input)
	}

	def finishDecode(ioSession : IoSession, protocolDecoderOutput : ProtocolDecoderOutput)
	{
		// do nothing
	}

	def dispose(session : IoSession)
	{
		val cache = session.getAttribute("ioCache")
		if(cache != null) cache.asInstanceOf[IoBuffer].clear()
		session.removeAttribute("ioCache")
	}
}
