/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package test

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite

class TestAmfBase extends FunSuite
{
	test("test undefined")
	{
		val buf = BufUtils.mkb(0x0)

		assert((AmfType.NULL, null) === Amf.decode(buf))
	}

	test("test null")
	{
		val buf = BufUtils.mkb(0x1)

		assert((AmfType.NULL, null) === Amf.decode(buf))
		assert(buf === Amf.encode((AmfType.NULL, null)))
	}

	test("test false")
	{
		val buf = BufUtils.mkb(0x2)

		assert((AmfType.BOOL, false) === Amf.decode(buf))
		assert(buf === Amf.encode((AmfType.BOOL, false)))
	}

	test("test true")
	{
		val buf = BufUtils.mkb(0x3)

		assert((AmfType.BOOL, true) === Amf.decode(buf))
		assert(buf === Amf.encode((AmfType.BOOL, true)))
	}
}
