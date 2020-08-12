package cn.hiboot.framework.research.netty.order.encode;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/23 23:21
 */
public class HttpXmlResponse {
    private FullHttpResponse response;
    private Object result;

    public HttpXmlResponse(FullHttpResponse response, Object result) {
        this.response = response;
        this.result = result;
    }

    public FullHttpResponse getResponse() {
        return response;
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
