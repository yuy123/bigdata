package com.demo.bean;

public class Node {

    int data;
    Node next;
    static Node prev;

    public Node() {
    }

    public Node(int data) {
        this.data = data;
    }

    public Node(int data, Node next) {
        this.data = data;
        this.next = next;
    }

    //遍历数据
    public static void list(Node head) {
        Node temp = head;
        while (temp.next != null) {
            System.out.println("temp.next.data==" + temp.next.data);
            temp = temp.next;
        }
    }

    //添加数据
    public static void addData(int value) {
        Node node = new Node(value);
        Node p = prev;
        while (p.next != null) {
            p = p.next;
        }
        p.next = node;
        System.out.println("prev.next.data==" + prev.next.data);
    }

}