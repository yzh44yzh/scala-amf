/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf

import org.apache.mina.core.buffer.IoBuffer

object BufUtils
{
    def mkb(bytes: List[Byte]): IoBuffer =
    {
        val buf = IoBuffer.allocate(64).setAutoExpand(true)
        for(byte <- bytes) buf.put(byte)
        buf.flip
        buf.position(0)
        buf
    }

    def eq(buf1: IoBuffer, buf2: IoBuffer): Boolean =
    {
        buf1.position(0)
        buf2.position(0)

        while(buf1.hasRemaining) if(buf1.get != buf2.get) return false

        if(buf1.hasRemaining) return false
        if(buf2.hasRemaining) return false

        true
    }
}
