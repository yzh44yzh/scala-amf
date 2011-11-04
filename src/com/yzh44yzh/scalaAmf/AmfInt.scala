/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf

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
}
