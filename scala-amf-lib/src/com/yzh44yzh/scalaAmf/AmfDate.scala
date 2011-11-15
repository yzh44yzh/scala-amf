package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import java.util.Date

private object AmfDate
{
    def read(buf : IoBuffer, ref : Ref) : Date =
    {
        val code = AmfInt.read(buf)

        if((code & 1) == 0)
        {
            ref.dates.get(code >> 1).asInstanceOf[Date]
        }
        else
        {
            val date = new Date(buf.getDouble.asInstanceOf[Long])
            ref.dates.store(date)
            date
        }
    }

    def write(buf : IoBuffer, date : Date, ref : Ref) : IoBuffer =
    {
        val id = ref.dates.getKey(date)
        if(id != 0)
        {
            buf.put((id << 1).toByte)
        }
        else
        {
            buf.put(0x1 toByte)
            buf.putDouble(date.getTime)
            ref.dates.store(date)
        }

        buf
    }
}
