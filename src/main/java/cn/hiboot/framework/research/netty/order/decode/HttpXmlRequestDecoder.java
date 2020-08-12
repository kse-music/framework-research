package cn.hiboot.framework.research.netty.order.decode;

import cn.hiboot.framework.research.netty.http.HttpFileServerHandler;
import cn.hiboot.framework.research.netty.order.encode.HttpXmlRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/23 22:37
 */
public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {

    public HttpXmlRequestDecoder(Class<?> clazz) {
        super(clazz);
    }

    public HttpXmlRequestDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {
        if(!msg.decoderResult().isSuccess()){
            HttpFileServerHandler.sendError(ctx,HttpResponseStatus.BAD_REQUEST);
            return;
        }
        HttpXmlRequest request = new HttpXmlRequest(msg,decode0(ctx,msg.content()));
        out.add(request);
    }
}
