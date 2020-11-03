package io.netty.example.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
/**
 * 描述
 *
 * @author fanqinhai
 * @since 2020/8/25 10:00 上午
 */
public class ChatServer {

    public static void main(String[] args) {

        NioEventLoopGroup bose = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bose, work)
                           .channel(NioServerSocketChannel.class)
                           .handler(new LoggingHandler(LogLevel.DEBUG))
                           .option(ChannelOption.SO_BACKLOG, 120)
                           .childHandler(new ChannelInitializer<SocketChannel>() {
                               @Override
                               protected void initChannel(SocketChannel ch) throws Exception {
                                   ChannelPipeline pipeline = ch.pipeline();
                                   pipeline.addLast("stringDecoder", new StringDecoder())
                                           .addLast("stringEncoder", new StringEncoder())
                                           .addLast("chatServerHandler", new ChatServerHandler());
                               }
                           });

            ChannelFuture sync = serverBootstrap.bind(8888).sync();

            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            work.shutdownGracefully();
            bose.shutdownGracefully();
        }
    }
}
