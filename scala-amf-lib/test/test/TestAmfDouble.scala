/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package test

import com.yzh44yzh.scalaAmf._
import org.apache.mina.core.buffer.IoBuffer
import org.scalatest.FunSuite

class TestAmfDouble extends FunSuite
{
	val buf_0_01 : IoBuffer = BufUtils.mkb(0x5, 0x3f, -0x7c, 0x7a, -0x1f, 0x47, -0x52, 0x14, 0x7b)
	val buf_0_1 : IoBuffer = BufUtils.mkb(0x5, 0x3f, -0x47, -0x67, -0x67, -0x67, -0x67, -0x67, -0x66)
	val buf_0_5 : IoBuffer = BufUtils.mkb(0x5, 0x3f, -0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
	val buf_1_33 : IoBuffer = BufUtils.mkb(0x5, 0x3f, -0xb, 0x47, -0x52, 0x14, 0x7a, -0x1f, 0x48)
	val buf_250_25 : IoBuffer = BufUtils.mkb(0x5, 0x40, 0x6f, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00)
	val buf_m250_25 : IoBuffer = BufUtils.mkb(0x5, -0x40, 0x6f, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00)
	val buf_9 : IoBuffer = BufUtils.mkb(0x5, 0x41, -0x69, -0x29, -0x7d, -0x1, -0xb, -0x3e, -0x71)
	val buf_m9 : IoBuffer = BufUtils.mkb(0x5, -0x3f, -0x69, -0x29, -0x7d, -0x1, -0xb, -0x3e, -0x71)

	test("decode double")
	{
		assert(0.01 === Amf.decode(buf_0_01))
		buf_0_01.position(0)

		assert(0.1 === Amf.decode(buf_0_1))
		buf_0_1.position(0)

		assert(0.5 === Amf.decode(buf_0_5))
		buf_0_5.position(0)

		assert(1.33 === Amf.decode(buf_1_33))
		buf_1_33.position(0)

		assert(250.25 === Amf.decode(buf_250_25))
		buf_250_25.position(0)

		assert(-250.25 === Amf.decode(buf_m250_25))
		buf_m250_25.position(0)

		assert(99999999.99 === Amf.decode(buf_9))
		buf_9.position(0)

		assert(-99999999.99 === Amf.decode(buf_m9))
		buf_m9.position(0)
	}

	test("encode double")
	{
		assert(buf_0_01 === Amf.encode(0.01))
		assert(buf_0_1 === Amf.encode(0.1))
		assert(buf_0_5 === Amf.encode(0.5))
		assert(buf_1_33 === Amf.encode(1.33))
		assert(buf_250_25 === Amf.encode(250.25))
		assert(buf_m250_25 === Amf.encode(-250.25))
		assert(buf_9 === Amf.encode(99999999.99))
		assert(buf_m9 === Amf.encode(-99999999.99))

		val long : Long = 5000000000L
		val double : Double = 5000000000.0

		assert(Amf.encode(long) === Amf.encode(double))
	}
}
