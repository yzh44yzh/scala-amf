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
		assert((AmfType.INT, 0) === Amf.decode(buf_0))
		assert((AmfType.INT, 1) === Amf.decode(buf_1))
		assert((AmfType.INT, 55) === Amf.decode(buf_55))
		assert((AmfType.INT, 127) === Amf.decode(buf_127))
		assert((AmfType.INT, 128) === Amf.decode(buf_128))
		assert((AmfType.INT, 155) === Amf.decode(buf_155))
		assert((AmfType.INT, 555) === Amf.decode(buf_555))
		assert((AmfType.INT, 1555) === Amf.decode(buf_1555))
		assert((AmfType.INT, 16383) === Amf.decode(buf_16383))
		assert((AmfType.INT, 16384) === Amf.decode(buf_16384))
		assert((AmfType.INT, 2097151) === Amf.decode(buf_2097151))
		assert((AmfType.INT, 2097152) === Amf.decode(buf_2097152))
		assert((AmfType.INT, -1) === Amf.decode(buf_m1))
		assert((AmfType.INT, -55) === Amf.decode(buf_m55))
		assert((AmfType.INT, -555) === Amf.decode(buf_m555))
	}

	test("encode integer")
	{
		assert(buf_0 === Amf.encode((AmfType.INT, 0)))
		assert(buf_1 === Amf.encode((AmfType.INT, 1)))
		assert(buf_55 === Amf.encode((AmfType.INT, 55)))
		assert(buf_127 === Amf.encode((AmfType.INT, 127)))
		assert(buf_128 === Amf.encode((AmfType.INT, 128)))
		assert(buf_155 === Amf.encode((AmfType.INT, 155)))
		assert(buf_555 === Amf.encode((AmfType.INT, 555)))
		assert(buf_1555 === Amf.encode((AmfType.INT, 1555)))
		assert(buf_16383 === Amf.encode((AmfType.INT, 16383)))
		assert(buf_16384 === Amf.encode((AmfType.INT, 16384)))
		assert(buf_2097151 === Amf.encode((AmfType.INT, 2097151)))
		assert(buf_2097152 === Amf.encode((AmfType.INT, 2097152)))
		assert(buf_m1 === Amf.encode((AmfType.INT, -1)))
		assert(buf_m55 === Amf.encode((AmfType.INT, -55)))
		assert(buf_m555 === Amf.encode((AmfType.INT, -555)))
	}
}
