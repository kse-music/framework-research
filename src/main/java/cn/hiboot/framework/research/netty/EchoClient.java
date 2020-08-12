package cn.hiboot.framework.research.netty;

import cn.hiboot.framework.research.netty.code.MsgpackDecoder;
import cn.hiboot.framework.research.netty.code.MsgpackEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.jupiter.api.Test;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/13 23:04
 */
public class EchoClient {

    private int sendNumber;

    public EchoClient(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    public void connect(int port, String host) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));

//                            ch.pipeline().addLast(new FixedLengthFrameDecoder(20));

                            ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                            ch.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
                            ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());

                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoClientHandler(sendNumber));
                        }
                    });

            ChannelFuture f = b.connect(host,port).sync();

            f.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }


    @Test
    public void run() throws Exception {
        int port = 10110;
        new EchoClient(1).connect(port,"127.0.0.1");
    }

}
