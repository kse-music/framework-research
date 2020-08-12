package cn.hiboot.framework.research.netty.pvt.server;

import cn.hiboot.framework.research.netty.pvt.NettyConstant;
import cn.hiboot.framework.research.netty.pvt.codec.NettyMessageDecoder;
import cn.hiboot.framework.research.netty.pvt.codec.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.junit.jupiter.api.Test;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/28 0:52
 */
public class NettyServer {
    public void bind() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            ch.pipeline().addLast(new NettyMessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                            ch.pipeline().addLast(new LoginAuthRespHandler());
                            ch.pipeline().addLast("HeartBeatHandler", new HeartBeatRespHandler());
                        }
                    });

            ChannelFuture cf = b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
            System.out.println("Netty server start ok : " + (NettyConstant.REMOTEIP + ":" + NettyConstant.PORT));
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Test
    public void run() throws Exception {
        bind();
    }
}
