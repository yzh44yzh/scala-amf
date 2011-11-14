/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package test

import com.yzh44yzh.scalaAmf._
import java.util.Date
import org.apache.mina.core.buffer.IoBuffer
import org.scalatest.FunSuite

class TestAmfDate extends FunSuite
{
    val dt1 = new Date(1289767440000L) // Sun Nov 14 22:44:00 GMT+0200 2010
    val dt2 = new Date(791687040000L)  // Thu Feb  2 03:04:00 GMT+0200 1995
    val dt3 = new Date(328928521000L)  // Wed Jun  4 03:02:01 GMT+0200 1980

    val buf1: IoBuffer = BufUtils.mkb(List(0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00))
    val buf2: IoBuffer = BufUtils.mkb(List(0x08, 0x01, 0x42, 0x67, 0x0a, -0x79, 0x31, -0x80, 0x00, 0x0))
    val buf3: IoBuffer = BufUtils.mkb(List(0x08, 0x01, 0x42, 0x53, 0x25, 0x6a, -0x46, -0x36, 0x00, 0x00))

    test("decode date")
    {
        val (AmfType.DATE, res1) = Amf.decode(buf1)
        val (AmfType.DATE, res2) = Amf.decode(buf2)
        val (AmfType.DATE, res3) = Amf.decode(buf3)

        assert(dt1.equals(res1))
        assert(dt2.equals(res2))
        assert(dt3.equals(res3))
    }

    test("encode date")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.DATE, dt1)), buf1))
        assert(BufUtils.eq(Amf.encode((AmfType.DATE, dt2)), buf2))
        assert(BufUtils.eq(Amf.encode((AmfType.DATE, dt3)), buf3))
    }
}
