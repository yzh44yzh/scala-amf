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

    def read(buf: IoBuffer): String =
    {
        val tp = AmfInt.read(buf)

        /*if((tp & 1) == 0)
        {
            if(stringReferences.isEmpty)
            {
                return "null"
            }
            return stringReferences.get(tp >> 1)
        }*/

        val len: Int = tp >> 1
        if(len == 0) return ""

        val limit = buf.limit

        val strBuf: ByteBuffer = buf.buf
        strBuf.limit(strBuf.position + len)

        val string = charset.decode(strBuf).toString

        buf.limit(limit)

        //stringReferences.add(string)

        string
    }

    def write(buf: IoBuffer, str: String) =
    {
        if(str.equals(""))
        {
            AmfInt.write(buf, 1)
        }
        else
        {
            val bytes = encode(str)
			putStr(buf, str, bytes);
        }
    }

    private def encode(str : String) : Array[Byte] =
    {
		// Element element = getStringCache().get(string);
		// byte[] bytes = (element == null ? null : (byte[]) element.getObjectValue());

        // if(bytes == null) {
        val byBuf : ByteBuffer = charset.encode(str)
        val bytes = new Array[Byte](byBuf.limit)
        byBuf.get(bytes)
        // getStringCache().put(new Element(string, encoded));
        // }

        bytes
    }

    private def putStr(buf : IoBuffer, str : String, bytes : Array[Byte]) =
    {
        // Integer pos = stringReferences.get(str);
        /* if(pos != null)
        {
            // Reference to existing string
            putInteger(pos << 1);
            return;
        } */

        AmfInt.write(buf, bytes.length << 1 | 1);
        buf.put(bytes);
        // stringReferences.put(str, stringReferences.size());
    }
}
