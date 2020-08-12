package cn.hiboot.framework.research.netty.pvt.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/28 0:08
 */
public class NettyMarshallingDecoder extends MarshallingDecoder {

    public NettyMarshallingDecoder(UnmarshallerProvider provider) {
        super(provider);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        return super.decode(ctx, in);
    }
}
