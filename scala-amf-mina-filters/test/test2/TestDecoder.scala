package test2

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.scalatest.FunSuite
import com.yzh44yzh.scalaAmf.mina.AmfDecoder
import org.apache.mina.core.buffer.IoBuffer
import java.util.{ArrayList, LinkedHashMap, Date}
import com.yzh44yzh.scalaAmf.{Amf, AmfClass, AmfType, BufUtils}

class TestDecoder extends FunSuite
{
	test("test short message")
    {
		// {d={uid=d5b0ef4c8c51f303ecbaed81a6e078c5, hasCam=true}, q=9, a=autoLogin}
        val buf = BufUtils.mkb(List(
				0x0a, 0x0b, 0x01, 0x03, 0x61, 0x06, 0x13, 0x61,
				0x75, 0x74, 0x6f, 0x4c, 0x6f, 0x67, 0x69, 0x6e,
				0x03, 0x64, 0x0a, 0x01, 0x0d, 0x68, 0x61, 0x73,
				0x43, 0x61, 0x6d, 0x03, 0x07, 0x75, 0x69, 0x64,
				0x06, 0x41, 0x64, 0x35, 0x62, 0x30, 0x65, 0x66,
				0x34, 0x63, 0x38, 0x63, 0x35, 0x31, 0x66, 0x33,
				0x30, 0x33, 0x65, 0x63, 0x62, 0x61, 0x65, 0x64,
				0x38, 0x31, 0x61, 0x36, 0x65, 0x30, 0x37, 0x38,
				0x63, 0x35, 0x01, 0x03, 0x71, 0x04, 0x09, 0x01,
				0x0))

        assert(buf.limit == 73)

        val res = new AmfDecoder().getData(buf, null)
        val data : AmfClass = res._1
        val newCache : IoBuffer = res._2

        assert(newCache == null)
        assert(data.get("q").equals(9))
        assert(data.get("a").equals("autoLogin"))

        val d = data.get("d").asInstanceOf[LinkedHashMap[String, Any]]
        assert(d.get("uid").equals("d5b0ef4c8c51f303ecbaed81a6e078c5"))
        assert(d.get("hasCam").equals(true))
    }

	test("test short message again")
    {
        // {a=connect, d={appType=chat, sid=0, fromDomain=http://chat71/}, q=1}
        val buf = BufUtils.mkb(List(
                0x0A, 0x0B, 0x01, 0x03, 0x61, 0x06, 0x0F, 0x63,
                0x6F, 0x6E, 0x6E, 0x65, 0x63, 0x74, 0x03, 0x64,
                0x0A, 0x01, 0x0F, 0x61, 0x70, 0x70, 0x54, 0x79,
                0x70, 0x65, 0x06, 0x09, 0x63, 0x68, 0x61, 0x74,
                0x07, 0x73, 0x69, 0x64, 0x04, 0x00, 0x15, 0x66,
                0x72, 0x6F, 0x6D, 0x44, 0x6F, 0x6D, 0x61, 0x69,
                0x6E, 0x06, 0x1D, 0x68, 0x74, 0x74, 0x70, 0x3A,
                0x2F, 0x2F, 0x63, 0x68, 0x61, 0x74, 0x37, 0x31,
                0x2F, 0x01, 0x03, 0x71, 0x04, 0x01, 0x01, 0x00))

        assert(buf.limit == 72)

        val res = new AmfDecoder().getData(buf, null)
        val data : AmfClass = res._1
        val newCache : IoBuffer = res._2

        assert(newCache == null)
        assert(data.get("a").equals("connect"))
        assert(data.get("q").equals(1))

        val d = data.get("d").asInstanceOf[LinkedHashMap[String, Any]]
        assert(d.get("appType").equals("chat"))
        assert(d.get("sid").equals(0))
        assert(d.get("fromDomain").equals("http://chat71/"))
    }

	test("test long message")
    {
		/*
		0xa,  0xb,  0x1,  0x3,  a 0x6,  0x17,
		s e n d M e s s a g e 0x3,  d 0xa,  0x1,  0xf,
		m e s s a g e 0xa,  0x-7f,  0x3,  0x1,  0xf,
		i m a g e I D 0x17,  y o u T u b e L i n k 0xf,  w h i s p e r 0x1d,  i
		*/
		val in1 = BufUtils.mkb(List(
				0x0a, 0x0b, 0x01, 0x03, 0x61, 0x06, 0x17, 0x73,
				0x65, 0x6e, 0x64, 0x4d, 0x65, 0x73, 0x73, 0x61,
				0x67, 0x65, 0x03, 0x64, 0x0a, 0x01, 0x0f, 0x6d,
				0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0xa, -0x7f,
				0x03, 0x01, 0x0f, 0x69, 0x6d, 0x61, 0x67, 0x65,
				0x49, 0x44, 0x17, 0x79, 0x6f, 0x75, 0x54, 0x75,
				0x62, 0x65, 0x4c, 0x69, 0x6e, 0x6b, 0x0f, 0x77,
				0x68, 0x69, 0x73, 0x70, 0x65, 0x72, 0x1d, 0x69
		))

		assert(in1.limit == 64)

        var res = new AmfDecoder().getData(in1, null)
        var data : AmfClass = res._1
        var newCache : IoBuffer = res._2

        assert(data == null)
		assert(BufUtils.eq(in1, newCache))

		/*
		m a g e E x t e n t i o n 0x11,
		s e n d e r I D 0xf,
		c o n t e n t 0x9,
		d a t e 0x15,
		r e c e i v e r I D 0x6,
		0x1,  0x6,  0x1,  0x2,  0x6,  0x1,  0x6,  0x3,  1 0x6,  0x1b,  0x20,
		h e l l o 0x20,  w o r l d 0x20,
		0x8,  0x1,  B s 0x1c,  0x-3a,  0x-1d,  r 0 0x0,  0x1,  0xd,
		r o o m I D 0x6,  0xb,
		r o o m 1 0x1,
		0x3,  q 0x4,  0x16,  0x1,  0x0
		 */
		val in2 = BufUtils.mkb(List(
			0x6d,  0x61,  0x67,  0x65,  0x45,  0x78,  0x74,  0x65,
			0x6e,  0x74,  0x69,  0x6f,  0x6e,  0x11,  0x73,  0x65,
			0x6e,  0x64,  0x65,  0x72,  0x49,  0x44,  0x0f,  0x63,
			0x6f,  0x6e,  0x74,  0x65,  0x6e,  0x74,  0x09,  0x64,
			0x61,  0x74,  0x65,  0x15,  0x72,  0x65,  0x63,  0x65,
			0x69,  0x76,  0x65,  0x72,  0x49,  0x44,  0x06,  0x01,
			0x06,  0x01,  0x02,  0x06,  0x01,  0x06,  0x03,  0x31,
			0x06,  0x1b,  0x20,  0x68,  0x65,  0x6c,  0x6c,  0x6f,
			0x20,  0x77,  0x6f,  0x72,  0x6c,  0x64,  0x20,  0x08,
			0x01,  0x42,  0x73,  0x1c, -0x3a, -0x1d,  0x72,  0x30,
			0x00,  0x01,  0x0d,  0x72,  0x6f,  0x6f,  0x6d,  0x49,
			0x44,  0x06,  0x0b,  0x72,  0x6f,  0x6f,  0x6d,  0x31,
			0x01,  0x03,  0x71,  0x04,  0x16,  0x01,  0x00
		))

		assert(in2.limit == 103)

        res = new AmfDecoder().getData(in2, newCache)
        data = res._1
        newCache = res._2

		/*
		{d={message={content= hello world , whisper=false, imageID=, receiverID=null, senderID=1, youTubeLink=,
					 imageExtention=, date=Mon Aug 15 10:15:39 EEST 2011},
			roomID=room1},
		 q=22,
		 a=sendMessage}
		 */

        assert(newCache == null)
		assert(data.get("q").equals(22))
		assert(data.get("a").equals("sendMessage"))

		val d = data.get("d").asInstanceOf[LinkedHashMap[String, Any]]
		assert(d.get("roomID").equals("room1"))

		val message = d.get("message").asInstanceOf[LinkedHashMap[String, Any]]
		assert(message.get("content").equals(" hello world "))
		assert(message.get("whisper").equals(false))
		assert(message.get("imageID").equals(""))
		assert(message.get("receiverID") == null)
		assert(message.get("senderID").equals("1"))
		assert(message.get("youTubeLink").equals(""))
		assert(message.get("imageExtention").equals(""))
		assert(message.get("date") != null)
    }

	test("test 0-ended package")
    {
		/*
		a b 1 3 a 6 17
		s e n d M e s s a g e 3 d a 1 f
		m e s s a g e a -7f 3 1 f
		w h i s p e r 1d
		i m a g e E x t e n t i o n f
		i m a g e I D 17
		y o u T u b e L i n k 15
		r e c e i v e r I D 9
		d a t e f
		c o n t e n t 11
		s e n d e r I D 2 6 1 6 1 6 1 1 8 1 B s 1c -3d -18 N -30 0
		*/
		val in1 = BufUtils.mkb(List(
				0x0A, 0x0B, 0x01, 0x03, 0x61, 0x06, 0x17, 0x73, 0x65, 0x6E, 0x64, 0x4D, 0x65, 0x73, 0x73, 0x61,
				0x67, 0x65, 0x03, 0x64, 0x0A, 0x01, 0x0F, 0x6D, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x0A, 0x81 toByte,
                0x03, 0x01, 0x0F, 0x77, 0x68, 0x69, 0x73, 0x70, 0x65, 0x72, 0x1D, 0x69, 0x6D, 0x61, 0x67, 0x65,
				0x45, 0x78, 0x74, 0x65, 0x6E, 0x74, 0x69, 0x6F, 0x6E, 0x0F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x49,
				0x44, 0x17, 0x79, 0x6F, 0x75, 0x54, 0x75, 0x62, 0x65, 0x4C, 0x69, 0x6E, 0x6B, 0x15, 0x72, 0x65,
				0x63, 0x65, 0x69, 0x76, 0x65, 0x72, 0x49, 0x44, 0x09, 0x64, 0x61, 0x74, 0x65, 0x0F, 0x63, 0x6F,
				0x6E, 0x74, 0x65, 0x6E, 0x74, 0x11, 0x73, 0x65, 0x6E, 0x64, 0x65, 0x72, 0x49, 0x44, 0x02, 0x06,
				0x01, 0x06, 0x01, 0x06, 0x01, 0x01, 0x08, 0x01, 0x42, 0x73, 0x1C,
                0xC3 toByte, 0xE8 toByte, 0x4E, 0xD0 toByte, 0x00
		))

		assert(in1.limit == 128)

        var res = new AmfDecoder().getData(in1, null)
        var data : AmfClass = res._1
        var newCache : IoBuffer = res._2

        assert(data == null)
		assert(BufUtils.eq(in1, newCache))

		/*
		 6 d 20 a s d f 20 6 3 1 d
		 r o o m I D 6 b
		 r o o m 1 1 3 q 4 16 1 0
		 */
		val in2 = BufUtils.mkb(List(
				0x06, 0x0D, 0x20, 0x61, 0x73, 0x64, 0x66, 0x20, 0x06, 0x03, 0x31, 0x0D, 0x72, 0x6F, 0x6F, 0x6D,
				0x49, 0x44, 0x06, 0x0B, 0x72, 0x6F, 0x6F, 0x6D, 0x31, 0x01, 0x03, 0x71, 0x04, 0x16, 0x01, 0x00
		))

		assert(in2.limit == 32)

        res = new AmfDecoder().getData(in2, newCache)
        data = res._1
        newCache = res._2

		/*
		{d={message={content= asdf , whisper=false, imageID=, receiverID=null, senderID=1, youTubeLink=,
					 imageExtention=, date=Mon Aug 15 10:15:39 EEST 2011},
			roomID=room1},
		 q=22,
		 a=sendMessage}
		 */

        assert(newCache == null)
		assert(data.get("q").equals(22))
		assert(data.get("a").equals("sendMessage"))

		val d = data.get("d").asInstanceOf[LinkedHashMap[String, Any]]
		assert(d.get("roomID").equals("room1"))

		val message = d.get("message").asInstanceOf[LinkedHashMap[String, Any]]
		assert(message.get("content").equals(" asdf "))
		assert(message.get("whisper").equals(false))
		assert(message.get("imageID").equals(""))
		assert(message.get("receiverID") == null)
		assert(message.get("senderID").equals("1"))
		assert(message.get("youTubeLink").equals(""))
		assert(message.get("imageExtention").equals(""))
		assert(message.get("date") != null)
    }
}
