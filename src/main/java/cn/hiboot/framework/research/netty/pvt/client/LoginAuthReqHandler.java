package cn.hiboot.framework.research.netty.pvt.client;

import cn.hiboot.framework.research.netty.pvt.MessageType;
import cn.hiboot.framework.research.netty.pvt.ResultType;
import cn.hiboot.framework.research.netty.pvt.struct.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/28 0:15
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(NettyMessage.build(MessageType.LOGIN_REQ));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            byte loginResult = (byte) message.getBody();
            if (loginResult != ResultType.SUCCESS.value()) {
                ctx.close();//握手失败，关闭连接
            } else {
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
