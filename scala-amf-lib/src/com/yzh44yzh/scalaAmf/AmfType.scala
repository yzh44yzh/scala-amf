package com.yzh44yzh.scalaAmf

import java.util.LinkedHashMap

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

object AmfType extends Enumeration
{
    type AmfType = Value
    val NULL, BOOL, INT, DOUBLE, STRING, DATE, ARRAY, OBJECT, BYTEARRAY = Value
}

class AmfClass extends LinkedHashMap[String, Any]
{
    var className : String = "";
}
