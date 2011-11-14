package test

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite
import java.util.LinkedHashMap

class TestAmfObj extends FunSuite
{

    def createObj1() : LinkedHashMap[String, Any] = {
        val obj = new LinkedHashMap[String, Any]
        obj.put("name", "Bob")
        obj.put("gender", 1)
        obj.put("age", 25)
        obj
    }
    val obj1 = createObj1()
    val buf1 = BufUtils.mkb(List(0x0a, 0x0b, // Object
			0x01,
			0x09, 0x6e, 0x61, 0x6d, 0x65, // name
			0x06, 0x07, 0x42, 0x6f, 0x62, // bob
			0x0d, 0x67, 0x65, 0x6e, 0x64, 0x65, 0x72, // gender
			0x04, 0x01, // 1
			0x07, 0x61, 0x67, 0x65, // age
			0x04, 0x19, // 25
			0x01))

    def createObj2() : LinkedHashMap[String, Any] = {
        val location = new LinkedHashMap[String, Any]
        location.put("country", "Belarus")
        location.put("city", "Minsk")

        val obj = new LinkedHashMap[String, Any]
        obj.put("location", location)
        obj.put("name", "Yura")
        obj
    }
    val obj2 = createObj2()
    val buf2 = BufUtils.mkb(List(0x0a, 0x0b, // Object
			0x01,
			0x11, 0x6c, 0x6f, 0x63, 0x61, 0x74, 0x69, 0x6f, 0x6e, // location
			0x0a, 0x0b, // inner Object
                0x01,
                0x0f, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x72, 0x79, // country
                0x06, 0x0f, 0x42, 0x65, 0x6c, 0x61, 0x72, 0x75, 0x73, // Belarus
                0x09, 0x63, 0x69, 0x74, 0x79, // city
                0x06, 0x0b, 0x4d, 0x69, 0x6e, 0x73, 0x6b, // Minsk
                0x01, // end of inner Object
			0x09, 0x6e, 0x61, 0x6d, 0x65, // name
			0x06, 0x09, 0x59, 0x75, 0x72, 0x61, // Yura
			0x01))

    test("decode objects")
    {
        val (AmfType.OBJECT, res1) = Amf.decode(buf1)
        assert(obj1.equals(res1))

        val (AmfType.OBJECT, res2) = Amf.decode(buf2)
        assert(obj2.equals(res2))
    }
    
    test("encode objects")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj1)), buf1))
        assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj2)), buf2))
    }
}
