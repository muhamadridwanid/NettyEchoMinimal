package id.muhamadridwan.tutorial.nettyechominimal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mridwan on 18/10/2017.
 */
public class ServerApp {

    private static final Logger LOG = Logger.getLogger(ServerApp.class.getName());
    private static int port = 8000;

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel
                                    .pipeline()
                                    .addLast(
                                            new LoggingHandler(LogLevel.INFO),
                                            new StringDecoder(),
                                            new StringEncoder(),
                                            new ServerListener()
                                    );
                        }
                    });

            LOG.info("Server starting on port " + port);
            ChannelFuture cn = bootstrap.bind(port).sync();
            cn.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOG.log(Level.SEVERE, e.toString(), e);

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
