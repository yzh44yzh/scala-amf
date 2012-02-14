package com.yzh44yzh.scalaRpc

import com.yzh44yzh.scalaAmf.AmfClass

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class RPCCall(input : AmfClass)
{
	val action : String = input.getStr("a")
	val callbackID : Int = input.getInt("q")
	val params : AmfClass = input.getAmfClass("d")
}
