/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package test

import com.yzh44yzh.scalaAmf._
import org.apache.mina.core.buffer.IoBuffer
import org.scalatest.FunSuite
import java.util.{Arrays, ArrayList, Date}

class TestAmfDate extends FunSuite
{
    val dt1 = new Date(1289767440000L) // Sun Nov 14 22:44:00 GMT+0200 2010
    val dt2 = new Date(791687040000L)  // Thu Feb  2 03:04:00 GMT+0200 1995
    val dt3 = new Date(328928521000L)  // Wed Jun  4 03:02:01 GMT+0200 1980

    val buf1: IoBuffer = BufUtils.mkb(0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00)
    val buf2: IoBuffer = BufUtils.mkb(0x08, 0x01, 0x42, 0x67, 0x0a, -0x79, 0x31, -0x80, 0x00, 0x0)
    val buf3: IoBuffer = BufUtils.mkb(0x08, 0x01, 0x42, 0x53, 0x25, 0x6a, -0x46, -0x36, 0x00, 0x00)

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

    test("equal dates")
    {
        val dt1 = new Date(1289767440000L)
        val dt2 = new Date(1289767440000L)
        val dt3 = dt1

        val w1 = new IdentityWrapper(dt1)
        val w2 = new IdentityWrapper(dt2)
        val w3 = new IdentityWrapper(dt3)

        assert(w1.hashCode != w2.hashCode)
        assert(w1.hashCode == w3.hashCode)
        assert(!w1.equals(w2))
        assert(w1.equals(w3))

        val arr = new ArrayList(Arrays.asList(dt1, dt2))

        val buf: IoBuffer = BufUtils.mkb(0x9, 0x5, 0x1,
            0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00,
            0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00
        )

        val (AmfType.ARRAY, res) = Amf.decode(buf)
        assert(arr.equals(res))

        val str = BufUtils.diff(Amf.encode((AmfType.ARRAY, arr)), buf)
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, arr)), buf))
    }

    test("null dates")
    {
        val dt1 = null
        val dt2 = null

        val w1 = new IdentityWrapper(dt1)
        val w2 = new IdentityWrapper(dt2)

        assert(!w1.equals(w2))

        val arr = new ArrayList(Arrays.asList(dt1, dt2))

        val buf: IoBuffer = BufUtils.mkb(0x9, 0x5, 0x1, 0x1, 0x1)

        val (AmfType.ARRAY, res) = Amf.decode(buf)
        assert(arr.equals(res))

        val str = BufUtils.diff(Amf.encode((AmfType.ARRAY, arr)), buf)
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, arr)), buf))
    }
}
