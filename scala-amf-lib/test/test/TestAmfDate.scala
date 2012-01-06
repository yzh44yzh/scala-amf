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
	test("test Sun Nov 14 22:44:00 GMT+0200 2010")
	{
		val dt = new Date(1289767440000L)
		val buf : IoBuffer = BufUtils.mkb(0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x0, 0x0)

		assert(dt === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(dt))
	}

	test("test Thu Feb 2 03:04:00 GMT+0200 1995")
	{
		val dt = new Date(791687040000L)
		val buf : IoBuffer = BufUtils.mkb(0x08, 0x01, 0x42, 0x67, 0x0a, -0x79, 0x31, -0x80, 0x0, 0x0)

		assert(dt === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(dt))
	}

	test("test Wed Jun 4 03:02:01 GMT+0200 1980")
	{
		val dt = new Date(328928521000L)
		val buf : IoBuffer = BufUtils.mkb(0x08, 0x01, 0x42, 0x53, 0x25, 0x6a, -0x46, -0x36, 0x0, 0x0)

		assert(dt === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(dt))
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
		val buf : IoBuffer = BufUtils.mkb(0x9, 0x5, 0x1,
			0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00,
			0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00)

		assert(arr === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(arr))
	}

	test("null dates")
	{
		val dt1 = null
		val dt2 = null

		val w1 = new IdentityWrapper(dt1)
		val w2 = new IdentityWrapper(dt2)

		assert(!w1.equals(w2))

		val arr = new ArrayList(Arrays.asList(dt1, dt2))
		val buf : IoBuffer = BufUtils.mkb(0x9, 0x5, 0x1, 0x1, 0x1)

		assert(arr === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(arr))
	}
}
