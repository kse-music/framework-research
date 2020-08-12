package cn.hiboot.framework.research.netty.order;

import cn.hiboot.framework.research.netty.base.AbstractNettyServer;
import cn.hiboot.framework.research.netty.order.decode.HttpXmlRequestDecoder;
import cn.hiboot.framework.research.netty.order.encode.HttpXmlRequest;
import cn.hiboot.framework.research.netty.order.encode.HttpXmlResponse;
import cn.hiboot.framework.research.netty.order.encode.HttpXmlResponseEncoder;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/23 23:42
 */
public class HttpXmlServer extends AbstractNettyServer {

    @Test
    public void run() {
        new HttpXmlServer().start();
    }

    @Override
    protected void initialChannel(SocketChannel ch) {
        ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
        ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));

        ch.pipeline().addLast("xml-decoder",new HttpXmlRequestDecoder(Order.class,true));

        ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());

        ch.pipeline().addLast("xml-encoder",new HttpXmlResponseEncoder());

        ch.pipeline().addLast("xmlServerHandler", new SimpleChannelInboundHandler<HttpXmlRequest>() {

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, HttpXmlRequest xmlRequest) throws Exception {
                HttpRequest request = xmlRequest.getRequest();
                Order order = (Order)xmlRequest.getBody();
                System.out.println(JSON.toJSONString(order));
                doBusiness(order);
                ChannelFuture channelFuture = ctx.writeAndFlush(new HttpXmlResponse(null, order));
                if(!HttpUtil.isKeepAlive(request)){
                    channelFuture.addListener((future) ->  ctx.close());
                }
            }

            private void doBusiness(Order order){
                order.getCustomer().setFirstName("狄");
                order.getCustomer().setLastName("仁杰");
                order.getCustomer().setMiddleNames(Lists.newArrayList("李元芳"));
                Address address = order.getShipTo();
                address.setCity("洛阳");
                address.setCountry("大唐");
                address.setState("河南道");
                address.setPostCode("123456");
                order.setBillTo(address);
                order.setShipTo(address);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
                cause.printStackTrace();
                if(ctx.channel().isActive()){
                    sendError(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR);
                }
            }

            private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
                FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                        Unpooled.copiedBuffer("Failure: "+status.toString()+"\r\n", CharsetUtil.UTF_8));
                response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html;charset=UTF-8");
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            }

        });
    }
}
