/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite

class TestAmfBase extends FunSuite
{
    test("decode base types")
    {
        assert(Amf.decode(BufUtils.mkb(List(0x0))) === (AmfType.NULL, null))
        assert(Amf.decode(BufUtils.mkb(List(0x1))) === (AmfType.NULL, null))
        assert(Amf.decode(BufUtils.mkb(List(0x2))) === (AmfType.BOOL, false))
        assert(Amf.decode(BufUtils.mkb(List(0x3))) === (AmfType.BOOL, true))
    }

    test("encode base types")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.NULL, null)), BufUtils.mkb(List(0x1))))
        assert(BufUtils.eq(Amf.encode((AmfType.BOOL, false)), BufUtils.mkb(List(0x2))))
        assert(BufUtils.eq(Amf.encode((AmfType.BOOL, true)), BufUtils.mkb(List(0x3))))
    }
}
