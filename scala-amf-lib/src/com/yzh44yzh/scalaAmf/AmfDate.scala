package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import java.util.Date

private object AmfDate
{
    def read(buf : IoBuffer) : Date =
    {
        val ref = AmfInt.read(buf)

        /*if((ref & 1) == 0)
        {
            // return getReference(ref >> 1).asInstanceOf[Date]
        }*/

        val date = new Date(buf.getDouble.asInstanceOf[Long])
        //storeReference(date)
        date
    }

    def write(buf : IoBuffer, date : Date) =
    {
        /*if(hasReference(date))
        {
            putInteger(getReferenceId(date) << 1)
            return
        }*/
        //storeReference(date)

        buf.put(0x1 toByte)
        buf.putDouble(date.getTime)
    }
}
