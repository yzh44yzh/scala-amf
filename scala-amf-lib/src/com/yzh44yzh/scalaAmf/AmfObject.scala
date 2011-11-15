package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer

private object AmfObject
{
    def read(buf: IoBuffer): AmfClass =
    {
        var code = AmfInt.read(buf)
        /*
		if((code & 1) == 0)
		{
			//Reference
		}
         */

        code >>= 1
        val inlineClass : Boolean = (code & 1) == 1
        if(!inlineClass)
        {
            // TODO what is this?
            throw new Exception("inline classes are not supported")
        }

        code >>= 1

        var result : AmfClass = null

        val className = AmfString.read(buf)
        if(className.equals(""))
        {
            if((code & 0x03) == 0) result = readNamesThanValues(code >> 2, buf)
            else result = readNameValuePairs(buf)
        }
        else
        {
            // TODO
        }

        result.className = ""
        result
    }

    def write(buf: IoBuffer, obj: AmfClass) : IoBuffer =
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

    def readNamesThanValues(numItems : Int, buf : IoBuffer) : AmfClass = {
        val result = new AmfClass

        val arr = new Array[String](numItems);
        for(i <- 0 until numItems)
        {
            arr(i) = AmfString.read(buf)
        }

        for(i <- 0 until numItems)
        {
            val (anyType, value)  = Amf.decode(buf)
            result.put(arr(i), value)
        }

        result
    }

    def readNameValuePairs(buf : IoBuffer) : AmfClass = {
        val result = new AmfClass

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

        result
    }
}
