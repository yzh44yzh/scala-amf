/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package test

import com.yzh44yzh.scalaAmf._
import org.apache.mina.core.buffer.IoBuffer
import org.scalatest.FunSuite

class TestAmfInt extends FunSuite
{
	val buf_0 : IoBuffer = BufUtils.mkb(0x4, 0)
	val buf_1 : IoBuffer = BufUtils.mkb(0x4, 0x1)
	val buf_55 : IoBuffer = BufUtils.mkb(0x4, 0x37)
	val buf_127 : IoBuffer = BufUtils.mkb(0x4, 0x7f)
	val buf_128 : IoBuffer = BufUtils.mkb(0x4, -0x7f, 0)
	val buf_155 : IoBuffer = BufUtils.mkb(0x4, -0x7f, 0x1b)
	val buf_555 : IoBuffer = BufUtils.mkb(0x4, -0x7c, 0x2b)
	val buf_1555 : IoBuffer = BufUtils.mkb(0x4, -0x74, 0x13)
	val buf_16383 : IoBuffer = BufUtils.mkb(0x4, -0x1, 0x7f)
	val buf_16384 : IoBuffer = BufUtils.mkb(0x4, -0x7f, -0x80, 0)
	val buf_2097151 : IoBuffer = BufUtils.mkb(0x4, -0x1, -0x1, 0x7f)
	val buf_2097152 : IoBuffer = BufUtils.mkb(0x4, -0x80, -0x40, -0x80, 0)
	val buf_m1 : IoBuffer = BufUtils.mkb(0x4, -0x1, -0x1, -0x1, -0x1)
	val buf_m55 : IoBuffer = BufUtils.mkb(0x4, -0x1, -0x1, -0x1, -0x37)
	val buf_m555 : IoBuffer = BufUtils.mkb(0x4, -0x1, -0x1, -0x3, -0x2b)

	test("decode integer")
	{
		assert(0 === Amf.decode(buf_0))
		assert(1 === Amf.decode(buf_1))
		assert(55 === Amf.decode(buf_55))
		assert(127 === Amf.decode(buf_127))
		assert(128 === Amf.decode(buf_128))
		assert(155 === Amf.decode(buf_155))
		assert(555 === Amf.decode(buf_555))
		assert(1555 === Amf.decode(buf_1555))
		assert(16383 === Amf.decode(buf_16383))
		assert(16384 === Amf.decode(buf_16384))
		assert(2097151 === Amf.decode(buf_2097151))
		assert(2097152 === Amf.decode(buf_2097152))
		assert(-1 === Amf.decode(buf_m1))
		assert(-55 === Amf.decode(buf_m55))
		assert(-555 === Amf.decode(buf_m555))
	}

	test("encode integer")
	{
		assert(buf_0 === Amf.encode(0))
		assert(buf_1 === Amf.encode(1))
		assert(buf_55 === Amf.encode(55))
		assert(buf_127 === Amf.encode(127))
		assert(buf_128 === Amf.encode(128))
		assert(buf_155 === Amf.encode(155))
		assert(buf_555 === Amf.encode(555))
		assert(buf_1555 === Amf.encode(1555))
		assert(buf_16383 === Amf.encode(16383))
		assert(buf_16384 === Amf.encode(16384))
		assert(buf_2097151 === Amf.encode(2097151))
		assert(buf_2097152 === Amf.encode(2097152))
		assert(buf_m1 === Amf.encode(-1))
		assert(buf_m55 === Amf.encode(-55))
		assert(buf_m555 === Amf.encode(-555))
	}
}
