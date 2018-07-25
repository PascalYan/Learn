package com.pascalx.java;

/**
 * @author yanghui10
 * @date 2018/7/25.
 */
public class CycleReference {


    public static void main(String[] args) {
        new A();
    }

    static class A {
        static {
            System.out.println("A is init");
            B b = new B();
        }

        public A() {
//            构造函数中循环引用报错：
//            Exception in thread "main" java.lang.StackOverflowError
//            at com.pascalx.java.CycleReference$A.<init>(CycleReference.java:20)
            B b = new B();
        }
    }

    static class B {
        static {
            System.out.println("B is init");
            A a = new A();
        }

        public B() {
            A a = new A();
        }
    }
}
