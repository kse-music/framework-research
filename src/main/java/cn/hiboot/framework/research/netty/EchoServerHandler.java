package cn.hiboot.framework.research.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/5/24 14:46
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        String body = (String) msg;
//        System.out.println("This is " + ++counter +"times receive client :  " + body);
//        body += "$_";
//        ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
//        ctx.writeAndFlush(resp);
        System.out.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
