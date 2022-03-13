package com.demo.jihe.List;

import com.demo.bean.Node;

import java.util.LinkedList;

public class LinkedListDemo1 {
    public static void main(String[] args) {
        System.out.println("link list demo");
        LinkedList<String> linklist = new LinkedList<String>();
        linklist.add("a");
        linklist.add("b");
        linklist.add("c");
        LinkedList<String> linklist2 = new LinkedList<String>();
        linklist.add("b");
        linklist.add("c");
        linklist.add("a");
        linklist.add("d");
        String first = linklist.getFirst();
        System.out.println("=first=" + first);
        String last = linklist.getLast();
        System.out.println("=last=" + last);

        int length = linklist2.size() > linklist.size() ? linklist2.size() : linklist.size();
        for (int i = 0; i <length ;i++) {

        }

    }
/*
    public Node getIntersectionNode(Node headA, Node headB) {
        Node l1 = headA, l2 = headB;
        while (l1 != l2) {
            l1 = (l1 == null) ? headB : l1.next;
            l2 = (l2 == null) ? headA : l2.next;
        }
        return l1;
    }
*/


}
