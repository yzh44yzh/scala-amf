package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import java.nio.ByteBuffer
import java.nio.charset.Charset

private object AmfString
{
    val charset = Charset.forName("UTF-8")

    def read(buf: IoBuffer, ref : Ref): String =
    {
        val code = AmfInt.read(buf)

        if((code & 1) == 0)
        {
            ref.strings.get(code >> 1).asInstanceOf[String]
        }
        else
        {
            val len: Int = code >> 1
            if(len == 0) return ""

            val limit = buf.limit
            val strBuf: ByteBuffer = buf.buf
            strBuf.limit(strBuf.position + len)
            val string = charset.decode(strBuf).toString
            buf.limit(limit)

            ref.strings.store(string)
            string
        }
    }

    def write(buf: IoBuffer, str: String, ref : Ref) =
    {
        // TODO refs

        if(str.equals(""))
        {
            AmfInt.write(buf, 1)
        }
        else
        {
            val byBuf : ByteBuffer = charset.encode(str)
            val bytes = new Array[Byte](byBuf.limit)
            byBuf.get(bytes)

            AmfInt.write(buf, bytes.length << 1 | 1);
            buf.put(bytes);
        }
    }
}
