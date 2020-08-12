package cn.hiboot.framework.research.netty.pvt.codec;

import cn.hiboot.framework.research.netty.pvt.MarshallingCodeCFactory;
import cn.hiboot.framework.research.netty.pvt.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/27 0:50
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    NettyMarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() {
        this.marshallingEncoder = MarshallingCodeCFactory.buildMarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        if(msg == null || msg.getHeader() == null){
            throw new Exception("The encode message is null");
        }
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeLong(msg.getHeader().getSessionId());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());
        sendBuf.writeInt(msg.getHeader().getAttachment().size());

        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes(CharsetUtil.UTF_8);
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);
            value = param.getValue();
            marshallingEncoder.encode(ctx,value,sendBuf);
        }
        key = null;
        keyArray = null;
        value = null;
        if(msg.getBody() != null){
            marshallingEncoder.encode(ctx,msg.getBody(),sendBuf);
        }else {
            sendBuf.writeInt(0);
            sendBuf.setInt(4,sendBuf.readableBytes()-8);
        }
        out.add(sendBuf);
    }

}
