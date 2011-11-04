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
}
