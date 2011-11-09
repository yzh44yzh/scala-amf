package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer

private object AmfArray
{
    def read(buf: IoBuffer): List[Any] =
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

        var result : List[Any] = List()
        var i = 0;
        while(i < len)
        {
            val (anyType, res) = Amf.decode(buf)
            result = res :: result
            i += 1
        }
        result.reverse
    }

    def write(buf: IoBuffer, list: List[Any]) =
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

        list.foreach(item => Amf.encodeAny(buf, item))
    }
}
