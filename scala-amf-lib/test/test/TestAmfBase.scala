/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package test

import com.yzh44yzh.scalaAmf.{Amf, BufUtils}
import org.scalatest.FunSuite

class TestAmfBase extends FunSuite
{
	test("test undefined")
	{
		val buf = BufUtils.mkb(0x0)
		assert(null == Amf.decode(buf))
	}

	test("test null")
	{
		val buf = BufUtils.mkb(0x1)

		assert(null == Amf.decode(buf))
		assert(buf === Amf.encode(null))
	}

	test("test false")
	{
		val buf = BufUtils.mkb(0x2)

		assert(false === Amf.decode(buf))
		assert(buf === Amf.encode(false))
	}

	test("test true")
	{
		val buf = BufUtils.mkb(0x3)

		assert(true === Amf.decode(buf))
		assert(buf === Amf.encode(true))
	}
}
