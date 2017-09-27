package com.company;

import java.util.LinkedList;

/**
 * Created by Ziwei on 1/21/2017.
 */
class CustomList {
    Node head;

    class Node {
        int data;
        Node next;

        Node(int d) { data = d; next = null; }
    }

    Node newNode(int data) {
        return new Node(data);
    }

    void printList() {
        Node currNode = head;
        while (currNode != null) {
            System.out.print(currNode.data + " ");
            currNode = currNode.next;
        }
    }

    void sortedInsert(Node n) {
        if (head == null || n.data <= head.data){
            n.next = head;
            head = n;
        } else {
            Node currNode = head.next;
            Node preNode = head;

            while (currNode != null) {
                if (currNode.data >= n.data && n.data >= preNode.data) {
                    preNode.next = n;
                    n.next = currNode;
                    return;
                }

                preNode = currNode;
                currNode = currNode.next;
            }

            // we're at the end of the list.
            preNode.next = n;
        }
    }

    void deleteNode(Node n) {
        if (head == null)
        {
            System.out.println("List is empty...");
            return;
        }

        boolean done = false;

        if (n.data == head.data)
        {
            head = head.next;
            done = true;
        }
        else
        {
            Node preNode = head;
            Node currNode = head.next;

            while (currNode != null) {
                if (currNode.data == n.data) {
                    preNode.next = currNode.next;
                    done = true;
                    break;
                }

                preNode = currNode;
                currNode = currNode.next;
            }
        }

        if (!done)
            System.out.println("Node not found!");
        else
            System.out.println("Node " + n.data + " has been deleted!");

        this.printList();
    }

    static Integer strComp(LinkedList<String> str1, LinkedList<String> str2) {
        if ((str1 == null && str2 == null) || str1.size() == 0 && str2.size() == 0)
            return 0;

        if (str1.size() < str2.size())
            return -1;

        if (str1.size() > str2.size())
            return 1;

        // Size are identical
        char c1,c2;
        while (str1.isEmpty() != true) {
            c1 = str1.poll().charAt(0);
            c2 = str2.poll().charAt(0);

            if (c1 < c2)
                return -1;
            else if (c1 > c2)
                return 1;
        }

        return 0;
    }
}
