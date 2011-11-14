package test

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite
import java.util.{ArrayList, Arrays}

class TestAmfArray extends FunSuite
{
    val boolsList = new ArrayList(Arrays.asList(true, true, false))
    val boolsBuf = BufUtils.mkb(List(0x09, 0x07, 0x01, 0x03, 0x03, 0x02))

    val intsList = new ArrayList(Arrays.asList(1, 2, 3, -4))
    val intsBuf = BufUtils.mkb(List(0x09, 0x09, 0x01,
			0x04, 0x01,
			0x04, 0x02,
			0x04, 0x03,
			0x04, -0x1, -0x1, -0x1, -0x4))

    val doublesList = new ArrayList(Arrays.asList(0.5, 1.5, 100.15))
    val doublesBuf = BufUtils.mkb(List(0x09, 0x07, 0x01,
			0x05, 0x3f, -0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x05, 0x3f, -0x8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x05, 0x40, 0x59, 0x09, -0x67, -0x67, -0x67, -0x67, -0x66))

    val stringsList = new ArrayList(Arrays.asList("a", "b", "c", "Hello"))
    val stringsBuf = BufUtils.mkb(List(0x09, 0x09, 0x01,
			0x06, 0x03, 0x61,
			0x06, 0x03, 0x62,
			0x06, 0x03, 0x63,
			0x06, 0x0b, 0x48, 0x65, 0x6c, 0x6c, 0x6f))

    val anyList = new ArrayList(Arrays.asList(1, "Hello", 35.25, true))
    val anyBuf = BufUtils.mkb(List(0x9, 0x9, 0x1,
            0x4, 0x1,
            0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f,
            0x5, 0x40, 0x41, -0x60, 0x0, 0x0, 0x0, 0x0, 0x0,
            0x3))

    val arrList = new ArrayList(Arrays.asList(
                    new ArrayList(Arrays.asList(1, 2, 3)),
                    new ArrayList(Arrays.asList("a", "b", "c")),
                    new ArrayList(Arrays.asList("Hello"))
                 ))
    val arrBuf = BufUtils.mkb(List(0x9, 0x7, 0x1,
            0x9, 0x7, 0x1,
                0x4, 0x1, 0x4, 0x2, 0x4, 0x3,
            0x9, 0x7, 0x1,
                0x6, 0x3, 0x61, 0x6, 0x3, 0x62, 0x6, 0x3, 0x63,
            0x9, 0x3, 0x1,
                0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f))

    test("decode arrays")
    {
        val (AmfType.ARRAY, res1) = Amf.decode(boolsBuf)
        assert(boolsList.equals(res1))

        val (AmfType.ARRAY, res2) = Amf.decode(intsBuf)
        assert(intsList.equals(res2))

        val (AmfType.ARRAY, res3) = Amf.decode(doublesBuf)
        assert(doublesList.equals(res3))

        val (AmfType.ARRAY, res4) = Amf.decode(stringsBuf)
        assert(stringsList.equals(res4))

        val (AmfType.ARRAY, res5) = Amf.decode(anyBuf)
        assert(anyList.equals(res5))

        val (AmfType.ARRAY, res6) = Amf.decode(arrBuf)
        assert(arrList.equals(res6))
    }

    test("encode arrays")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, boolsList)), boolsBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, intsList)), intsBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, doublesList)), doublesBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, stringsList)), stringsBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, anyList)), anyBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, arrList)), arrBuf))
    }
}
