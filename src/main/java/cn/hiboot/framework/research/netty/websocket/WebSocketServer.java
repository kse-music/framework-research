package cn.hiboot.framework.research.netty.websocket;

import cn.hiboot.framework.research.netty.base.AbstractNettyServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/24 23:58
 */
public class WebSocketServer extends AbstractNettyServer {

    @Override
    protected void initialChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("http-codec",new HttpServerCodec());
        pipeline.addLast("http-aggregator",new HttpObjectAggregator(65536));
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast("handler", new SimpleChannelInboundHandler<Object>() {

            private WebSocketServerHandshaker handshaker;

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                if(msg instanceof FullHttpRequest){
                    handleHttpRequest(ctx,(FullHttpRequest)msg);
                }else if(msg instanceof WebSocketFrame){
                    handleWebSocketFrame(ctx,(WebSocketFrame)msg);
                }
            }

            @Override
            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                ctx.flush();
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                cause.printStackTrace();
                ctx.close();
            }

            private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
                if(!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))){
                    sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.BAD_REQUEST));
                    return;
                }
                WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:"+getPort()+"/websocket",null,false);
                handshaker = wsFactory.newHandshaker(req);
                if(handshaker == null){
                    WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
                }else {
                    handshaker.handshake(ctx.channel(),req);
                }
            }

            private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
                if(res.status().code() != 200){
                    ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
                    res.content().writeBytes(buf);
                    buf.release();
                    HttpUtil.setContentLength(res,res.content().readableBytes());
                }
                ChannelFuture channelFuture =  ctx.channel().writeAndFlush(res);
                if(!HttpUtil.isKeepAlive(req) || res.status().code() != 200){
                    channelFuture.addListener(ChannelFutureListener.CLOSE);
                }
            }

            private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
                if(frame instanceof CloseWebSocketFrame){
                    handshaker.close(ctx.channel(),((CloseWebSocketFrame) frame).retain());
                    return;
                }

                if(frame instanceof PingWebSocketFrame){
                    ctx.channel().write(new PingWebSocketFrame(frame.content().retain()));
                    return;
                }

                if(!(frame instanceof TextWebSocketFrame)){
                    throw new UnsupportedOperationException(String.format("%s frame types not supported",frame.getClass().getName()));
                }

                String request = ((TextWebSocketFrame) frame).text();

                ctx.channel().write(new TextWebSocketFrame(request + ", 欢迎使用Netty WebSocket 服务，现在时刻：" + new Date()));
            }

        });
    }

    @Test
    public void run(){
        new WebSocketServer().start();
    }

}
