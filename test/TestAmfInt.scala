/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite

class TestAmfInt extends FunSuite
{
    test("decode integer")
    {
        assert(Amf.decode(BufUtils.mkb(List(0x4, 0))) === (AmfType.INT, 0))
        assert(Amf.decode(BufUtils.mkb(List(0x4, 0x1))) === (AmfType.INT, 1))
        assert(Amf.decode(BufUtils.mkb(List(0x4, 0x37))) === (AmfType.INT, 55))
        assert(Amf.decode(BufUtils.mkb(List(0x4, 0x7f))) === (AmfType.INT, 127))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x7f, 0))) === (AmfType.INT, 128))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x7f, 0x1b))) === (AmfType.INT, 155))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x7c, 0x2b))) === (AmfType.INT, 555))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x74, 0x13))) === (AmfType.INT, 1555))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, 0x7f))) === (AmfType.INT, 16383))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x7f, -0x80, 0))) === (AmfType.INT, 16384))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, -0x1, 0x7f))) === (AmfType.INT, 2097151))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x80, -0x40, -0x80, 0))) === (AmfType.INT, 2097152))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, -0x1, -0x1, -0x1))) === (AmfType.INT, -1))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, -0x1, -0x1, -0x37))) === (AmfType.INT, -55))
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, -0x1, -0x3, -0x2b))) === (AmfType.INT, -555))
    }
}
