package test

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite
import java.util.{Arrays, ArrayList, Date}

class TestRefs extends FunSuite
{
    // ref for Dates
    def createObj1() : AmfClass = {
        val obj = new AmfClass
        obj.put("date4", new Date(791687040000L))  // Sun Nov 14 22:44:00 GMT+0200 2010
        obj.put("date2", new Date(791687040000L))  // Thu Feb  2 03:04:00 GMT+0200 1995
        obj.put("date1", new Date(1289767440000L))
        obj.put("date3", new Date(1289767440000L))
        obj
    }
    val obj1 = createObj1()
    val buf1 = BufUtils.mkb(List(0xa, 0xb,
            0x1,
            0xb, 0x64, 0x61, 0x74, 0x65, 0x34, // date4
            0x08, 0x01, 0x42, 0x67, 0x0a, -0x79, 0x31, -0x80, 0x00, 0x0,
            0xb, 0x64, 0x61, 0x74, 0x65, 0x32, // date2
            0x8, 0x2, // ref to Sun Nov
            0xb, 0x64, 0x61, 0x74, 0x65, 0x31, // date1
            0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00,
            0xb, 0x64, 0x61, 0x74, 0x65, 0x33, // date3
            0x8, 0x4, // ref to Thu Feb
            0x1
    ))


    // ref for Strings
    def createObj2() : AmfClass = {
        val obj = new AmfClass
        obj.put("str2", "World")
        obj.put("str1", "Hello")
        obj.put("str4", "Hello")
        obj.put("str3", "Hello")
        obj
    }
    val obj2 = createObj2()
    val buf2 = BufUtils.mkb(List(0xa, 0xb,
            0x1,
            0x9, 0x73, 0x74, 0x72, 0x32, // str2
            0x6, 0xb, 0x57, 0x6f, 0x72, 0x6c, 0x64, // World
            0x9, 0x73, 0x74, 0x72, 0x31, // str1
            0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f, // Hello
            0x9, 0x73, 0x74, 0x72, 0x34, // str4
            0x6, 0x6, // ref to Hello
            0x9, 0x73, 0x74, 0x72, 0x33, // str3
            0x6, 0x6, // ref to Hello
            0x1
    ))

    
    // ref for Arrays
    def createObj3() : AmfClass = {
        val arr1 = new ArrayList(Arrays.asList(1, 2, 3))
        val arr2 = new ArrayList(Arrays.asList(4, 5, 6))
        val obj = new AmfClass
        obj.put("arr2", arr1)
        obj.put("arr1", arr1)
        obj.put("arr3", arr1)
        obj.put("arr4", arr2)
        obj.put("arr5", arr2)
        obj
    }
    val obj3 = createObj3()
    val buf3 = BufUtils.mkb(List(0xa, 0xb,
        0x1,
        0x9, 0x61, 0x72, 0x72, 0x32, // arr2
        0x9, 0x7, 0x1, 0x4, 0x1, 0x4, 0x2, 0x4, 0x3, // [1,2,3]
        0x9, 0x61, 0x72, 0x72, 0x31, // arr1
        0x9, 0x2, // ref to arr1
        0x9, 0x61, 0x72, 0x72, 0x33, // arr3
        0x9, 0x2, // ref to arr1
        0x9, 0x61, 0x72, 0x72, 0x34, // arr4
        0x9, 0x7, 0x1, 0x4, 0x4, 0x4, 0x5, 0x4, 0x6, // [4,5,6]
        0x9, 0x61, 0x72, 0x72, 0x35, // arr5
        0x9, 0x4, // ref to arr2
        0x1
    ))


    // ref for Objects
    def createObj4() : ArrayList[Any] = {
        val obj1 = new AmfClass()
        obj1.put("a", 1)
        obj1.put("b", 2)
        val obj2 = new AmfClass()
        obj2.put("d", 4)
        obj2.put("c", 3)
        new ArrayList(Arrays.asList(obj1, obj2, obj2, obj1))
    }
    val obj4 = createObj4()
    val buf4 = BufUtils.mkb(List(0x9, 0x9, 0x1,
        0xa, 0xb, // obj1
            0x1,
            0x3, 0x61, 0x4, 0x1, // a:1
            0x3, 0x62, 0x4, 0x2, // b:2
            0x1, 
        0xa, // obj2 
            0x1, // trait
            0x3, 0x64, 0x4, 0x4, // d:3
            0x3, 0x63, 0x4, 0x3, // c:4
            0x1,
        0xa, 0x4, // ref to obj2
        0xa, 0x2 // ref to obj1
    ))

    // ref for Arrays
    def createObj5() : ArrayList[Any] = {
        val arr1 = new ArrayList(Arrays.asList(1, 2, 3))
        val arr2 = new ArrayList(Arrays.asList(4, 5, 6))
        new ArrayList(Arrays.asList(arr1, arr2, arr1, arr1, arr2))
    }
    val obj5 = createObj5()
    val buf5 = BufUtils.mkb(List(0x9, 0xb, 0x1,
        0x9, 0x7, 0x1, 0x4, 0x1, 0x4, 0x2, 0x4, 0x3, // arr1
        0x9, 0x7, 0x1, 0x4, 0x4, 0x4, 0x5, 0x4, 0x6, // arr2
        0x9, 0x2, // ref to arr1
        0x9, 0x2, // ref to arr1
        0x9, 0x4 // ref to arr2
    ))


    def createObj6() : AmfClass = {
        val obj1 = new AmfClass()
        obj1.put("bbb", 2)
        obj1.put("aaa", 1)

        val obj2 = new AmfClass()
        obj2.put("bbb", 4)
        obj2.put("aaa", 3)

        var obj = new AmfClass()
        obj.put("aa2", obj1)
        obj.put("bb1", obj2)
        obj.put("bb2", obj2)
        obj.put("aa1", obj1)
        obj
    }
    val obj6 = createObj6()
    val buf6 = BufUtils.mkb(List(0xa, 0xb, 0x1,
        0x7, 0x61, 0x61, 0x32, // aa2
        0xa, 0x1,
            0x7, 0x62, 0x62, 0x62, // bbb
            0x4, 0x2,
            0x7, 0x61, 0x61, 0x61, // aaa
            0x4, 0x1,
            0x1,
        0x7, 0x62, 0x62, 0x31, // bb1
        0xa, 0x1,
            0x2, // ref to bbb
            0x4, 0x4,
            0x4, // ref to aaa
            0x4, 0x3,
            0x1,
        0x7, 0x62, 0x62, 0x32, // bb2
        0xa, 0x4, // ref to obj2
        0x7, 0x61, 0x61, 0x31, // aa1
        0xa, 0x2, // ref to obj1
        0x1
    ))


    def createObj7() : AmfClass = {
        val obj1 = new AmfClass()
        obj1.put("bbb", 2)
        obj1.put("aaa", 1)

        val arr = new ArrayList(Arrays.asList(1, 2, 3))

        var obj = new AmfClass()
        obj.put("aa4", "Hello")
        obj.put("aa5", 123)
        obj.put("aa6", arr)
        obj.put("aa7", obj1)
        obj.put("aa1", true)
        obj.put("aa8", arr)
        obj.put("aa2", "Hello")
        obj.put("aa3", obj1)
        obj
    }
    val obj7 = createObj7()
    val buf7 = BufUtils.mkb(List(0xa, 0xb, 0x1,
        0x7, 0x61, 0x61, 0x34, // aa4
        0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f, // Hello
        0x7, 0x61, 0x61, 0x35, // aa5
        0x4, 0x7b, // 123
        0x7, 0x61, 0x61, 0x36, // aa6
        0x9, 0x7, 0x1, 0x4, 0x1, 0x4, 0x2, 0x4, 0x3, // [1,2,3]
        0x7, 0x61, 0x61, 0x37, // aa7
        0xa, 0x1, // obj1
            0x7, 0x62, 0x62, 0x62, // bbb
            0x4, 0x2, // 2
            0x7, 0x61, 0x61, 0x61, // aaa
            0x4, 0x1, // 1
            0x1,
        0x7, 0x61, 0x61, 0x31, // aa1
        0x3, // true
        0x7, 0x61, 0x61, 0x38, // aa8
        0x9, 0x2, // ref to [1,2,3]
        0x7, 0x61, 0x61, 0x32, // aa2
        0x6, 0x2, // ref to Hello
        0x7, 0x61, 0x61, 0x33, // aa3
        0xa, 0x2, // ref to obj1
        0x1
    ))
    /*
    TODO: flash client gives here ref 0x4
    Я пока не разобрался, в какой последовательности должны ложиться в кэш объекты
    при сериализации и десериализации. 
     */


    test("decode objects")
    {
        val (AmfType.OBJECT, res1) = Amf.decode(buf1)
        assert(obj1.equals(res1))

        val (AmfType.OBJECT, res2) = Amf.decode(buf2)
        assert(obj2.equals(res2))

        val (AmfType.OBJECT, res3) = Amf.decode(buf3)
        assert(obj3.equals(res3))

        val (AmfType.ARRAY, res4) = Amf.decode(buf4)
        assert(obj4.equals(res4))

        val (AmfType.ARRAY, res5) = Amf.decode(buf5)
        assert(obj5.equals(res5))

        val (AmfType.OBJECT, res6) = Amf.decode(buf6)
        assert(obj6.equals(res6))

        val (AmfType.OBJECT, res7) = Amf.decode(buf7)
        assert(obj7.equals(res7))
    }

    test("encode objects")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj1)), buf1))
        assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj2)), buf2))
        assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj3)), buf3))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, obj4)), buf4))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, obj5)), buf5))
        assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj6)), buf6))
        assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj7)), buf7))
    }
}
