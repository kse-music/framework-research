package cn.hiboot.framework.research.netty.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/15 22:56
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        MessagePack msgpack = new MessagePack();
        byte[] array = new byte[msg.readableBytes()];
        msg.getBytes(msg.readerIndex(),array);
        out.add(msgpack.read(array));
    }
}
