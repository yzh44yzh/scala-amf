package test

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite
import org.apache.mina.core.buffer.IoBuffer
import java.io.{FileInputStream, File}

class TestAmfString extends FunSuite
{
	def readBuf(filename : String) : IoBuffer =
	{
		val file = new File(filename)
		val bytes = new Array[Byte](file.length toInt)
		val stream = new FileInputStream(file)
		stream.read(bytes)
		BufUtils.mkb(bytes : _*)
	}

	test("test Hello")
	{
		val str = "Hello :)"
		val buf = BufUtils.mkb(0x06, 0x11, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x3a, 0x29)

		assert((AmfType.STRING, str) === Amf.decode(buf))
		assert(buf === Amf.encode((AmfType.STRING, str)))
	}
	
	test("test empty string")
	{
		val str = ""
		val buf = BufUtils.mkb(0x06, 0x1)

		assert((AmfType.STRING, str) === Amf.decode(buf))
		assert(buf === Amf.encode((AmfType.STRING, str)))
	}

	test("test long string")
	{
		val str : String = "Ever look at a testing problem and wonder how to solve it? " +
				"If so you know what it feels like to lack domain expertise. Sometimes this is user-oriented knowledge. " +
				"Testing a flight simulator requires knowledge of how to fly a plane. " +
				"Testing tax preparation software requires knowledge of accounting. " +
				"Other times the knowledge is more problem-oriented. Testing a mobile operating " +
				"system means understand how Wi-Fi and device drivers work. Whenever the bill of materials " +
				"contains a testing problem that the risk analysis identifies as important, the expertise needed " +
				"to test it needs to be on the testing team. Hire it, contract it, outsource it. Whatever it takes " +
				"to ensure that people who know what they are doing and have experience doing it are on staff " +
				"for the duration of the project. There is no technological substitution for expertise."
		val buf = readBuf("test/strbuf3")

		assert((AmfType.STRING, str) === Amf.decode(buf))
		assert(buf === Amf.encode((AmfType.STRING, str)))
	}

	test("test cyrillic string")
	{
		val str : String = "Метаязыковая абстракция — это процесс решения сложных задач путём " +
				"создания нового языка или создания словаря с целью лучшего понимания предметной области."
		val buf = readBuf("test/strbuf4")

		assert((AmfType.STRING, str) === Amf.decode(buf))
		assert(buf === Amf.encode((AmfType.STRING, str)))
	}
}
