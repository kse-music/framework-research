package cn.hiboot.framework.research.netty.order.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/23 22:37
 */
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {

        ByteBuf body = encode0(ctx,msg.getResult());
        FullHttpResponse response = msg.getResponse();

        if(response == null){
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK, body);
        }else {
            response = new DefaultFullHttpResponse(msg.getResponse().protocolVersion(),msg.getResponse().status());
        }
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/xml");
        HttpUtil.setContentLength(response,body.readableBytes());
        out.add(response);

    }

}
