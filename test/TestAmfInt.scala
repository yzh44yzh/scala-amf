/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite

class TestAmfInt extends FunSuite
{
    test("decode integer")
    {
        assert(Amf.decode(BufUtils.mkb(List(0x4, 0))) === 0)
        assert(Amf.decode(BufUtils.mkb(List(0x4, 0x1))) === 1)
        assert(Amf.decode(BufUtils.mkb(List(0x4, 0x37))) === 55)
        assert(Amf.decode(BufUtils.mkb(List(0x4, 0x7f))) === 127)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x7f, 0))) === 128)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x7f, 0x1b))) === 155)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x7c, 0x2b))) === 555)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x74, 0x13))) === 1555)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, 0x7f))) === 16383)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x7f, -0x80, 0))) === 16384)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, -0x1, 0x7f))) === 2097151)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x80, -0x40, -0x80, 0))) === 2097152)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, -0x1, -0x1, -0x1))) === -1)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, -0x1, -0x1, -0x37))) === -55)
        assert(Amf.decode(BufUtils.mkb(List(0x4, -0x1, -0x1, -0x3, -0x2b))) === -555)
    }
}
