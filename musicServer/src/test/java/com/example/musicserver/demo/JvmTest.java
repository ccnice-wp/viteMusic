package com.example.musicserver.demo;

import org.junit.jupiter.api.Test;

/**
 * @author ChengJianhong
 * @date 2023/3/20
 */
public class JvmTest {

    class User {
        int id;
        String name;
        User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    void alloc(int i) {
        new User(i, "name"+i);
    }

    @Test
    public void test1() {
        JvmTest jvmTest = new JvmTest();
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            jvmTest.alloc(i);
        }
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
    }

    static void printMemoryInfo(){
        System.out.println("total: " + Runtime.getRuntime().totalMemory());
        System.out.println("free: " +Runtime.getRuntime().freeMemory());
    }

    @Test
    public void test2() {
        printMemoryInfo();


        byte[] b = new byte[1024*1024];
        System.out.println("------------------");

        printMemoryInfo();

    }

    // 计算递归调用次数
    static int count = 0;

    /**
     * 递归查看栈深度
     */
    static void foo() {
        count++;
        foo();
    }

    @Test
    public void test3() {
        try {
            foo();
        } catch (Throwable t) {
            System.out.println(count);
            t.printStackTrace();// 栈溢出，递归调用过深
        }
    }
}
