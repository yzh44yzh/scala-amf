/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf

import org.apache.mina.core.buffer.IoBuffer
import AmfType._

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
            case 0x8 => (AmfType.DATE, 0) // TODO DATE
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
            case AmfType.BOOL => buf.put((if(value._2.asInstanceOf[Boolean]) 0x3 else 0x2) toByte)
            case AmfType.INT => buf.put(0x4 toByte); buf.put(0 toByte)
            case _ => throw new Exception("invalid amf type " + value._1)
        }

        buf.flip
        buf.position(0)
        buf
    }
}
