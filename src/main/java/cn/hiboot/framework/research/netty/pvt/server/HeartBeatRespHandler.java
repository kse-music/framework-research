package cn.hiboot.framework.research.netty.pvt.server;

import cn.hiboot.framework.research.netty.pvt.MessageType;
import cn.hiboot.framework.research.netty.pvt.struct.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/28 0:19
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
            System.out.println("Receive client Heart beat message : ---> " + message);
            NettyMessage heartBeat = NettyMessage.build(MessageType.HEARTBEAT_RESP);
            System.out.println("Send Heart beat response message to client: ---> " + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

}
