package com.pascalx.concurrence;

import java.util.concurrent.TimeUnit;

/**
 * 等待通知机制经典范式
 *
 * @author yanghui
 * @date 2018/7/20.
 */
public class SynWaitAndNotify {

    //保证可见性
    static volatile boolean flag = false;
    static Object lock = new Object();


    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (!flag) {  //重新获得锁后，要重新检查flag
                        try {
                            /*
                            当前线程要锁定该对象lock之后，才能用锁定的对象执行这些方法，这里需要用到synchronized关键字，
                            锁定哪个对象就用哪个对象来执行notify(), notifyAll(),wait(), wait(long), wait(long, int)操作，
                            否则就会报IllegalMonitorStateException异常。
                             */
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //flag满足，do something
                    System.out.println("do something");
                }

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = true;
                    lock.notifyAll();
                }
            }
        }).start();
    }

}
