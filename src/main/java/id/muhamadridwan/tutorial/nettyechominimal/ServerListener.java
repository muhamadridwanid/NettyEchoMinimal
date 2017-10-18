package id.muhamadridwan.tutorial.nettyechominimal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created by mridwan on 18/10/2017.
 */
public class ServerListener extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TimeUnit.SECONDS.sleep(1); //Simulasi Proses
        ctx.channel().writeAndFlush("Response -> " + msg.toString());
    }

}
