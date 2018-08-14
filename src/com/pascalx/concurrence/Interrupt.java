package com.pascalx.concurrence;

import java.util.concurrent.TimeUnit;

/**
 * Interrupt机制理解
 *
 * @author yanghui10
 * @date 2018/8/13.
 */
public class Interrupt {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().isInterrupted());

                    Thread.interrupted();
                    System.out.println(Thread.currentThread().isInterrupted());

                    Thread.currentThread().interrupt();
                    System.out.println(Thread.currentThread().isInterrupted());


                    Thread.interrupted();
                    System.out.println(Thread.currentThread().isInterrupted());
//                      System.out.println(Thread.interrupted());
//                      System.out.println(Thread.interrupted());

                }
            }
        });

        t.start();

        t.interrupt();
    }
}
