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
    val str1 = "Hello :)"
    val buf1 = BufUtils.mkb(0x06, 0x11, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x3a, 0x29)

    val str2 = ""
    val buf2 = BufUtils.mkb(0x06, 0x1)

    val str3: String = "Ever look at a testing problem and wonder how to solve it? " +
            "If so you know what it feels like to lack domain expertise. Sometimes this is user-oriented knowledge. " +
            "Testing a flight simulator requires knowledge of how to fly a plane. " +
            "Testing tax preparation software requires knowledge of accounting. " +
            "Other times the knowledge is more problem-oriented. Testing a mobile operating " +
            "system means understand how Wi-Fi and device drivers work. Whenever the bill of materials " +
            "contains a testing problem that the risk analysis identifies as important, the expertise needed " +
            "to test it needs to be on the testing team. Hire it, contract it, outsource it. Whatever it takes " +
            "to ensure that people who know what they are doing and have experience doing it are on staff " +
            "for the duration of the project. There is no technological substitution for expertise."
    val buf3 = readBuf("test/strbuf3")

    val str4: String = "Метаязыковая абстракция — это процесс решения сложных задач путём " +
            "создания нового языка или создания словаря с целью лучшего понимания предметной области."
    val buf4 = readBuf("test/strbuf4")

    def readBuf(filename : String) : IoBuffer =
    {
        val file = new File(filename)
        val bytes = new Array[Byte](file.length toInt)
        val stream = new FileInputStream(file)
        stream.read(bytes)
        BufUtils.mkb(bytes : _*)
    }

    test("decode string")
    {
        val (AmfType.STRING, res1) = Amf.decode(buf1)
        assert(str1.equals(res1))

        val (AmfType.STRING, res2) = Amf.decode(buf2)
        assert(str2.equals(res2))

        val (AmfType.STRING, res3) = Amf.decode(buf3)
        assert(str3.equals(res3))

        val (AmfType.STRING, res4) = Amf.decode(buf4)
        assert(str4.equals(res4))
    }

    test("encode string")
    {
        assert(BufUtils.eq(Amf.encode((AmfType.STRING, str1)), buf1))
        assert(BufUtils.eq(Amf.encode((AmfType.STRING, str2)), buf2))
        assert(BufUtils.eq(Amf.encode((AmfType.STRING, str3)), buf3))
        assert(BufUtils.eq(Amf.encode((AmfType.STRING, str4)), buf4))
    }
}
