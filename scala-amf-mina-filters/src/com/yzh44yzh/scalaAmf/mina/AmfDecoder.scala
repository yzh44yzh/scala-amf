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

		val res = getData(in, cache)
		val data : AmfClass = res._1
		val newCache : IoBuffer = res._2

		session.setAttribute("ioCache", newCache)
		if(data != null) out.write(data)
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

	def getData(in : IoBuffer, cache : IoBuffer) : (AmfClass, IoBuffer) =
	{
		val limit : Int = in.limit
		var oldCache : IoBuffer = cache
		var newCache : IoBuffer = null
		var data : AmfClass = null

		if(in.get(limit - 1) == 0) // last byte in buffer is 0
		{
			var pkg : IoBuffer = null
			if(oldCache != null)
			{
				pkg = BufUtils.merge(oldCache, in) // TODO implement merge
				oldCache = null
			}
			else pkg = in
			pkg.position(0)

			try
			{
				data = Amf.decode(pkg).asInstanceOf[AmfClass]
			}
			catch
			{
				case e : BufferUnderflowException =>
				{
					newCache = keepInCache(in, oldCache)
					data = null
				}
			}
		}
		else
		{
			newCache = keepInCache(in, oldCache)
		}

		in.position(limit)
		(data, newCache)
	}

	private def keepInCache(in : IoBuffer, cache : IoBuffer) : IoBuffer =
	{
		if(cache == null)
		{
			val newCache = IoBuffer.allocate(in.limit, true).setAutoExpand(true)
			BufUtils.copy(in, newCache) // TODO implement copy
			newCache
		}
		else BufUtils.merge(cache, in)
	}
}
