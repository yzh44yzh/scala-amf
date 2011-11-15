package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.buffer.IoBuffer

object BufUtils
{
    def mkb(bytes : List[Byte]) : IoBuffer =
    {
        val buf = IoBuffer.allocate(64).setAutoExpand(true)
        for(byte <- bytes) buf.put(byte)
        buf.flip
        buf.position(0)
        buf
    }

    def mkBufFromArray(bytes : Array[Byte]) : IoBuffer =
    {
        val buf = IoBuffer.allocate(64).setAutoExpand(true)
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
}
