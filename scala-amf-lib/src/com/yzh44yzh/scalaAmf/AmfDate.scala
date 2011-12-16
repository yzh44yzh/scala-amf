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
            val id = code >> 1
            ref.objects.get(id).asInstanceOf[Date]
        }
        else
        {
            val date = new Date(buf.getDouble.asInstanceOf[Long])
            ref.objects.store(date)
            date
        }
    }

    def write(buf : IoBuffer, date : Date, ref : Ref) : IoBuffer =
    {
        if(ref.objects.hasValue(date))
        {
            val id = ref.objects.getKey(date)
            AmfInt.write(buf, id << 1)
        }
        else
        {
            buf.put(0x1 toByte)
            buf.putDouble(date.getTime)
            ref.objects.store(date)
        }

        buf
    }
}
