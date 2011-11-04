/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf

import org.apache.mina.core.buffer.IoBuffer
import AmfType._
import java.util.Date

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
            case 0x6 => (AmfType.STRING, "") // TODO STRING
            case 0x7 => (AmfType.STRING, "") // TODO XMLDOC
            case 0x8 => (AmfType.DATE, readDate(buf))
            case 0x9 => (AmfType.ARRAY, 0) // TODO ARRAY
            case 0xa => (AmfType.OBJECT, 0) // TODO OBJECT
            case 0xb => (AmfType.STRING, 0) // TODO XML
            case 0xc => (AmfType.BYTEARRAY, 0) // TODO BYTEARRAY
            case _ => throw new Exception("invalid amf type " + tp)
        }
    }

    def encode(value : (AmfType, Any)) : IoBuffer =
    {
        var buf = IoBuffer.allocate(64).setAutoExpand(true)

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
            case AmfType.DATE =>
                buf.put(0x8 toByte)
                writeDate(buf,  value._2.asInstanceOf[Date])
            case _ => throw new Exception("invalid amf type " + value._1)
        }

        buf.flip
        buf.position(0)
        buf
    }

    def readDate(buf : IoBuffer) : Date =
    {
        val ref = AmfInt.read(buf)

        /*if((ref & 1) == 0)
        {
            // return getReference(ref >> 1).asInstanceOf[Date]
        }*/

        val date = new Date(buf.getDouble.asInstanceOf[Long])
        //storeReference(date)
        date
    }

    def writeDate(buf : IoBuffer, date : Date) =
    {
        /*if(hasReference(date))
        {
            putInteger(getReferenceId(date) << 1)
            return
        }*/
        //storeReference(date)

        buf.put(0x1 toByte)
        buf.putDouble(date.getTime)
    }
}
