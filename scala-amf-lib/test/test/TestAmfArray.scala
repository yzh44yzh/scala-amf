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

    def createArr1() : ArrayList[AmfClass] = {
        val obj1 = new AmfClass(); obj1.put("a", 1); obj1.put("b", 2)
        val obj2 = new AmfClass(); obj2.put("d", 4); obj2.put("c", 3)
        val obj3 = new AmfClass(); obj3.put("e", 5); obj3.put("f", 6)
        new ArrayList(Arrays.asList(obj1, obj2, obj3))
    }
    val arrObjList = createArr1()
    val arrObjBuf = BufUtils.mkb(List(0x9, 0x7, 0x1,
             0xa, 0xb, 0x1, // obj1
                 0x3, 0x61, 0x4, 0x1, // a:1
                 0x3, 0x62, 0x4, 0x2, // b:2
                 0x1,
             0xa, 0x1, // obj2
                 0x3, 0x64, 0x4, 0x4, // d:4
                 0x3, 0x63, 0x4, 0x3, // c:3
                 0x1,
             0xa, 0x1, // obj3
                 0x3, 0x65, 0x4, 0x5, // e:5
                 0x3, 0x66, 0x4, 0x6, // f:6
                 0x1))

    def createArr2() : ArrayList[AmfClass] = {
        val obj1 = new AmfClass(); obj1.put("name", "Bob"); obj1.put("id", 1);
        val obj2 = new AmfClass(); obj2.put("name", "Bill"); obj2.put("id", 2);
        val obj3 = new AmfClass(); obj3.put("name", "John"); obj3.put("id", 3);
        new ArrayList(Arrays.asList(obj1, obj2, obj3))
    }
    val arrObjList2 = createArr2()
    val arrObjBuf2 = BufUtils.mkb(List(0x9, 0x7, 0x1,
              0xa, 0xb, 0x1,
                  0x9, 0x6e, 0x61, 0x6d, 0x65, // name
                  0x6, 0x7, 0x42, 0x6f, 0x62, // Bob
                  0x5, 0x69, 0x64, // id
                  0x4, 0x1, // 1
                  0x1,
              0xa, 0x1,
                  0x0, // ref to name
                  0x6, 0x9, 0x42, 0x69, 0x6c, 0x6c, // Bill
                  0x4, // ref to id
                  0x4, 0x2, // 2
                  0x1,
              0xa, 0x1,
                  0x0, // ref to name
                  0x6, 0x9, 0x4a, 0x6f, 0x68, 0x6e, // John
                  0x4, // ref to id
                  0x4, 0x3, // 3
                  0x1))

    // TODO array of not registered classes in not supported yet
    def createArr3() : ArrayList[AmfClass] = {
        val user1 = new AmfClass();
        user1.put("age", 0);
        user1.put("name", "Bill")
        user1.put("id", 1);
        user1.put("admin", false)

        val user2 = new AmfClass();
        user2.put("age", 0);
        user2.put("name", "Bob")
        user2.put("id", 2);
        user2.put("admin", false)

        val user3 = new AmfClass();
        user3.put("age", 25);
        user3.put("name", "John");
        user3.put("id", 3);
        user3.put("admin", false)

        val user4 = new AmfClass();
        user4.put("age", 0);
        user4.put("name", "Helen");
        user4.put("id", 4);
        user4.put("admin", true)

        new ArrayList(Arrays.asList(user1, user2, user3, user4))
    }
    val arrClassList = createArr3()
    val arrClassBuf = BufUtils.mkb(List(0x9, 0x9, 0x1,
               0xa, 0x43, 0x1,
                   0x7, 0x61, 0x67, 0x65, // age
                   0x9, 0x6e, 0x61, 0x6d, 0x65, // name
                   0x5, 0x69, 0x64, // id
                   0xb, 0x61, 0x64, 0x6d, 0x69, 0x6e, // admin
                   0x4, 0x0, // 0
                   0x6, 0x9, 0x42, 0x69, 0x6c, 0x6c, // Bill
                   0x4, 0x1, // 1
                   0x2, // false
               0xa, 0x1,
                   0x4, 0x0, // 0
                   0x6, 0x7, 0x42, 0x6f, 0x62, // Bill
                   0x4, 0x2, // 2
                   0x2, // false
               0xa, 0x1,
                   0x4, 0x19, // 25
                   0x6, 0x9, 0x4a, 0x6f, 0x68, 0x6e, // John
                   0x4, 0x3, // 3
                   0x2, // false
               0xa, 0x1,
                   0x4, 0x0, // 0
                   0x6, 0xb, 0x48, 0x65, 0x6c, 0x65, 0x6e, // Helen
                   0x4, 0x4, // 4
                   0x3 // true
   ))

    // TODO array of registered classes in not supported yet
    def createArr4() : ArrayList[AmfClass] = {
        val user1 = new AmfClass();
        user1.className = "some.pack.User";
        user1.put("age", 0);
        user1.put("name", "Bill")
        user1.put("id", 1);
        user1.put("admin", false)

        val user2 = new AmfClass();
        user2.className = "some.pack.User";
        user2.put("age", 0);
        user2.put("name", "Bob")
        user2.put("id", 2);
        user2.put("admin", false)

        val user3 = new AmfClass();
        user3.className = "some.pack.User";
        user3.put("age", 25);
        user3.put("name", "John");
        user3.put("id", 3);
        user3.put("admin", false)

        val user4 = new AmfClass();
        user4.className = "some.pack.User";
        user4.put("age", 0);
        user4.put("name", "Helen");
        user4.put("id", 4);
        user4.put("admin", true)
        
        new ArrayList(Arrays.asList(user1, user2, user3, user4))
    }
    val arrRClassList = createArr4()
    val arrRClassBuf = BufUtils.mkb(List(0x9, 0x9, 0x1,
               0xa, 0x43, 0x1d, // user1
                   0x73, 0x6f, 0x6d, 0x65, 0x2e, // some.
                   0x70, 0x61, 0x63, 0x6b, 0x2e, // pack.
                   0x55, 0x73, 0x65, 0x72, // User
                   0x7, 0x61, 0x67, 0x65, // age
                   0x9, 0x6e, 0x61, 0x6d, 0x65, // name
                   0x5, 0x69, 0x64, // id
                   0xb, 0x61, 0x64, 0x6d, 0x69, 0x6e, // admin
                   0x4, 0x0, // 0
                   0x6, 0x9, 0x42, 0x69, 0x6c, 0x6c, // Bill
                   0x4, 0x1, // 1
                   0x2, // false
               0xa, // user2
                   0x1, // trait
                   0x4, 0x0, // age:0
                   0x6, 0x7, 0x42, 0x6f, 0x62, // name:Bob
                   0x4, 0x2, // id:2
                   0x2, // admin:false
               0xa, // user3
                   0x1, // trait
                   0x4, 0x19, // age:25
                   0x6, 0x9, 0x4a, 0x6f, 0x68, 0x6e, // John
                   0x4, 0x3, // id:3
                   0x2, // admin:false
               0xa, // user4
                   0x1, // trait
                   0x4, 0x0, // age:0
                   0x6, 0xb, 0x48, 0x65, 0x6c, 0x65, 0x6e, // Helen
                   0x4, 0x4, // id:4
                   0x3 // admin:true
           ))

    def createArr5() : ArrayList[Any] = {
        val user1 = new AmfClass();
        user1.put("name", "Bob")
        user1.put("id", 1);

        val user2 = new AmfClass();
        user2.put("name", "Bill")
        user2.put("id", 2);
        user2.put("age", 25);

        val user3 = new AmfClass();
        user3.className = "some.pack.RUser";
        user3.put("id", 3);
        user3.put("gender", 1)
        user3.put("name", "Helen");

        new ArrayList(Arrays.asList(true, user1, user2, user3, null, "Hello", 128))
    }
    val mixedList = createArr5()
    val mixedBuf = BufUtils.mkb(List(0x9, 0xf, 0x1,
            0x3, // true
            0xa, 0xb, 0x1,
                0x9, 0x6e, 0x61, 0x6d, 0x65, // name
                0x6, 0x7, 0x42, 0x6f, 0x62, // Bob
                0x5, 0x69, 0x64, // id
                0x4, 0x1, // 1
                0x1,
            0xa, 0x1,
                0x0, // ref to name
                0x6, 0x9, 0x42, 0x69, 0x6c, 0x6c, // Bill
                0x4, // ref to id
                0x4, 0x2, // id:2
                0x7, 0x61, 0x67, 0x65, // age
                0x4, 0x19, // age:25
                0x1,
            0xa, 0x33, 0x1f,
                0x73, 0x6f, 0x6d, 0x65, 0x2e, // some.
                0x70, 0x61, 0x63, 0x6b, 0x2e, // pack.
                0x52, 0x55, 0x73, 0x65, 0x72, // RUser
                0x4, // ref to id
                0xd, 0x67, 0x65, 0x6e, 0x64, 0x65, 0x72, // gender
                0x0, // ref to name
                0x4, 0x3, // id:3
                0x4, 0x1, // gender:1
                0x6, 0xb, 0x48, 0x65, 0x6c, 0x65, 0x6e, // Helen
            0x1, // null
            0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f, // Hello
            0x4, -0x7f, 0x0 // 128
           ))


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

        val (AmfType.ARRAY, res7) = Amf.decode(arrObjBuf)
        assert(arrObjList.equals(res7))

        val (AmfType.ARRAY, res8) = Amf.decode(arrObjBuf2)
        assert(arrObjList2.equals(res8))

        val (AmfType.ARRAY, res9) = Amf.decode(mixedBuf)
        assert(mixedList.equals(res9))
    }

    test("encode arrays")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, boolsList)), boolsBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, intsList)), intsBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, doublesList)), doublesBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, stringsList)), stringsBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, anyList)), anyBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, arrList)), arrBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, arrObjList)), arrObjBuf))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, arrObjList2)), arrObjBuf2))
        assert(BufUtils.eq(Amf.encode((AmfType.ARRAY, mixedList)), mixedBuf))
    }
}
