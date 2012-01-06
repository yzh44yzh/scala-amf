package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer

object BufUtils
{
	def mkb(bytes : Byte*) : IoBuffer =
	{
		val buf = IoBuffer.allocate(128).setAutoExpand(true)
		for(byte <- bytes) buf.put(byte)
		buf.flip
		buf.position(0)
		buf
	}

	def eq(buf1 : IoBuffer, buf2 : IoBuffer) : Boolean =
	{
		buf1.position(0)
		buf2.position(0)

		while(buf1.hasRemaining) if(buf1.get != buf2.get) return false

		if(buf1.hasRemaining) return false
		if(buf2.hasRemaining) return false

		true
	}

	def diff(buf1 : IoBuffer, buf2 : IoBuffer) : String =
	{
		var res = ""

		buf1.position(0)
		buf2.position(0)

		while(buf1.hasRemaining && buf2.hasRemaining)
		{
			val s1 = if(buf1.hasRemaining) buf1.get.toHexString else "-"
			val s2 = if(buf2.hasRemaining) buf2.get.toHexString else "-"
			if(s1.equals(s2)) res += "=" else res += "#"
			res += s1 + " : " + s2 + "\n"
		}

		res
	}

	def copy(src : IoBuffer, dst : IoBuffer)
	{
		src.position(0)
		dst.position(0)
		while(src.hasRemaining) dst.put(src.get)
		src.position(0)
		dst.flip
	}

	def merge(b1 : IoBuffer, b2 : IoBuffer) : IoBuffer =
	{
		val limit1 = if(b1 == null) 0 else b1.limit
		val limit2 = if(b2 == null) 0 else b2.limit
		val result = IoBuffer.allocate(limit1 + limit2)

		if(b1 != null)
		{
			b1.position(0)
			while(b1.hasRemaining) result.put(b1.get)
			b1.position(0)
		}

		if(b2 != null)
		{
			b2.position(0)
			while(b2.hasRemaining) result.put(b2.get)
			b2.position(0)
		}

		result.flip
		result.position(0)
		result
	}

	def getRest(buf : IoBuffer) : IoBuffer =
	{
		if(buf == null) return null

		if(!buf.hasRemaining) return null

		val result = IoBuffer.allocate(buf.limit - buf.position)

		val pos = buf.position
		while(buf.hasRemaining) result.put(buf.get)
		buf.position(pos)

		result.flip
		result.position(0)
		result
	}

	def toString(buf : IoBuffer, showLetters : Boolean) : String =
	{
		val res = new StringBuffer
		while(buf.hasRemaining)
		{
			val b = buf.get
			var ch : String = ""
			if(showLetters && ((b >= 48 && b <= 57) || (b >= 65 && b <= 90) || (b >= 97 && b <= 122)))
			{
				ch = Character.toString(b.asInstanceOf[Char])
			}
			else
			{
				ch = Integer.toHexString(b)
				if(ch.length == 1) ch = "0x0" + ch
				else ch = "0x" + ch
				ch = ch + ", "
			}
			res.append(" ").append(ch)
		}
		buf.position(0)
		res.toString
	}
}
