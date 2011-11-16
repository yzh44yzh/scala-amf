package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer

private object AmfObject
{
    def read(buf: IoBuffer, ref : Ref): AmfClass =
    {
        var code = AmfInt.read(buf)

        /*
        0x1       0001 -- trait
        0xb       1011 -- simple object
        0x23  (10)0011 -- class with 2 fields
        0x33  (11)0011 -- class with 3 fields
        0x43 (100)0011 -- class with 4 fields
        0x53 (101)0011 -- class with 5 fields

        first bit: 1 if reference, 0 if not reference
        I don't know what 2nd and 3rd bits means :)
        4th bit: 1 if dynamic object, 0 if not dynamic
        than goes number of fields
         */

        /*
		if((code & 1) == 0)
		{
			//TODO get from ref
		}
         */

        var className = ""
        if(code != 1) className = AmfString.read(buf, ref)

        val result = if(code != 1 && (code & 8) == 0) // check 4th bit
        {
            // not dynamic object
            val numFields = code >> 4
            readNamesThanValues(numFields, buf, ref)
        }
        else
        {
            // dynamic object
            readNameValuePairs(buf, ref)
        }

        result.className = className
        ref.objects.store(result)
        result
    }

    def write(buf: IoBuffer, obj: AmfClass, ref : Ref) : IoBuffer =
    {
        /*
		if(hasReference(array))
		{
		    // TODO get index
			putInteger(getReferenceId(array) << 1);
			return;
		}
         */

        ref.objects.store(obj)

        if(obj.className.equals("")) writeNameValuePairs(buf, obj, ref)
        else writeNamesThanValues(buf, obj, ref)

        buf
    }

    def readNamesThanValues(numFields : Int, buf : IoBuffer, ref : Ref) : AmfClass = {
        val result = new AmfClass

        val arr = new Array[String](numFields);
        for(i <- 0 until numFields)
        {
            arr(i) = AmfString.read(buf, ref)
        }

        for(i <- 0 until numFields)
        {
            val (anyType, value)  = Amf.decode(buf, ref)
            result.put(arr(i), value)
        }

        result
    }

    def writeNamesThanValues(buf : IoBuffer, obj : AmfClass, ref : Ref) : IoBuffer = {

        val code = (obj.size() << 4) + 3  // add 0011
        buf.put(code toByte) 

        AmfString.write(buf, obj.className, ref)

        val it = obj.keySet().iterator()
        while(it.hasNext)
        {
            AmfString.write(buf, it.next, ref)
        }

        val it2 = obj.keySet().iterator()
        while(it2.hasNext)
        {
            Amf.encodeAny(buf, obj.get(it2.next), ref);
        }

        buf
    }

    def readNameValuePairs(buf : IoBuffer, ref : Ref) : AmfClass = {
        val result = new AmfClass

        var moreProperties = true
        while(moreProperties)
        {
            var propName = AmfString.read(buf, ref)
            if(!propName.equals(""))
            {
                val (anyType, value)  = Amf.decode(buf, ref)
                result.put(propName, value)
            }
            else moreProperties = false
        }

        result
    }

    def writeNameValuePairs(buf : IoBuffer, obj : AmfClass, ref : Ref) : IoBuffer = {

        if(ref.objects.get(0) == obj) buf.put(0xb toByte) // add dynamic object market to first object only
        
        AmfString.write(buf, "", ref) // empty class name

        val it = obj.keySet().iterator()
        while(it.hasNext)
        {
            val key = it.next
            AmfString.write(buf, key, ref)
            Amf.encodeAny(buf, obj.get(key), ref);
        }

        AmfString.write(buf, "", ref) // end prop/value pairs
        
        buf
    }
}
