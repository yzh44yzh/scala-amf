package com.yzh44yzh.scalaRpc

import com.yzh44yzh.scalaAmf.AmfClass

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class RPCCall(input : AmfClass)
{
	val action : String = input.get("a").asInstanceOf[String]
	val callbackID : Int = input.get("q").asInstanceOf[Int]
	val params : AmfClass = input.get("d").asInstanceOf[AmfClass]

	var result : AmfClass = new AmfClass

	def answer() : AmfClass =
	{
		val a = new AmfClass
		a.put("q", callbackID)
		a.put("d", result)
		a
	}
}
