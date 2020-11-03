package io.netty.example.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
/**
 * 描述
 *
 * @author fanqinhai
 * @since 2020/8/25 10:10 上午
 */
@Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 通知所有连接上的客户端
     */
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[" + channel.remoteAddress().toString() + "]:进来了 \n");
        channels.add(channel);
        System.out.println("当前用户数：" + channels.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[" + channel.remoteAddress().toString() + "]:离开了 \n");
        System.out.println("当前用户数：" + channels.size());
    }

    /**
     * 当服务器监听到客户端活动时
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "]: 上线了");
    }

    /**
     * 离线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "]: 下线了");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel inComing = ctx.channel();

        System.out.println("channels.size=" + channels.size());
        for (Channel channel : channels) {
            if (channel != inComing) {
                channel.writeAndFlush("[" + inComing.remoteAddress() + " ]说：" + msg + "\n");
            } else {
                channel.writeAndFlush("我说：" + msg + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "异常！");
        ctx.close();
    }
}
