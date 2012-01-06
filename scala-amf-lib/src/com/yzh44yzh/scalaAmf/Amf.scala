package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import java.util.{ArrayList, Date}

object Amf
{
	def decode(buf : IoBuffer) : Any =
	{
		val ref = new Ref
		val res = decode(buf, ref)
		res
	}

	def decode(buf : IoBuffer, ref : Ref) : Any =
	{
		val tp = buf.get
		tp match
		{
			case 0x0 => null // UNDEFINED
			case 0x1 => null // NULL
			case 0x2 => false
			case 0x3 => true
			case 0x4 => AmfInt.read(buf)
			case 0x5 => buf.getDouble
			case 0x6 => AmfString.read(buf, ref)
			case 0x7 => "" // TODO XMLDOC
			case 0x8 => AmfDate.read(buf, ref)
			case 0x9 => AmfArray.read(buf, ref)
			case 0xa => AmfObject.read(buf, ref)
			case 0xb => "" // TODO XML
			case 0xc => null // TODO BYTEARRAY
			case _ => throw new Exception("invalid amf type " + tp)
		}
	}

	def encode(value : Any) : IoBuffer =
	{
		val buf = IoBuffer.allocate(128).setAutoExpand(true)
		val ref = new Ref

		encode(buf, value, ref)

		buf.flip
		buf.position(0)
		buf
	}

	def encode(buf : IoBuffer, value : Any, ref : Ref) : IoBuffer =
	{
		value match
		{
			case null => buf.put(0x1 toByte)
			case false => buf.put(0x2 toByte)
			case true => buf.put(0x3 toByte)
			case int : Int => buf.put(0x4 toByte); AmfInt.write(buf, int)
			case double : Double => buf.put(0x5 toByte); buf.putDouble(double)
			case str : String => buf.put(0x6 toByte); AmfString.write(buf, str, ref)
			case date : Date => buf.put(0x8 toByte); AmfDate.write(buf, date, ref)
			case arr : ArrayList[Any] => buf.put(0x9 toByte); AmfArray.write(buf, arr, ref)
			case obj : AmfClass => buf.put(0xa toByte); AmfObject.write(buf, obj, ref)
			case _ => throw new Exception("invalid value " + value)
		}

		buf
	}
}
