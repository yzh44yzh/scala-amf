package test

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite
import java.util.Date

class TestRefs extends FunSuite
{
    // ref for Dates
    def createObj1() : AmfClass = {
        val obj = new AmfClass
        obj.put("date1", new Date(1289767440000L))
        obj.put("date2", new Date(791687040000L))
        obj.put("date3", new Date(1289767440000L))
        obj.put("date4", new Date(791687040000L))
        obj
    }
    val obj1 = createObj1
    val buf1 = BufUtils.mkb(List(0xa, 0xb,
            0x1,
            0xb, 0x64, 0x61, 0x74, 0x65, 0x31, // date1
            0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00,
            0xb, 0x64, 0x61, 0x74, 0x65, 0x32, // date2
            0x08, 0x01, 0x42, 0x67, 0x0a, -0x79, 0x31, -0x80, 0x00, 0x0,
            0xb, 0x64, 0x61, 0x74, 0x65, 0x33, // date3
            0x8, 0x2,
            0xb, 0x64, 0x61, 0x74, 0x65, 0x34, // date4
            0x8, 0x4,
            0x1
    ))


    test("decode objects")
    {
        val (AmfType.OBJECT, res1) = Amf.decode(buf1)
        assert(obj1.equals(res1))
    }

    test("encode objects")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj1)), buf1))
    }

}
