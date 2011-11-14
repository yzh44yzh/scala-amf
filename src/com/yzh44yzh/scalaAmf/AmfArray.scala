package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import java.util.ArrayList

private object AmfArray
{
    def read(buf: IoBuffer): ArrayList[Any] =
    {
        val code = AmfInt.read(buf)
        /*
		if((code & 1) == 0)
		{
			//Reference
			Object ref = getReference(code >> 1);
			if(ref != null)
			{
				return ref;
			}
		}
         */
        val len = (code >> 1)

        val key = AmfString.read(buf)
        if(!key.equals("")) throw new Exception("associative arrays are not supported")

        var result = new ArrayList[Any]
        var i = 0;
        while(i < len)
        {
            val (anyType, res) = Amf.decode(buf)
            result.add(res)
            i += 1
        }

        result
    }

    def write(buf: IoBuffer, list: ArrayList[Any]) =
    {
        /*
		if(hasReference(array))
		{
			putInteger(getReferenceId(array) << 1);
			return;
		}

		storeReference(array);
         */

        val len = list.size
        AmfInt.write(buf, len << 1 | 1)
        AmfString.write(buf, "")

        val it = list.iterator()
        while(it.hasNext) Amf.encodeAny(buf, it.next())
    }
}
