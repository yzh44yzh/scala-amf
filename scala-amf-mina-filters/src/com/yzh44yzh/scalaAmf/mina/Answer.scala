package com.yzh44yzh.scalaAmf.mina

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import com.yzh44yzh.scalaAmf.AmfClass

class Answer()
{
	private var _action : String = ""

	def action = _action

	def setAction(value : String)
	{
		_action = value
	}

	private var _queryID : Int = -1

	def queryID = _queryID

	private var _data : Any = null

	def data = _data

	def setData(value : Any)
	{
		_data = value
	}

	def init(data : java.util.Map[String, Any])
	{
		_action = data.get("a").asInstanceOf[String]
		if(data.get("q") != null) _queryID = data.get("q").asInstanceOf[Int]
	}

	def rawData() : AmfClass =
	{
		val res = new AmfClass
		res.put("a", action)
		res.put("d", _data)
		if(_queryID != -1) res.put("q", _queryID)
		res
	}
}
