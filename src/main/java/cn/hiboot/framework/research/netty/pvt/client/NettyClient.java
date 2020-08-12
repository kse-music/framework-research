package cn.hiboot.framework.research.netty.pvt.client;

import cn.hiboot.framework.research.netty.pvt.NettyConstant;
import cn.hiboot.framework.research.netty.pvt.codec.NettyMessageDecoder;
import cn.hiboot.framework.research.netty.pvt.codec.NettyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/28 0:52
 */
public class NettyClient {

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    EventLoopGroup group = new NioEventLoopGroup();

    public void connect(int port,String host) throws Exception{
        try {
            Bootstrap b = new Bootstrap();

            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024,4,4));
                            ch.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
                            ch.pipeline().addLast("LoginAuthHandler",new LoginAuthReqHandler());
                            ch.pipeline().addLast("HeartBeatHandler",new HeartBeatReqHandler());
                        }
                    });

            ChannelFuture f = b.connect(new InetSocketAddress(host,port),new InetSocketAddress(NettyConstant.LOCALIP,NettyConstant.LOCAL_PORT)).sync();
            f.channel().closeFuture().sync();
        } finally {
            executor.execute(() -> {
                try{
                    TimeUnit.SECONDS.sleep(5);
                    try{
                        connect(NettyConstant.PORT,NettyConstant.REMOTEIP);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            });
        }
    }


    @Test
    public void run() throws Exception {
        connect(NettyConstant.PORT,NettyConstant.REMOTEIP);
    }

}
