package com.yzh44yzh.scalaAmf.mina

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFactory
import org.apache.mina.filter.codec.ProtocolDecoder
import org.apache.mina.filter.codec.ProtocolEncoder

class AmfCodecFactory extends ProtocolCodecFactory
{
    private val encoder = new AmfEncoder
    private val decoder = new AmfDecoder

    def getEncoder(ioSession: IoSession): ProtocolEncoder =
    {
        encoder
    }

    def getDecoder(ioSession: IoSession): ProtocolDecoder =
    {
        decoder
    }
}
