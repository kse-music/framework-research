package cn.hiboot.framework.research.netty.pvt.struct;


import cn.hiboot.framework.research.netty.pvt.MessageType;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/27 0:46
 */
public class NettyMessage {
    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                '}';
    }

    public static NettyMessage build(MessageType type){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(type.value());
        message.setHeader(header);
        return message;
    }
}
