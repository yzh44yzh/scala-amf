package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import java.util.LinkedHashMap

private object AmfObject
{
    def read(buf: IoBuffer): LinkedHashMap[String, Any] =
    {
        val code = AmfInt.read(buf)
        /*
		if((code & 1) == 0)
		{
			//Reference
		}
         */

        val result = new LinkedHashMap[String, Any]

        val className = AmfString.read(buf)

        if(className.equals(""))
        {
            var moreProperties = true
            while(moreProperties)
            {
                var propName = AmfString.read(buf)
                if(!propName.equals(""))
                {
                    val (anyType, value)  = Amf.decode(buf)
                    result.put(propName, value)
                }
                else moreProperties = false
            }
        }
        else
        {
            // TODO
        }

        result
    }

    def write(buf: IoBuffer, obj: LinkedHashMap[String, Any]) : IoBuffer =
    {
        /*
		if(hasReference(array))
		{
			putInteger(getReferenceId(array) << 1);
			return;
		}

		storeReference(array);
         */

        buf.put(0xb toByte) // empty class name
        AmfString.write(buf, "") // begin prop/value pairs

        val it = obj.keySet().iterator()
        while(it.hasNext)
        {
            val key = it.next
            AmfString.write(buf, key)

            Amf.encodeAny(buf, obj.get(key));
        }

        AmfString.write(buf, "") // end prop/value pairs

        buf
    }
}
