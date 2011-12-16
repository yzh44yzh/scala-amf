/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package test

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite

class TestAmfBase extends FunSuite
{
	test("decode base types")
	{
		assert(Amf.decode(BufUtils.mkb(0x0)) === (AmfType.NULL, null))
		assert(Amf.decode(BufUtils.mkb(0x1)) === (AmfType.NULL, null))
		assert(Amf.decode(BufUtils.mkb(0x2)) === (AmfType.BOOL, false))
		assert(Amf.decode(BufUtils.mkb(0x3)) === (AmfType.BOOL, true))
	}

	test("encode base types")
	{
		assert(BufUtils.eq(Amf.encode((AmfType.NULL, null)), BufUtils.mkb(0x1)))
		assert(BufUtils.eq(Amf.encode((AmfType.BOOL, false)), BufUtils.mkb(0x2)))
		assert(BufUtils.eq(Amf.encode((AmfType.BOOL, true)), BufUtils.mkb(0x3)))
	}
}
