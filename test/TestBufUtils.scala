/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf._
import org.scalatest.FunSuite

class TestBufUtils extends FunSuite
{
    test("make buffer")
    {
        val buf = BufUtils.mkb(List(1, 2, 3))
        assert(buf.capacity() == 64)
        assert(buf.get === 1)
        assert(buf.get === 2)
        assert(buf.get === 3)
    }

    test("equals")
    {
        val buf1 = BufUtils.mkb(List(1, 2, 3, 4))
        val buf2 = BufUtils.mkb(List(1, 2, 3, 4))
        val buf3 = BufUtils.mkb(List(1, 2, 3, 4, 5))
        val buf4 = BufUtils.mkb(List(1, 2, 5))
        assert(BufUtils.eq(buf1, buf2))
        assert(!BufUtils.eq(buf1, buf3))
        assert(!BufUtils.eq(buf1, buf4))
    }
}
