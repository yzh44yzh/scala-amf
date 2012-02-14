package com.yzh44yzh.scalaAmf

import java.util.LinkedHashMap

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class AmfClass
{
	var className : String = "";

	private val map = new LinkedHashMap[String, Any]()

	def put(key : String, value : Any) = map.put(key, value)

	def get(key : String) : Any = map.get(key)

	def size = map.size

	def iterator = map.keySet().iterator

	override def hashCode = map.hashCode

	override def equals(other : Any) : Boolean =
	{
		if(other == null) return false
		other.hashCode == hashCode
	}
}
