package com.yzh44yzh.scalaAmf

import java.util.{Date, Map, LinkedHashMap}


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

	def containsKey(key : String) : Boolean = map.containsKey(key)

	def remove(key : String) : Any = map.remove(key)

	def iterator = map.keySet().iterator

	def getStr(key : String) : String =
	{
		val value = map.get(key)
		if(value == null) "" else value.toString
	}

	def getBool(key : String) : Boolean =
	{
		val value = map.get(key)
		if(value == null) false else value.equals(true)
	}

	def getInt(key : String) : Int =
	{
		val value = map.get(key)
		try
		{
			value.asInstanceOf[Int]
		}
		catch
		{
			case e : Exception => 0
		}
	}

	def getDouble(key : String) : Double =
	{
		val value = map.get(key)
		try
		{
			value.asInstanceOf[Double]
		}
		catch
		{
			case e : Exception => 0.0
		}
	}

	def getDate(key : String) : Date =
	{
		val value = map.get(key)
		try
		{
			value.asInstanceOf[Date]
		}
		catch
		{
			case e : Exception => null
		}
	}

	def getAmfClass(key : String) : AmfClass =
	{
		val value = map.get(key)
		try
		{
			value.asInstanceOf[AmfClass]
		}
		catch
		{
			case e : Exception => null
		}
	}

	def getMap : Map[String, Any] = map

	override def hashCode = map.hashCode

	override def equals(other : Any) : Boolean =
	{
		if(other == null) return false
		other.hashCode == hashCode
	}
}
