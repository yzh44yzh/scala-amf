package test

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite
import java.util.{Arrays, ArrayList, Date}

class TestRefs extends FunSuite
{
    test("ref for Dates")
    {
		val dt1 = new Date(791687040000L) // Sun Nov 14 22:44:00 GMT+0200 2010
		val dt2 = new Date(1289767440000L) // Thu Feb  2 03:04:00 GMT+0200 1995

		val obj = new AmfClass
		obj.put("date4", dt1)
		obj.put("date2", dt1)
		obj.put("date1", dt2)
		obj.put("date3", dt2)

		val buf = BufUtils.mkb(0xa, 0xb,
								   0x1,
								   0xb, 0x64, 0x61, 0x74, 0x65, 0x34, // date4
								   0x08, 0x01, 0x42, 0x67, 0x0a, -0x79, 0x31, -0x80, 0x00, 0x0,
								   0xb, 0x64, 0x61, 0x74, 0x65, 0x32, // date2
								   0x8, 0x2, // ref to Sun Nov
								   0xb, 0x64, 0x61, 0x74, 0x65, 0x31, // date1
								   0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00,
								   0xb, 0x64, 0x61, 0x74, 0x65, 0x33, // date3
								   0x8, 0x4, // ref to Thu Feb
								   0x1)

		val (AmfType.OBJECT, res) = Amf.decode(buf)
		assert(obj.equals(res))
		assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj)), buf))
	}

    test("ref for Strings")
    {
		val obj = new AmfClass
		obj.put("str2", "World")
		obj.put("str1", "Hello")
		obj.put("str4", "Hello")
		obj.put("str3", "Hello")

		val buf = BufUtils.mkb(0xa, 0xb,
								   0x1,
								   0x9, 0x73, 0x74, 0x72, 0x32, // str2
								   0x6, 0xb, 0x57, 0x6f, 0x72, 0x6c, 0x64, // World
								   0x9, 0x73, 0x74, 0x72, 0x31, // str1
								   0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f, // Hello
								   0x9, 0x73, 0x74, 0x72, 0x34, // str4
								   0x6, 0x6, // ref to Hello
								   0x9, 0x73, 0x74, 0x72, 0x33, // str3
								   0x6, 0x6, // ref to Hello
								   0x1)

		val (AmfType.OBJECT, res) = Amf.decode(buf)
		assert(obj.equals(res))
		assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj)), buf))
	}

	test("ref for Arrays in Object")
    {
		val arr1 = new ArrayList(Arrays.asList(1, 2, 3))
		val arr2 = new ArrayList(Arrays.asList(4, 5, 6))

		val obj = new AmfClass
		obj.put("arr2", arr1)
		obj.put("arr1", arr1)
		obj.put("arr3", arr1)
		obj.put("arr4", arr2)
		obj.put("arr5", arr2)

		val buf = BufUtils.mkb(0xa, 0xb,
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
								   0x1)

		val (AmfType.OBJECT, res) = Amf.decode(buf)
		assert(obj.equals(res))
		assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj)), buf))
	}

	test("ref for Objects in Array")
    {
		val obj1 = new AmfClass()
		obj1.put("a", 1)
		obj1.put("b", 2)

		val obj2 = new AmfClass()
		obj2.put("d", 4)
		obj2.put("c", 3)

		val obj = new ArrayList(Arrays.asList(obj1, obj2, obj2, obj1))

		val buf = BufUtils.mkb(0x9, 0x9, 0x1,
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
								   0xa, 0x2) // ref to obj1

		val (AmfType.ARRAY, res) = Amf.decode(buf)
		assert(obj.equals(res))
		assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, obj)), buf))
	}

    test("ref for Arrays in Array")
    {
		val arr1 = new ArrayList(Arrays.asList(1, 2, 3))
		val arr2 = new ArrayList(Arrays.asList(4, 5, 6))

		val obj = new ArrayList(Arrays.asList(arr1, arr2, arr1, arr1, arr2))

		val buf = BufUtils.mkb(0x9, 0xb, 0x1,
								   0x9, 0x7, 0x1, 0x4, 0x1, 0x4, 0x2, 0x4, 0x3, // arr1
								   0x9, 0x7, 0x1, 0x4, 0x4, 0x4, 0x5, 0x4, 0x6, // arr2
								   0x9, 0x2, // ref to arr1
								   0x9, 0x2, // ref to arr1
								   0x9, 0x4) // ref to arr2

		val (AmfType.ARRAY, res) = Amf.decode(buf)
		assert(obj.equals(res))
		assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, obj)), buf))
	}

	test("refs for Objects in Object")
    {
		val obj1 = new AmfClass()
		obj1.put("bbb", 2)
		obj1.put("aaa", 1)

		val obj2 = new AmfClass()
		obj2.put("bbb", 4)
		obj2.put("aaa", 3)

		val obj = new AmfClass()
		obj.put("aa2", obj1)
		obj.put("bb1", obj2)
		obj.put("bb2", obj2)
		obj.put("aa1", obj1)

		val buf = BufUtils.mkb(0xa, 0xb, 0x1,
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
								   0x1)

		val (AmfType.OBJECT, res) = Amf.decode(buf)
		assert(obj.equals(res))
		assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj)), buf))
	}

	test("mixed refs")
	{
		val obj1 = new AmfClass()
		obj1.put("bbb", 2)
		obj1.put("aaa", 1)

		val arr = new ArrayList(Arrays.asList(1, 2, 3))

		val obj = new AmfClass()
		obj.put("aa4", "Hello")
		obj.put("aa5", 123)
		obj.put("aa6", arr)
		obj.put("aa7", obj1)
		obj.put("aa1", true)
		obj.put("aa8", arr)
		obj.put("aa2", "Hello")
		obj.put("aa3", obj1)

		val buf = BufUtils.mkb(0xa, 0xb, 0x1,
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
								   0xa, 0x4, // ref to obj1
								   0x1)

		val (AmfType.OBJECT, res) = Amf.decode(buf)
        assert(obj.equals(res))
        assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj)), buf))
    }

    test("test emtpy objects and arrays")
    {
        val obj1 = new AmfClass(); obj1.put("room", "R1")
        val obj2 = new AmfClass()
        val obj3 = new AmfClass(); obj3.put("room", "R2")
        val obj4 = new AmfClass()
        val obj5 = new AmfClass()
        val arr6 = new ArrayList(Arrays.asList(1, 2, 3))
        val arr7 = new ArrayList(Arrays.asList())
        val arr8 = new ArrayList(Arrays.asList())

        val arr = new ArrayList(Arrays.asList(obj1, obj2, obj3, obj4, obj5, arr6, arr7, arr8))

        val buf = BufUtils.mkb(0x9, 0x11, 0x1,
            0xa, 0xb, 0x1, // obj1
            0x9, 0x72, 0x6f, 0x6f, 0x6d, // room
            0x6, 0x5, 0x52, 0x31, // R1
            0x1,
            0xa, 0x1, 0x1, // obj2, empty
            0xa, 0x1, // obj3
            0x0, // ref to room
            0x6, 0x5, 0x52, 0x32, // R2
            0x1,
            0xa, 0x1, 0x1, // obj4 empty
            0xa, 0x1, 0x1, // obj5 empty
            0x9, 0x7, 0x1, 0x4, 0x1, 0x4, 0x2, 0x4, 0x3, // [1,2,3]
            0x9, 0x1, 0x1, // empty array
            0x9, 0x1, 0x1  // empty array
        )

        val (AmfType.ARRAY, res) = Amf.decode(buf)
        assert(arr.equals(res))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, arr)), buf))
    }

	test("mixed arrays, objects and dates")
	{
		val d = new Date(791687040000L) // Sun Nov 14 22:44:00 GMT+0200 2010
		
		val o1 = new AmfClass()
		o1.put("a", 1)
		o1.put("b", 2)
		
		val o2 = new AmfClass()
		o2.put("a", 3)
		o2.put("b", 4)

		val arr = new ArrayList(Arrays.asList(1, 2, 3))
		
		val obj = new AmfClass
		obj.put("dref", d)
		obj.put("o1", o1)
		obj.put("o2", o2)
		obj.put("arr", arr)
		obj.put("d", d)
		obj.put("o2ref", o2)
		obj.put("arrref", arr)
		obj.put("o1ref", o1)

		val buf = BufUtils.mkb(0xa, 0xb, 0x1,
								  0x9, 0x64, 0x72, 0x65, 0x66, // "dref"
								  0x08, 0x01, 0x42, 0x67, 0x0a, -0x79, 0x31, -0x80, 0x00, 0x0, // date value
								  0x5, 0x6f, 0x31, // "o1"
								  0xa, 0x1, // obj o1
									  0x3, 0x61, // "a"
									  0x4, 0x1, 0x3, // a:1
									  0x62, // "b"
									  0x4, 0x2, // b:2
								  0x1, // end obj o1
								  0x5, 0x6f, 0x32, // "o2"
								  0xa, 0x1, // obj o2
									  0x4, // ref to "a"
									  0x4, 0x3, // a:3
									  0x6, // ref to "b"
									  0x4, 0x4, // b:4
								  0x1, // end obj o2
								  0x7, 0x61, 0x72, 0x72, // "arr"
								  0x9, 0x7, 0x1, 0x4, 0x1, 0x4, 0x2, 0x4, 0x3, // [1,2,3]
								  0x3, 0x64, // "d"
								  0x8, 0x2, // ref to dref
								  0xb, 0x6f, 0x32, 0x72, 0x65, 0x66, // "o2ref"
								  0xa, 0x6, // ref to obj o2
								  0xd, 0x61, 0x72, 0x72, 0x72, 0x65, 0x66, // "arrref"
								  0x9, 0x8, // ref to arr
								  0xb, 0x6f, 0x31, 0x72, 0x65, 0x66, // "o1ref"
								  0xa, 0x4, // ref to obj o1
								  0x1)
		// refs: 0x2 dref, 0x4 arr, 0x6 o2, 0x8 o1

		val (AmfType.OBJECT, res) = Amf.decode(buf)
		assert(obj.equals(res))
		assert(BufUtils.eq(Amf.encode((AmfType.OBJECT, obj)), buf))
	}
}
