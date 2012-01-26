package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

object Convert
{
	implicit def int2byte(int : Int) : Byte = int.toByte
}
