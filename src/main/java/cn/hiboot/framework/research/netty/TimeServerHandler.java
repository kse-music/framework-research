package cn.hiboot.framework.research.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;


/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/5/24 14:46
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    /**
     * 收到数据时调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
//            ByteBuf buf = (ByteBuf) msg;
//            byte[] req = new byte[buf.readableBytes()];
//            buf.readBytes(req);
//            String body = new String(req,CharsetUtil.UTF_8).substring(0,req.length - System.getProperty("line.separator").length());
            String body = (String) msg;
            System.out.println(body + " the counter is " + ++counter);
            String currentTime = ("QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER");
            currentTime += System.getProperty("line.separator");
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.writeAndFlush(resp);
        } finally {
            // 抛弃收到的数据
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 当Netty由于IO错误或者处理器在处理事件时抛出异常时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
