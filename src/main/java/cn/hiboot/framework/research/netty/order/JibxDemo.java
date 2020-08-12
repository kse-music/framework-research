package cn.hiboot.framework.research.netty.order;

import org.jibx.runtime.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/8/22 11:25
 */
public class JibxDemo {

    private IBindingFactory factory = null;

    private StringWriter writer = null;

    private StringReader reader = null;

    private final static String CHARSET_NAME = "UTF-8";

    private String encode2Xml(Order order) throws JiBXException, IOException {
        factory = BindingDirectory.getFactory(Order.class);
        writer = new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(order, CHARSET_NAME, null, writer);
        String xmlStr = writer.toString();
        writer.close();
        System.out.println(xmlStr);
        return xmlStr;
    }

    private Order decode2Order(String xmlBody) throws JiBXException {
        reader = new StringReader(xmlBody);
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        Order order = (Order) uctx.unmarshalDocument(reader);
        return order;
    }

    @Test
    public void xmlPoJOBind() throws Exception {
        String body = encode2Xml(OrderFactory.create(123));
        Order order2 = decode2Order(body);
        System.out.println(order2);
    }

}
