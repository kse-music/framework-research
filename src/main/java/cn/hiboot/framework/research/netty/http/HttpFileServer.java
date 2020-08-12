package cn.hiboot.framework.research.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.junit.jupiter.api.Test;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/19 23:38
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "/";

    public void bind(int port,String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                            //支持异步发送大的码流，但不占用过多内存
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            ch.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url));
                        }
                    });

            ChannelFuture f = b.bind("127.0.0.1",port).sync();
            System.out.println("HTTP文件服务器启动，网址是："+"http://127.0.0.1:"+port+url);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Test
    public void run() throws Exception {
        int port = 10110;
        bind(port,DEFAULT_URL);
    }

}
