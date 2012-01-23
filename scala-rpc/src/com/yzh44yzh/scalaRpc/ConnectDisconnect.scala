package com.yzh44yzh.scalaRpc

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

trait ConnectDisconnect
{
	def onConnect(client : Client)

	def onDisconnect(client : Client)
}
