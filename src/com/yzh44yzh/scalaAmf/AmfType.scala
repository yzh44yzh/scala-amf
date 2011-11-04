/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf

object AmfType extends Enumeration
{
    type AmfType = Value
    val NULL, BOOL, INT, DOUBLE, STRING, DATE, ARRAY, OBJECT, BYTEARRAY = Value
}
