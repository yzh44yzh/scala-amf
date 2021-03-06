package test

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite
import java.util.{Date, ArrayList, Arrays}

class TestComplexData extends FunSuite
{
	test("test getHistory")
	{
		val buf = BufUtils.mkb(0xa, 0xb, 0x1,
			0xf, 0x68, 0x69, 0x73, 0x74, 0x6f, 0x72, 0x79, // history
			0x9, 0x5, 0x1, // array of two history objects
				0xa, 0x1, // history object 1
					0xd, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72, // sender
					0xa, 0x1, // sender object
						0x9, 0x6e, 0x61, 0x6d, 0x65, // name
						0x6, 0x7, 0x42, 0x6f, 0x62, // Bob
						0x2b, 0x5f, 0x73, 0x75, 0x62, 0x73, 0x63, 0x72, 0x69, 0x62, 0x65, // _subscribe
						0x50, 0x65, 0x72, 0x6d, 0x69, 0x73, 0x73, 0x69, 0x6f, 0x6e, 0x73, // Permissions
						0xa, 0x1, 0x1, // empty object
						0xd, 0x67, 0x65, 0x6e, 0x64, 0x65, 0x72, // gender
						0x6, 0x9, 0x6d, 0x61, 0x6c, 0x65, // male
						0x5, 0x69, 0x64, // id
						0x6, 0x3, 0x31, // "1"
					0x1, // end of sender object
					0xf, 0x6d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, // message
					0xa, 0x1, // message object 1
						0x9, 0x64, 0x61, 0x74, 0x65, // date
						0x08, 0x01, 0x42, 0x72, -0x3c, -0x3e, 0x14, -0x18, 0x00, 0x00, // date value
						0x15, 0x72, 0x65, 0x63, 0x65, 0x69, 0x76, 0x65, 0x72, 0x49, 0x44, // receiverID
						0x6, 0x1, // empty string
						0xf, 0x63, 0x6f, 0x6e, 0x74, 0x65, 0x6e, 0x74, // content
						0x6, 0xb, 0x48, 0x65, 0x6c, 0x6c, 0x6f, // Hello
						0x11, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72, 0x49, 0x44, // senderID
						0x6, 0x10, // ref to string "1"
					0x1, // end of message object
				0x1, // end of history object 1
				0xa, 0x1, // history object 2
					0x2, // ref to string "sender"
					0xa, 0x6, // ref to sender object
					0x12, // ref to string "message"
					0xa, 0x1, // message object 2
					0x14, // ref to string "date"
					0x08, 0x01, 0x42, 0x53, 0x25, 0x6a, -0x46, -0x36, 0x00, 0x00, // date value
					0x16, // ref to string "receiverID"
					0x6, 0x1, // empty string
					0x18, // ref to string "content"
					0x6, 0x5, 0x48, 0x69, // "Hi"
					0x1c, // ref to string "senderID
					0x6, 0x10, // ref to string "1"
					0x1, // end of message object 2
				0x1, // ent of history object 2
			0xd, 0x72, 0x6f, 0x6f, 0x6d, 0x49, 0x44, // roomID
			0x6, 0xb, 0x72, 0x6f, 0x6f, 0x6d, 0x31, // room1
			0x9, 0x72, 0x65, 0x66, 0x73, // refs
			0x9, 0x13, 0x1, // array of refs
				0x8, 0xc,  // 5 ref to d1
				0x8, 0x12, // 8 ref to d2
				0xa, 0x8,  // 3 ref to subscribe permissions
				0xa, 0x6,  // 2 ref to sender
				0xa, 0xa,  // 4 ref to msg1
				0xa, 0x10, // 7 ref to msg2
				0xa, 0x4,  // 1 ref to history1
				0xa, 0xe,  // 6 ref to history2
				0x9, 0x2,  // 0 ref to array of history objects
			0x1)

		// 0:array of hist, 1:hist1, 2:sender, 3:sp, 4:msg1, 5:d1, 6:hist2, 7:msg2, 8:d2

		val sp = new AmfClass

		val sender = new AmfClass
		sender.put("name", "Bob");
		sender.put("_subscribePermissions", sp);
		sender.put("gender", "male");
		sender.put("id", "1");

		val d1: Date = new Date(1289767440000L)
		val msg1 = new AmfClass
		msg1.put("date", d1)
		msg1.put("receiverID", "")
		msg1.put("content", "Hello")
		msg1.put("senderID", "1")

		val history1 = new AmfClass
		history1.put("sender", sender)
		history1.put("message", msg1)

		val d2: Date = new Date(328928521000L)
		val msg2 = new AmfClass
		msg2.put("date", d2)
		msg2.put("receiverID", "")
		msg2.put("content", "Hi")
		msg2.put("senderID", "1")

		val history2 = new AmfClass
		history2.put("sender", sender)
		history2.put("message", msg2)

		val history = new ArrayList[AmfClass]
		history.add(history1)
		history.add(history2)

		val obj = new AmfClass
		obj.put("history", history)
		obj.put("roomID", "room1")
		obj.put("refs", new ArrayList(Arrays.asList(d1, d2, sp, sender, msg1, msg2, history1, history2, history)))

		assert(obj === Amf.decode(buf))
		buf.position(0)
		assert(buf === Amf.encode(obj))
	}
}
