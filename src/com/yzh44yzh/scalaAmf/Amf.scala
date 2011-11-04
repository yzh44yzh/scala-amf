/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf

import org.apache.mina.core.buffer.IoBuffer

object Amf
{
    def decode(buf : IoBuffer) : Int =
    {
        val tp = buf.get
        tp match
        {
            case 0x0 => 0 // TODO UNDEFINED
            case 0x1 => 0 // TODO NULL
            case 0x2 => 0 // TODO FALSE
            case 0x3 => 0 // TODO TRUE
            case 0x4 => AmfInt.readInt(buf)
            case 0x5 => 0 // TODO DOUBLE
            case 0x6 => 0 // TODO STRING
            case 0x7 => 0 // TODO XMLDOC
            case 0x8 => 0 // TODO DATE
            case 0x9 => 0 // TODO ARRAY
            case 0xa => 0 // TODO OBJECT
            case 0xb => 0 // TODO XML
            case 0xc => 0 // TODO BYTEARRAY
            case _ => throw new Exception("invalid amf type " + tp)
        }
    }

}
