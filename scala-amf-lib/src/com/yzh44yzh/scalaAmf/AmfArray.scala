package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import java.util.ArrayList

private object AmfArray
{
    def read(buf: IoBuffer, ref : Ref): ArrayList[Any] =
    {
        val code = AmfInt.read(buf)
        if((code & 1) == 0)
        {
            // NOTE: for some unknown reason flash client uses wrong refs for Array
            // I have to fix it by subtracting 1
            // ref.dates.get(code >> 1).asInstanceOf[ArrayList[Any]]
            return ref.arrays.get((code >> 1) - 1).asInstanceOf[ArrayList[Any]]
        }

        val len = (code >> 1)

        val key = AmfString.read(buf, ref)
        if(!key.equals("")) throw new Exception("associative arrays are not supported")

        var result = new ArrayList[Any]
        var i = 0;
        while(i < len)
        {
            val (anyType, res) = Amf.decode(buf, ref)
            result.add(res)
            i += 1
        }

        ref.arrays.store(result)
        result
    }

    def write(buf: IoBuffer, list: ArrayList[Any], ref : Ref) : IoBuffer =
    {
        if(ref.arrays.hasValue(list))
        {
            val id = ref.arrays.getKey(list)
            // NOTE: for some unknown reason flash client uses wrong refs for Array
            // I have to fix it by adding 1
            // buf.put(id << 1).toByte)
            buf.put(((id + 1) << 1).toByte)
        }
        else
        {
            val len = list.size
            AmfInt.write(buf, len << 1 | 1)
            AmfString.write(buf, "", ref)

            val it = list.iterator()
            while(it.hasNext) Amf.encodeAny(buf, it.next(), ref)

            ref.arrays.store(list)
        }

        buf
    }
}
