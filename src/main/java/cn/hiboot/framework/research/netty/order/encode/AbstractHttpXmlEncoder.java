package cn.hiboot.framework.research.netty.order.encode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;

import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/23 22:38
 */
public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {

    IBindingFactory factory;
    StringWriter writer;
    final static String CHARSET_NAME = "UTF-8";
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);


    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws Exception {
        factory = BindingDirectory.getFactory(body.getClass());
        writer = new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(body, CHARSET_NAME, null, writer);
        String xmlStr = writer.toString();
        writer.close();
        writer = null;
        return Unpooled.copiedBuffer(xmlStr,UTF_8);

    }

    @Override
    @Deprecated
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(writer != null){
            writer.close();
            writer = null;
        }
    }

}
