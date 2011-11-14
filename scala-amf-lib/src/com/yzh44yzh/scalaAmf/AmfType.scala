package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

object AmfType extends Enumeration
{
    type AmfType = Value
    val NULL, BOOL, INT, DOUBLE, STRING, DATE, ARRAY, OBJECT, BYTEARRAY = Value
}
