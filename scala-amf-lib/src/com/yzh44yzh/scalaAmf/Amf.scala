package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer
import com.yzh44yzh.scalaAmf.AmfType._
import java.util.{ArrayList, Date}

object Amf
{
	def decode(buf : IoBuffer) : (AmfType, Any) =
	{
		val ref = new Ref
		var res = decode(buf, ref)
		buf.position(0) // restore side effect
		res
	}

	def decode(buf : IoBuffer, ref : Ref) : (AmfType, Any) =
	{
		val tp = buf.get
		tp match
		{
			case 0x0 => (AmfType.NULL, null) // UNDEFINED
			case 0x1 => (AmfType.NULL, null) // NULL
			case 0x2 => (AmfType.BOOL, false)
			case 0x3 => (AmfType.BOOL, true)
			case 0x4 => (AmfType.INT, AmfInt.read(buf))
			case 0x5 => (AmfType.DOUBLE, buf.getDouble)
			case 0x6 => (AmfType.STRING, AmfString.read(buf, ref))
			case 0x7 => (AmfType.STRING, "") // TODO XMLDOC
			case 0x8 => (AmfType.DATE, AmfDate.read(buf, ref))
			case 0x9 => (AmfType.ARRAY, AmfArray.read(buf, ref))
			case 0xa => (AmfType.OBJECT, AmfObject.read(buf, ref))
			case 0xb => (AmfType.STRING, 0) // TODO XML
			case 0xc => (AmfType.BYTEARRAY, 0) // TODO BYTEARRAY
			case _ => throw new Exception("invalid amf type " + tp)
		}
	}

	def encode(value : (AmfType, Any)) : IoBuffer =
	{
		val buf = IoBuffer.allocate(128).setAutoExpand(true)
		val ref = new Ref

		encode(buf, value, ref)

		buf.flip
		buf.position(0)
		buf
	}

	def encode(buf : IoBuffer, value : (AmfType, Any), ref : Ref) : IoBuffer =
	{
		value._1 match
		{
			case AmfType.NULL => buf.put(0x1 toByte)
			case AmfType.BOOL =>
				buf.put((if(value._2.asInstanceOf[Boolean]) 0x3 else 0x2) toByte)
			case AmfType.INT =>
				buf.put(0x4 toByte);
				AmfInt.write(buf, value._2.asInstanceOf[Int])
			case AmfType.DOUBLE =>
				buf.put(0x5 toByte);
				buf.putDouble(value._2.asInstanceOf[Double])
			case AmfType.STRING =>
				buf.put(0x6 toByte)
				AmfString.write(buf, value._2.asInstanceOf[String], ref)
			case AmfType.DATE =>
				buf.put(0x8 toByte)
				AmfDate.write(buf, value._2.asInstanceOf[Date], ref)
			case AmfType.ARRAY =>
				buf.put(0x9 toByte)
				AmfArray.write(buf, value._2.asInstanceOf[ArrayList[Any]], ref)
			case AmfType.OBJECT =>
				buf.put(0xa toByte)
				AmfObject.write(buf, value._2.asInstanceOf[AmfClass], ref)
			case _ => throw new Exception("invalid amf type " + value._1)
		}

		buf
	}

	def encodeAny(buf : IoBuffer, item : Any, ref : Ref) : IoBuffer =
	{
		item match
		{
			case null => encode(buf, (AmfType.NULL, null), ref)
			case false => encode(buf, (AmfType.BOOL, false), ref)
			case true => encode(buf, (AmfType.BOOL, true), ref)
			case int : Int => encode(buf, (AmfType.INT, int), ref)
			case double : Double => encode(buf, (AmfType.DOUBLE, double), ref)
			case str : String => encode(buf, (AmfType.STRING, str), ref)
			case date : Date => encode(buf, (AmfType.DATE, date), ref)
			case list : ArrayList[Any] => encode(buf, (AmfType.ARRAY, list), ref)
			case obj : AmfClass => encode(buf, (AmfType.OBJECT, obj), ref)
		}

		buf
	}
}
