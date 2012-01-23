package com.yzh44yzh.scalaAmf

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import mina.AmfCodecFactory
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress
import org.apache.log4j.PropertyConfigurator
import com.yzh44yzh.scalaRpc.RPC
;

object SampleServer extends App
{
	PropertyConfigurator.configure("log4j.properties")

	val log = LoggerFactory.getLogger(getClass)
	val port = 2244;

	log.info("SampleServer start at port {}", port);

	try
	{
		val acceptor = new NioSocketAcceptor()
		val cb = acceptor.getFilterChain
		cb.addLast("codec", new ProtocolCodecFilter(new AmfCodecFactory()))

		acceptor.setHandler(new RPC(new GameAPI()))
		acceptor.bind(new InetSocketAddress(port))
	}
	catch
	{
		case e : IOException => log.error("error running server", e)
	}
}
