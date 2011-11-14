/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf

import org.apache.mina.core.buffer.IoBuffer
import AmfType._
import java.util.{ArrayList, Date}

object Amf
{
    def decode(buf : IoBuffer) : (AmfType, Any) =
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
            case 0x6 => (AmfType.STRING, AmfString.read(buf))
            case 0x7 => (AmfType.STRING, "") // TODO XMLDOC
            case 0x8 => (AmfType.DATE, AmfDate.read(buf))
            case 0x9 => (AmfType.ARRAY, AmfArray.read(buf))
            case 0xa => (AmfType.OBJECT, 0) // TODO OBJECT
            case 0xb => (AmfType.STRING, 0) // TODO XML
            case 0xc => (AmfType.BYTEARRAY, 0) // TODO BYTEARRAY
            case _ => throw new Exception("invalid amf type " + tp)
        }
    }

    def encode(value : (AmfType, Any)) : IoBuffer =
    {
        val buf = IoBuffer.allocate(64).setAutoExpand(true)

        encode(buf, value)

        buf.flip
        buf.position(0)
        buf
    }

    def encode(buf : IoBuffer, value : (AmfType, Any)) : IoBuffer =
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
                AmfString.write(buf,  value._2.asInstanceOf[String])
            case AmfType.DATE =>
                buf.put(0x8 toByte)
                AmfDate.write(buf,  value._2.asInstanceOf[Date])
            case AmfType.ARRAY =>
                buf.put(0x9 toByte)
                AmfArray.write(buf,  value._2.asInstanceOf[ArrayList[Any]])
            case _ => throw new Exception("invalid amf type " + value._1)
        }

        buf
    }

    def encodeAny(buf : IoBuffer,  item : Any) =
    {
        item match
        {
            case null => encode(buf, (AmfType.NULL, null))
            case false => encode(buf, (AmfType.BOOL, false))
            case true => encode(buf, (AmfType.BOOL, true))
            case int : Int => encode(buf, (AmfType.INT, int))
            case double : Double => encode(buf, (AmfType.DOUBLE, double))
            case str : String => encode(buf, (AmfType.STRING, str))
            case date : Date => encode(buf, (AmfType.DATE, date))
            case list : ArrayList[Any] => encode(buf, (AmfType.ARRAY, list))
        }
    }
}
