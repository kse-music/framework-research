package cn.hiboot.framework.research.netty.pvt.server;

import cn.hiboot.framework.research.netty.pvt.MessageType;
import cn.hiboot.framework.research.netty.pvt.ResultType;
import cn.hiboot.framework.research.netty.pvt.struct.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/28 0:19
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private Map<String,Boolean> nodeCheck = new ConcurrentHashMap<>();
    private String[] whiteList = {"127.0.0.1","192.168.1.4"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage)msg;
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.value()){
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;

            if(nodeCheck.containsKey(nodeIndex)){
                System.out.println("重复登录,拒绝请求!");
                loginResp = buildResponse(ResultType.FAIL);
            }else {
                InetSocketAddress address = (InetSocketAddress)ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOK = false;
                for (String s : whiteList) {
                    if(s.equals(ip)){
                        isOK = true;
                        break;
                    }
                }
                loginResp = isOK ? buildResponse(ResultType.SUCCESS) : buildResponse(ResultType.FAIL);
                if(isOK){
                    nodeCheck.put(nodeIndex,true);
                }
                System.out.println("The login response is : " + loginResp + " body [" + loginResp.getBody() +"]");
                ctx.writeAndFlush(loginResp);
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResponse(ResultType result) {
        NettyMessage message = NettyMessage.build(MessageType.LOGIN_RESP);
        message.setBody(result.value());
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
