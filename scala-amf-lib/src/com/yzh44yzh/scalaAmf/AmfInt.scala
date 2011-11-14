package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer

private object AmfInt
{
    def read(buf : IoBuffer) : Int =
    {
        var n: Int = 0
        var b: Int = buf.get
        var result: Int = 0

        while((b & 0x80) != 0 && n < 3)
        {
            result <<= 7
            result |= (b & 0x7f)
            b = buf.get()
            n += 1
        }

        if(n < 3)
        {
            result <<= 7
            result |= b
        }
        else
        {
            result <<= 8
            result |= b & 0x0ff
            if((result & 0x10000000) != 0)
            {
                result |= 0xe0000000
            }
        }

        result
    }

    def write(buf : IoBuffer,  value : Int) =
    {
        if(value < 0)
        {
            buf.put((0x80 | ((value >> 22) & 0xff)) toByte)
            buf.put((0x80 | ((value >> 15) & 0x7f)) toByte)
            buf.put((0x80 | ((value >> 8) & 0x7f)) toByte)
            buf.put((value & 0xff) toByte)
        }
        else if(value <= 0x7f)
        {
            buf.put(value toByte)
        }
        else if(value <= 0x3fff)
        {
            buf.put((0x80 | ((value >> 7) & 0x7f)) toByte)
            buf.put((value & 0x7f) toByte)
        }
        else if(value <= 0x1fffff)
        {
            buf.put((0x80 | ((value >> 14) & 0x7f)) toByte)
            buf.put((0x80 | ((value >> 7) & 0x7f)) toByte)
            buf.put((value & 0x7f) toByte)
        }
        else
        {
            buf.put((0x80 | ((value >> 22) & 0xff)) toByte)
            buf.put((0x80 | ((value >> 15) & 0x7f)) toByte)
            buf.put((0x80 | ((value >> 8) & 0x7f)) toByte)
            buf.put((value & 0xff) toByte)
        }
    }
}
