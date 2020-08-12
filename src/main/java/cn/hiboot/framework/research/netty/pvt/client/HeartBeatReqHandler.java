package cn.hiboot.framework.research.netty.pvt.client;

import cn.hiboot.framework.research.netty.pvt.MessageType;
import cn.hiboot.framework.research.netty.pvt.struct.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/28 0:15
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeat;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            heartBeat = ctx.executor().scheduleAtFixedRate(() -> {
                NettyMessage heartBeat = NettyMessage.build(MessageType.HEARTBEAT_REQ);
                System.out.println("Client Send heart beat message to server : ---> " + heartBeat);
                ctx.writeAndFlush(heartBeat);
            },0,5000, TimeUnit.MILLISECONDS);
        } else if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()) {
            System.out.println("Client Receive server heart beat message : ---> " + message);
        } else {
            ctx.fireChannelRead(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //断连期间，心跳定时器停止工作，不再发送心跳请求信息
        if(heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }

}
