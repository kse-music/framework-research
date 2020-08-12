package cn.hiboot.framework.research.netty.order;

import cn.hiboot.framework.research.netty.base.AbstractNettyClient;
import cn.hiboot.framework.research.netty.order.decode.HttpXmlResponseDecoder;
import cn.hiboot.framework.research.netty.order.encode.HttpXmlRequest;
import cn.hiboot.framework.research.netty.order.encode.HttpXmlRequestEncoder;
import cn.hiboot.framework.research.netty.order.encode.HttpXmlResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import org.junit.jupiter.api.Test;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/23 23:29
 */
public class HttpXmlClient extends AbstractNettyClient {

    @Test
    public void run() {
        new HttpXmlClient().start();
    }

    @Override
    protected void initialChannel(SocketChannel ch) {
        ch.pipeline().addLast("http-decoder",new HttpResponseDecoder());
        ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
        //xml
        ch.pipeline().addLast("xml-decoder",new HttpXmlResponseDecoder(Order.class,true));

        ch.pipeline().addLast("http-encoder",new HttpRequestEncoder());

        ch.pipeline().addLast("xml-encoder",new HttpXmlRequestEncoder());

        ch.pipeline().addLast("xmlClientHandler", new SimpleChannelInboundHandler<HttpXmlResponse>() {

            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
                ctx.writeAndFlush(request);
            }

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
                System.out.println("client receive headers" + msg.getResponse().headers().names());
                System.out.println("client receive body" + msg.getResult());
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
                cause.printStackTrace();
                ctx.close();
            }
        });
    }
}
