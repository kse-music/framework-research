package cn.hiboot.framework.research.netty.order.encode;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/23 22:39
 */
public class HttpXmlRequest {
    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
