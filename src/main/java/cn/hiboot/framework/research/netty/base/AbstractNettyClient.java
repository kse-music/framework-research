package cn.hiboot.framework.research.netty.base;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/24 23:33
 */
public abstract class AbstractNettyClient {

    private String host;
    private int port;

    public AbstractNettyClient() {
        this("127.0.0.1",8080);
    }

    public AbstractNettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            initialChannel(ch);
                        }
                    });
            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }

    protected abstract void initialChannel(SocketChannel ch);

}
