/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package test

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite
import org.apache.mina.core.buffer.IoBuffer

class TestBufUtils extends FunSuite
{
	test("make buffer")
	{
		val buf = BufUtils.mkb(1, 2, 3)
		assert(buf.capacity() == 128)
		assert(buf.get === 1)
		assert(buf.get === 2)
		assert(buf.get === 3)
	}

	test("equals")
	{
		val buf1 = BufUtils.mkb(1, 2, 3, 4)
		val buf2 = BufUtils.mkb(1, 2, 3, 4)
		val buf3 = BufUtils.mkb(1, 2, 3, 4, 5)
		val buf4 = BufUtils.mkb(1, 2, 5)
		assert(BufUtils.eq(buf1, buf2))
		assert(!BufUtils.eq(buf1, buf3))
		assert(!BufUtils.eq(buf1, buf4))
	}

	test("copy")
	{
		val src = IoBuffer.allocate(64)
		src.put(1 toByte)
		src.put(2 toByte)
		src.put(3 toByte)
		src.flip

		val dst = IoBuffer.allocate(64)
		BufUtils.copy(src, dst)

		assert(dst.limit == 3)
		assert(dst.get(0) == 1)
		assert(dst.get(1) == 2)
		assert(dst.get(2) == 3)
	}

	test("merge")
	{
		val b1 = IoBuffer.allocate(64)
		b1.put(1 toByte)
		b1.put(2 toByte)
		b1.put(3 toByte)
		b1.flip

		val b2 = IoBuffer.allocate(64)
		b2.put(4 toByte)
		b2.put(5 toByte)
		b2.put(6 toByte)
		b2.flip

		val b3 = BufUtils.merge(b1, b2)
		assert(b3.limit == 6)
		assert(b3.get(0) == 1)
		assert(b3.get(1) == 2)
		assert(b3.get(2) == 3)
		assert(b3.get(3) == 4)
		assert(b3.get(4) == 5)
		assert(b3.get(5) == 6)
	}
}
