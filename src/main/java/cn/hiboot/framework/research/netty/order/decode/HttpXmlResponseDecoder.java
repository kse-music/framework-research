package cn.hiboot.framework.research.netty.order.decode;

import cn.hiboot.framework.research.netty.order.encode.HttpXmlResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import java.util.List;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/23 22:37
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {

    public HttpXmlResponseDecoder(Class<?> clazz) {
        super(clazz);
    }

    public HttpXmlResponseDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception {
        out.add(new HttpXmlResponse(msg,decode0(ctx,msg.content())));
    }
}
