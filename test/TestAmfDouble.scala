/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.apache.mina.core.buffer.IoBuffer
import org.scalatest.FunSuite

class TestAmfDouble extends FunSuite
{
    val buf_0_01: IoBuffer = BufUtils.mkb(List(0x5, 0x3f, -0x7c, 0x7a, -0x1f, 0x47, -0x52, 0x14, 0x7b))
    val buf_0_1: IoBuffer = BufUtils.mkb(List(0x5, 0x3f, -0x47, -0x67, -0x67, -0x67, -0x67, -0x67, -0x66))
    val buf_0_5: IoBuffer = BufUtils.mkb(List(0x5, 0x3f, -0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
    val buf_1_33: IoBuffer = BufUtils.mkb(List(0x5, 0x3f, -0xb, 0x47, -0x52, 0x14, 0x7a, -0x1f, 0x48))
    val buf_250_25: IoBuffer = BufUtils.mkb(List(0x5, 0x40, 0x6f, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00))
    val buf_m250_25: IoBuffer = BufUtils.mkb(List(0x5, -0x40, 0x6f, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00))
    val buf_9: IoBuffer = BufUtils.mkb(List(0x5, 0x41, -0x69, -0x29, -0x7d, -0x1, -0xb, -0x3e, -0x71))
    val buf_m9: IoBuffer = BufUtils.mkb(List(0x5, -0x3f, -0x69, -0x29, -0x7d, -0x1, -0xb, -0x3e, -0x71))

    test("decode double")
    {
        assert(Amf.decode(buf_0_01) === (AmfType.DOUBLE, 0.01))
        assert(Amf.decode(buf_0_1) === (AmfType.DOUBLE, 0.1))
        assert(Amf.decode(buf_0_5) === (AmfType.DOUBLE, 0.5))
        assert(Amf.decode(buf_1_33) === (AmfType.DOUBLE, 1.33))
        assert(Amf.decode(buf_250_25) === (AmfType.DOUBLE, 250.25))
        assert(Amf.decode(buf_m250_25) === (AmfType.DOUBLE, -250.25))
        assert(Amf.decode(buf_9) === (AmfType.DOUBLE, 99999999.99))
        assert(Amf.decode(buf_m9) === (AmfType.DOUBLE, -99999999.99))
    }

    test("encode double")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.DOUBLE, 0.01)), buf_0_01))
        assert(BufUtils.eq(Amf.encode((AmfType.DOUBLE, 0.1)), buf_0_1))
        assert(BufUtils.eq(Amf.encode((AmfType.DOUBLE, 0.5)), buf_0_5))
        assert(BufUtils.eq(Amf.encode((AmfType.DOUBLE, 1.33)), buf_1_33))
        assert(BufUtils.eq(Amf.encode((AmfType.DOUBLE, 250.25)), buf_250_25))
        assert(BufUtils.eq(Amf.encode((AmfType.DOUBLE, -250.25)), buf_m250_25))
        assert(BufUtils.eq(Amf.encode((AmfType.DOUBLE, 99999999.99)), buf_9))
        assert(BufUtils.eq(Amf.encode((AmfType.DOUBLE, -99999999.99)), buf_m9))
    }
}
