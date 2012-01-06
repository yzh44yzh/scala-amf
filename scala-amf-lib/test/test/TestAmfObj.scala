package test

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite
import java.util.Date

class TestAmfObj extends FunSuite
{
	test("anonymous object")
	{
		val obj = new AmfClass
		obj.put("name", "Bob")
		obj.put("gender", 1)
		obj.put("age", 25)

		val buf = BufUtils.mkb(0x0a, 0x0b, // Object
								  0x01,
								  0x09, 0x6e, 0x61, 0x6d, 0x65, // name
								  0x06, 0x07, 0x42, 0x6f, 0x62, // Bob
								  0x0d, 0x67, 0x65, 0x6e, 0x64, 0x65, 0x72, // gender
								  0x04, 0x01, // 1
								  0x07, 0x61, 0x67, 0x65, // age
								  0x04, 0x19, // 25
								  0x01)

		assert(obj === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(obj))
	}

	test("anonymous object with inner anonymous object")
	{
		val location = new AmfClass
		location.put("country", "Belarus")
		location.put("city", "Minsk")

		val obj = new AmfClass
		obj.put("location", location)
		obj.put("name", "Yura")

		val buf = BufUtils.mkb(0x0a, 0x0b, // Object
								  0x01,
								  0x11, 0x6c, 0x6f, 0x63, 0x61, 0x74, 0x69, 0x6f, 0x6e, // location
								  0x0a, // inner Object
								  0x01,
								  0x0f, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x72, 0x79, // country
								  0x06, 0x0f, 0x42, 0x65, 0x6c, 0x61, 0x72, 0x75, 0x73, // Belarus
								  0x09, 0x63, 0x69, 0x74, 0x79, // city
								  0x06, 0x0b, 0x4d, 0x69, 0x6e, 0x73, 0x6b, // Minsk
								  0x01, // end of inner Object
								  0x09, 0x6e, 0x61, 0x6d, 0x65, // name
								  0x06, 0x09, 0x59, 0x75, 0x72, 0x61, // Yura
								  0x01)

		assert(obj === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(obj))
	}

	test("AS3 class (not registered)")
	{
		val obj = new AmfClass
		obj.put("id", 25)
		obj.put("date", new Date(1289767440000L))
		obj.put("sender", "Bob")
		obj.put("content", "Hello")

		val buf = BufUtils.mkb(0xa, 0x43,
								  0x1, // empty class name
								  0x5, 0x69, 0x64, // id
								  0x9, 0x64, 0x61, 0x74, 0x65, // date
								  0xd, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72, // sender
								  0xf, 0x63, 0x6f, 0x6e, 0x74, 0x65, 0x6e, 0x74, // content
								  0x4, 0x19, // 25
								  0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00,
								  0x0, // Sun Nov 14 22:44:00 GMT+0200 2010
								  0x6, 0x7, 0x42, 0x6f, 0x62, // Bob
								  0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f) // Hello

		val bufEnc = BufUtils.mkb(0xa, 0xb,
									 0x1,
									 0x5, 0x69, 0x64, // id
									 0x4, 0x19, // 25
									 0x9, 0x64, 0x61, 0x74, 0x65, // date
									 0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00,
									 0x0, // Sun Nov 14 22:44:00 GMT+0200 2010
									 0xd, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72, // sender
									 0x6, 0x7, 0x42, 0x6f, 0x62, // Bob
									 0xf, 0x63, 0x6f, 0x6e, 0x74, 0x65, 0x6e, 0x74, // content
									 0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f, // Hello
									 0x1)

		assert(obj === Amf.decode(buf))
		buf.position(0)
		assert(bufEnc === Amf.encode(obj))
	}

	test("AS3 class (registered)")
	{
		val obj = new AmfClass
		obj.className = "some.pack.Message"
		obj.put("isPrivate", true)
		obj.put("id", 25)
		obj.put("content", "Hello")
		obj.put("sender", "Bob")

		val buf = BufUtils.mkb(0xa, 0x43,
								  0x23, // className string length
								  0x73, 0x6f, 0x6d, 0x65, 0x2e, // some.
								  0x70, 0x61, 0x63, 0x6b, 0x2e, // pack.
								  0x4d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, // Message
								  0x13, 0x69, 0x73, 0x50, 0x72, 0x69, 0x76, 0x61, 0x74, 0x65, // isPrivate
								  0x5, 0x69, 0x64, // id
								  0xf, 0x63, 0x6f, 0x6e, 0x74, 0x65, 0x6e, 0x74, // content
								  0xd, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72, // sender
								  0x3, // true
								  0x4, 0x19, // 25
								  0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f, // Hello
								  0x6, 0x7, 0x42, 0x6f, 0x62) // Bob

		val res = Amf.decode(buf)
		assert(obj.equals(res))
		assert(res.asInstanceOf[AmfClass].className.equals("some.pack.Message"))
		buf.position(0)
		assert(buf === Amf.encode(obj))
	}

	test("anonymous object with inner AS class")
	{
		val msg = new AmfClass
		msg.className = "some.pack.Message"
		msg.put("id", 25)
		msg.put("content", "Hello")
		msg.put("sender", "Bob")

		val obj = new AmfClass
		obj.put("message", msg)
		obj.put("request", 24)
		obj.put("action", "sendMessage")

		val buf = BufUtils.mkb(0xa, 0xb,
								  0x1,
								  0xf, 0x6d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, // message
								  0xa, 0x33,
								  0x23,
								  0x73, 0x6f, 0x6d, 0x65, 0x2e, // some.
								  0x70, 0x61, 0x63, 0x6b, 0x2e, // pack.
								  0x4d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, // Message
								  0x5, 0x69, 0x64, // id
								  0xf, 0x63, 0x6f, 0x6e, 0x74, 0x65, 0x6e, 0x74, // content
								  0xd, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72, // sender
								  0x4, 0x19, // 25
								  0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f, // Hello
								  0x6, 0x7, 0x42, 0x6f, 0x62, // Bob
								  0xf, 0x72, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, // request
								  0x4, 0x18, // 24
								  0xd, 0x61, 0x63, 0x74, 0x69, 0x6f, 0x6e, // action
								  0x6, 0x17, 0x73, 0x65, 0x6e, 0x64, 0x4d, 0x65, 0x73, 0x73, 0x61, 0x67,
								  0x65, // sendMessage
								  0x1)

		assert(obj === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(obj))
	}

	test("AS class with inner anonymous object")
	{
		val attr = new AmfClass
		attr.put("gender", "male")
		attr.put("age", 32)

		val obj = new AmfClass
		obj.className = "some.pack.User"
		obj.put("attributes", attr)
		obj.put("name", "Bill")
		obj.put("id", 234)

		val buf = BufUtils.mkb(0xa, 0x33,
								  0x1d,
								  0x73, 0x6f, 0x6d, 0x65, 0x2e, // some.
								  0x70, 0x61, 0x63, 0x6b, 0x2e, // pack.
								  0x55, 0x73, 0x65, 0x72, // User
								  0x15, 0x61, 0x74, 0x74, 0x72, 0x69, 0x62, 0x75, 0x74, 0x65, 0x73, // attributes
								  0x9, 0x6e, 0x61, 0x6d, 0x65, // name
								  0x5, 0x69, 0x64, // id
								  0xa,
								  0x1,
								  0xd, 0x67, 0x65, 0x6e, 0x64, 0x65, 0x72, // gender
								  0x6, 0x9, 0x6d, 0x61, 0x6c, 0x65, // male
								  0x7, 0x61, 0x67, 0x65, // age
								  0x4, 0x20, // 32
								  0x1,
								  0x6, 0x9, 0x42, 0x69, 0x6c, 0x6c, // Bill
								  0x4, -0x7f, 0x6a) // 234

		assert(obj === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(obj))
	}

	test("AS class with inner AS class")
	{
		val user = new AmfClass
		user.className = "some.pack.User"
		user.put("age", 44)
		user.put("name", "John")
		user.put("id", 2)
		user.put("admin", true)

		val obj = new AmfClass
		obj.className = "some.pack.Message"
		obj.put("content", "How are you? :)")
		obj.put("sender", user)

		val buf = BufUtils.mkb(0xa, 0x23,
								  0x23,
								  0x73, 0x6f, 0x6d, 0x65, 0x2e, // some.
								  0x70, 0x61, 0x63, 0x6b, 0x2e, // pack.
								  0x4d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, // Message
								  0xf, 0x63, 0x6f, 0x6e, 0x74, 0x65, 0x6e, 0x74, // content
								  0xd, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72, // sender
								  0x6, 0x1f,
								  0x48, 0x6f, 0x77, 0x20, // How
								  0x61, 0x72, 0x65, 0x20, // are
								  0x79, 0x6f, 0x75, 0x3f, 0x20, 0x3a, 0x29, // you? :)
								  0xa, 0x43,
								  0x1d,
								  0x73, 0x6f, 0x6d, 0x65, 0x2e, // some.
								  0x70, 0x61, 0x63, 0x6b, 0x2e, // pack.
								  0x55, 0x73, 0x65, 0x72, // User
								  0x7, 0x61, 0x67, 0x65, // age
								  0x9, 0x6e, 0x61, 0x6d, 0x65, // name
								  0x5, 0x69, 0x64, // id
								  0xb, 0x61, 0x64, 0x6d, 0x69, 0x6e, // admin
								  0x4, 0x2c, // 44
								  0x6, 0x9, 0x4a, 0x6f, 0x68, 0x6e, // John
								  0x4, 0x2, // 2
								  0x3) // true

		assert(obj === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(obj))
	}

	test("2 inner objects")
	{
		val obj1 = new AmfClass();
		obj1.put("name", "Bob");
		obj1.put("id", 1);

		val obj2 = new AmfClass();
		obj2.put("name", "Bill");
		obj2.put("id", 2);
		obj2.put("age", 25);

		val obj = new AmfClass()
		obj.put("user2", obj2);
		obj.put("user1", obj1);

		val buf = BufUtils.mkb(0xa, 0xb, 0x1,
								  0xb, 0x75, 0x73, 0x65, 0x72, 0x32, // user2
								  0xa, 0x1,
								  0x9, 0x6e, 0x61, 0x6d, 0x65, // name
								  0x6, 0x9, 0x42, 0x69, 0x6c, 0x6c, // Bill
								  0x5, 0x69, 0x64, // id
								  0x4, 0x2, // id:2
								  0x7, 0x61, 0x67, 0x65, // age
								  0x4, 0x19, // 25
								  0x1,
								  0xb, 0x75, 0x73, 0x65, 0x72, 0x31, // user1
								  0xa, 0x1,
								  0x2, // ref to name
								  0x6, 0x7, 0x42, 0x6f, 0x62, // Bob
								  0x6, // ref to id
								  0x4, 0x1, // id:1
								  0x1,
								  0x1)

		assert(obj === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(obj))
	}
}
