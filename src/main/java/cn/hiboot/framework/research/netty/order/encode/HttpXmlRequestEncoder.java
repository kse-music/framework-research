package cn.hiboot.framework.research.netty.order.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.InetAddress;
import java.util.List;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/23 22:37
 */
public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws Exception {

        ByteBuf body = encode0(ctx,msg.getBody());
        FullHttpRequest request = msg.getRequest();

        if(request == null){
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/do", body);
            request.headers().set(HttpHeaderNames.HOST, InetAddress.getLocalHost().getHostAddress())
                            .set(HttpHeaderNames.CONNECTION,HttpHeaderValues.CLOSE)
                            .set(HttpHeaderNames.ACCEPT_ENCODING,HttpHeaderValues.GZIP+","+HttpHeaderValues.DEFLATE)
                            .set(HttpHeaderNames.ACCEPT_CHARSET,CharsetUtil.ISO_8859_1+",utf-8;q=0.7,*;q=0.7")
                            .set(HttpHeaderNames.ACCEPT_LANGUAGE,"zh")
                            .set(HttpHeaderNames.USER_AGENT,"Netty xml Http Client side")
                            .set(HttpHeaderNames.ACCEPT,"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        }

        HttpUtil.setContentLength(request,body.readableBytes());
        out.add(request);

    }

}
