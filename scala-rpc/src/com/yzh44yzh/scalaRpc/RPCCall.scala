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

	// TODO
	// getInt
	// getString
	// and so on
}
