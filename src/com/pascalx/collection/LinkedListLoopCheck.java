package com.pascalx.collection;

/**
 * 链表环检测
 * <p>
 * 快慢指针
 *
 * @author yanghui10
 * @date 2018/7/24.
 */
public class LinkedListLoopCheck {

    public static boolean check(Node head) {
        Node slow = head;
        Node fast = head;

        while (slow.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {//相遇
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Node<String> head = new Node<>(new Node(new Node(new Node())));
        System.out.println(check(head));

//        tali->loopStar
        Node<Integer> loopStart = null;
        Node<Integer> tail = null;
        Node<Integer> loopHead = new Node<>(new Node(loopStart = (new Node(new Node(tail = new Node(null, 5), 4), 3)), 2), 1);
        tail.next = loopStart;
        System.out.println(check(loopHead));

    }

    static class Node<T> {
        Node next;
        T data;

        public Node(Node next, T data) {
            this.next = next;
            this.data = data;
        }

        public Node(Node next) {
            this.next = next;
        }

        public Node() {
        }
    }
}
