package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import java.util.ArrayList

private object AmfArray
{
	def read(buf : IoBuffer, ref : Ref) : ArrayList[Any] =
	{
		val code = AmfInt.read(buf)
		if((code & 1) == 0)
		{
			val id = code >> 1
			return ref.objects.get(id).asInstanceOf[ArrayList[Any]]
		}

		val len = code >> 1

		val key = AmfString.read(buf, ref)
		if(!key.equals("")) throw new Exception("associative arrays are not supported")

		var result = new ArrayList[Any]
		ref.objects.store(result)

		var i = 0;
		while(i < len)
		{
			val (anyType, res) = Amf.decode(buf, ref)
			result.add(res)
			i += 1
		}

		result
	}

	def write(buf : IoBuffer, list : ArrayList[Any], ref : Ref) : IoBuffer =
	{
		if(ref.objects.hasValue(list))
		{
			val id = ref.objects.getKey(list)
			AmfInt.write(buf, id << 1)
		}
		else
		{
			ref.objects.store(list)

			val len = list.size
			AmfInt.write(buf, len << 1 | 1)
			AmfString.write(buf, "", ref)

			val it = list.iterator()
			while(it.hasNext) Amf.encodeAny(buf, it.next(), ref)
		}

		buf
	}
}
