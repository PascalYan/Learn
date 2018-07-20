package com.pascalx.concurrence;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 等待通知机制经典范式
 * @author yanghui10
 * @date 2018/7/20.
 */
public class LockWaitAndNotify {

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    static boolean flag = false;

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                lock.lock();
                try {
                    while (!flag) {
                        condition.await();
                    }

                    System.out.println("do something");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(5);
                    flag = true;
                    condition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }).start();

    }
}
