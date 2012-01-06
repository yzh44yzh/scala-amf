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
		assert(128 === buf.capacity())
		assert(1 === buf.get)
		assert(2 === buf.get)
		assert(3 === buf.get)
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

		assert(3 === dst.limit)
		assert(1 === dst.get(0))
		assert(2 === dst.get(1))
		assert(3 === dst.get(2))
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
		assert(0 === b3.position)
		assert(6 === b3.limit)
		assert(1 === b3.get(0))
		assert(2 === b3.get(1))
		assert(3 === b3.get(2))
		assert(4 === b3.get(3))
		assert(5 === b3.get(4))
		assert(6 === b3.get(5))
	}

	test("merge with null buffer")
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

		val res1 = BufUtils.merge(null, b2)
		assert(0 === res1.position)
		assert(3 === res1.limit)
		assert(4 === res1.get(0))
		assert(5 === res1.get(1))
		assert(6 === res1.get(2))

		val res2 = BufUtils.merge(b1, null)
		assert(0 === res2.position)
		assert(3 === res2.limit)
		assert(1 === res2.get(0))
		assert(2 === res2.get(1))
		assert(3 === res2.get(2))

		val res3 = BufUtils.merge(null, null)
		assert(0 === res3.limit)
	}

	test("getRest")
	{
		val b = IoBuffer.allocate(64)
		b.put(1 toByte)
		b.put(2 toByte)
		b.put(3 toByte)
		b.put(4 toByte)
		b.put(5 toByte)
		b.flip

		b.get; b.get
		assert(2 === b.position)

		val res = BufUtils.getRest(b)
		assert(0 === res.position)
		assert(3 === res.limit)
		assert(3 === res.get(0))
		assert(4 === res.get(1))
		assert(5 === res.get(2))

		assert(BufUtils.getRest(null) == null)

		b.get; b.get; b.get
		assert(BufUtils.getRest(b) == null)
	}
}
