package com.yzh44yzh.scalaAmf.mina

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolEncoder
import org.apache.mina.filter.codec.ProtocolEncoderOutput
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import java.nio.charset.CharsetEncoder
import com.yzh44yzh.scalaAmf.{BufUtils, AmfClass, AmfType, Amf}

class AmfEncoder extends ProtocolEncoder
{
    private val log: Logger = LoggerFactory.getLogger(classOf[AmfEncoder])

    private val encoder: CharsetEncoder = Charset.forName("UTF-8").newEncoder

    def encode(ioSession: IoSession, msg: AnyRef, out: ProtocolEncoderOutput)
    {
        if(log.isDebugEnabled)
        {
            log.debug(" %%% encode {}", msg)
        }

        if(msg.isInstanceOf[String])
        {
            val buf = IoBuffer.allocate(128).setAutoExpand(true)
            buf.putString(msg.toString, encoder)
            buf.put(0 toByte)
            buf.flip
            out.write(buf)
        }
        else if(msg.isInstanceOf[AmfClass])
        {
            val buf = Amf.encode((AmfType.OBJECT, msg))
            if(log.isDebugEnabled)
            {
                log.debug(BufUtils.toString(buf, true))
            }
            out.write(buf)
        }
        else
        {
            log.error("msg must be String or AmfClass")
            log.error("{}", msg)
        }
    }

    def dispose(session: IoSession)
    {
        val cache = session.getAttribute("ioCache")
        if(cache != null) cache.asInstanceOf[IoBuffer].clear()
        session.removeAttribute("ioCache")
    }
}
