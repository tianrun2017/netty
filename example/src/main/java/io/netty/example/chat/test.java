package io.netty.example.chat;

import java.nio.charset.StandardCharsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
/**
 * 描述
 *
 * @author fanqinhai
 * @since 2020/8/25 3:49 下午
 */
public class test {

    public static void main(String[] args) {
        ByteBuf test = Unpooled.copiedBuffer("test", StandardCharsets.UTF_8);
        byte[] array = test.array();
        int size = test.readableBytes();
        byte aByte = test.getByte(0);
        for (int i = 0; i < size; i++) {
            System.out.println((char) test.getByte(i));
        }
    }
}
