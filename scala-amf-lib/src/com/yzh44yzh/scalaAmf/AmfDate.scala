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
            // NOTE: for some unknown reason flash client uses wrong refs for Date
            // I have to fix it by subtracting 1
            val id = (code >> 1) - 1
            ref.dates.get(id).asInstanceOf[Date]
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
        if(ref.dates.hasValue(date))
        {
            val id = ref.dates.getKey(date)
            // NOTE: for some unknown reason flash client uses wrong refs for Date
            // I have to fix it by adding 1
            AmfInt.write(buf, (id + 1)<< 1)
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
