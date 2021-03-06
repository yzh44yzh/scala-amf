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
            val id = code >> 1
            ref.strings.get(id).asInstanceOf[String]
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
        if(ref.strings.hasValue(str))
        {
            val id = ref.strings.getKey(str)
            AmfInt.write(buf, id << 1)
        }
        else
        {
            if(str.equals(""))
            {
                AmfInt.write(buf, 1)
            }
            else
            {
                ref.strings.store(str)

                val byBuf : ByteBuffer = charset.encode(str)
                val bytes = new Array[Byte](byBuf.limit)
                byBuf.get(bytes)

                AmfInt.write(buf, bytes.length << 1 | 1);
                buf.put(bytes);
            }
        }
    }
}
