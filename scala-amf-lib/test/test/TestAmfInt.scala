/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package test

import com.yzh44yzh.scalaAmf._
import org.apache.mina.core.buffer.IoBuffer
import org.scalatest.FunSuite

class TestAmfInt extends FunSuite
{
    val buf_0: IoBuffer = BufUtils.mkb(List(0x4, 0))
    val buf_1: IoBuffer = BufUtils.mkb(List(0x4, 0x1))
    val buf_55: IoBuffer = BufUtils.mkb(List(0x4, 0x37))
    val buf_127: IoBuffer = BufUtils.mkb(List(0x4, 0x7f))
    val buf_128: IoBuffer = BufUtils.mkb(List(0x4, -0x7f, 0))
    val buf_155: IoBuffer = BufUtils.mkb(List(0x4, -0x7f, 0x1b))
    val buf_555: IoBuffer = BufUtils.mkb(List(0x4, -0x7c, 0x2b))
    val buf_1555: IoBuffer = BufUtils.mkb(List(0x4, -0x74, 0x13))
    val buf_16383: IoBuffer = BufUtils.mkb(List(0x4, -0x1, 0x7f))
    val buf_16384: IoBuffer = BufUtils.mkb(List(0x4, -0x7f, -0x80, 0))
    val buf_2097151: IoBuffer = BufUtils.mkb(List(0x4, -0x1, -0x1, 0x7f))
    val buf_2097152: IoBuffer = BufUtils.mkb(List(0x4, -0x80, -0x40, -0x80, 0))
    val buf_m1: IoBuffer = BufUtils.mkb(List(0x4, -0x1, -0x1, -0x1, -0x1))
    val buf_m55: IoBuffer = BufUtils.mkb(List(0x4, -0x1, -0x1, -0x1, -0x37))
    val buf_m555: IoBuffer = BufUtils.mkb(List(0x4, -0x1, -0x1, -0x3, -0x2b))

    test("decode integer")
    {
        assert(Amf.decode(buf_0) === (AmfType.INT, 0))
        assert(Amf.decode(buf_1) === (AmfType.INT, 1))
        assert(Amf.decode(buf_55) === (AmfType.INT, 55))
        assert(Amf.decode(buf_127) === (AmfType.INT, 127))
        assert(Amf.decode(buf_128) === (AmfType.INT, 128))
        assert(Amf.decode(buf_155) === (AmfType.INT, 155))
        assert(Amf.decode(buf_555) === (AmfType.INT, 555))
        assert(Amf.decode(buf_1555) === (AmfType.INT, 1555))
        assert(Amf.decode(buf_16383) === (AmfType.INT, 16383))
        assert(Amf.decode(buf_16384) === (AmfType.INT, 16384))
        assert(Amf.decode(buf_2097151) === (AmfType.INT, 2097151))
        assert(Amf.decode(buf_2097152) === (AmfType.INT, 2097152))
        assert(Amf.decode(buf_m1) === (AmfType.INT, -1))
        assert(Amf.decode(buf_m55) === (AmfType.INT, -55))
        assert(Amf.decode(buf_m555) === (AmfType.INT, -555))
    }

    test("encode integer")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 0)), buf_0))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 1)), buf_1))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 55)), buf_55))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 127)), buf_127))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 128)), buf_128))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 155)), buf_155))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 555)), buf_555))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 1555)), buf_1555))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 16383)), buf_16383))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 16384)), buf_16384))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 2097151)), buf_2097151))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, 2097152)), buf_2097152))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, -1)), buf_m1))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, -55)), buf_m55))
        assert(BufUtils.eq(Amf.encode((AmfType.INT, -555)), buf_m555))
    }
}
