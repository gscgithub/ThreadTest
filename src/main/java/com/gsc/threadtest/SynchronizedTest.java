package com.gsc.threadtest;

import org.junit.Test;

public class SynchronizedTest {

    @Test
    public void test1() {
        synchronized (this) {
            synchronized (this) {
                System.out.println("synchronized can reentrant");
            }
        }
    }
}
