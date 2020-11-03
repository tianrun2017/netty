package io.netty.example.tianrun;

/**
 * 描述
 *
 * @author fanqinhai
 * @since 2020/7/13 10:19 上午
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(0));
        System.out.println(isPowerOfTwo(1));
        System.out.println(isPowerOfTwo(3));

        System.out.println(Math.abs(11 % -1));
        System.out.println(11 % -1);
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

}
