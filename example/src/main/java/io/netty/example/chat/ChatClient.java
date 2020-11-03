package io.netty.example.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
/**
 * 描述
 *
 * @author fanqinhai
 * @since 2020/8/25 1:47 下午
 */
public class ChatClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .handler(new ChannelInitializer<SocketChannel>() {

                         @Override
                         protected void initChannel(SocketChannel ch) throws Exception {
                             ch.pipeline()
                               //.addLast(new LoggingHandler(LogLevel.INFO))
                               .addLast(new StringDecoder())
                               .addLast(new StringEncoder())
                               .addLast(new ClientHandler());
                         }
                     });
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 8888).sync();
            Channel channel = sync.channel();
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(input.readLine() + "\n");
            }
            //sync.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }
}
