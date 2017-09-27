package com.company;

import java.util.LinkedList;
//import java.util.ArrayList;
//import kotlin.Pair;

class TestCases {
    public enum TestEnum {
        TopologicalSort,
        ListInsert,
        NoteDeletion,
        ListToTree,
        StrComp,
        PaindromeSubseq,
        AStar
    }

    static void runTestCase(TestEnum test) {
        switch (test) {
            case AStar:
                TestCases.runAStart();
                break;
            case StrComp:
                String str1 = "Thas";
                String str2 = "This";
                TestCases.runStrComp(str1, str2);
                break;
            case ListInsert:
                TestCases.runListInsert();
                break;
            case ListToTree:
                TestCases.runListToTree();
                break;
            case NoteDeletion:
                int node = 5;
                TestCases.runNodeDeletion(node);
                break;
            case PaindromeSubseq:
                String str = "fbbfbab";
                int len = TestCases.longestPalindromeSubseq(str);
                System.out.println("The longest palindrome length of '" + str + "' is " + len);
                break;
            case TopologicalSort:
                TestCases.runTopologicalSort();
                break;
            default:
                break;
        }
    }

    private static void runTopologicalSort() {
        Graph g = new Graph(6);
        g.addEdge(5, 2);
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        System.out.println("Following is a Topological " +
                "sort of the given graph");
        g.topologicalSort();
    }

    private static void runListInsert() {
        CustomList list = new CustomList();
        CustomList.Node new_node;
        new_node = list.newNode(5);
        list.sortedInsert(new_node);
        new_node = list.newNode(10);
        list.sortedInsert(new_node);
        new_node = list.newNode(7);
        list.sortedInsert(new_node);
        new_node = list.newNode(3);
        list.sortedInsert(new_node);
        new_node = list.newNode(1);
        list.sortedInsert(new_node);
        new_node = list.newNode(9);
        list.sortedInsert(new_node);

        System.out.println("Created Linked List");
        list.printList();
    }

    private static void runNodeDeletion(Integer n) {
        CustomList list = new CustomList();
        CustomList.Node new_node;
        new_node = list.newNode(5);
        list.sortedInsert(new_node);
        new_node = list.newNode(10);
        list.sortedInsert(new_node);
        new_node = list.newNode(7);
        list.sortedInsert(new_node);
        new_node = list.newNode(3);
        list.sortedInsert(new_node);
        new_node = list.newNode(1);
        list.sortedInsert(new_node);
        new_node = list.newNode(9);
        list.sortedInsert(new_node);

        CustomList.Node toDelete = list.newNode(n);
        list.deleteNode(toDelete);
    }

    private static TreeNode genStaticTree() {
        TreeNode four = new TreeNode(4);
        TreeNode one = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode five = new TreeNode(5);
        TreeNode six = new TreeNode(6);

        four.left = two;
        four.right = six;
        two.left = one;
        two.right = three;
        six.left = five;

        return four;
    }

    private static void runListToTree() {
        //TreeNode root = genStaticTree();

        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);

 /*       System.out.print("List: " + list.peek());
        for (Integer i)*/

        TreeNode root = ListToTree.convertListToTree(list);

        ListToTree.printTreeBFS(root);
    }

    private static void runStrComp(String str1, String str2) {
        LinkedList<String> strLst1 = new LinkedList<>();
        LinkedList<String> strLst2 = new LinkedList<>();

        for (Integer i = 0; i < str1.length(); i++)
            strLst1.add(Character.toString(str1.charAt(i)));

        for (Integer i = 0; i < str2.length(); i++)
            strLst2.add(Character.toString(str2.charAt(i)));

        System.out.print(CustomList.strComp(strLst1, strLst2));
    }

    private static int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0)
            return 0;

        int max = 1;
        String subStr = "";

        for (int i = 0; i < s.length(); i++) {
            for (int j = s.length(); j > i; j--) {
                if (s.charAt(i) != s.charAt(j-1))
                    continue;
                subStr = s.substring(i, j);
                if (isPalindromic(subStr) && subStr.length() > max)
                    max = subStr.length();
            }
        }

        return max;
    }

    private static boolean isPalindromic(String s) {
        if (s == null || s.length() == 0)
            return false;

        if (s.length() == 1)
            return true;

        String str = new StringBuilder(s).reverse().toString();
        return s.compareTo(str) == 0;
    }

    private static void runAStart() {
        //ArrayList<Pair<Integer, Integer>> map = new ArrayList<>(15);
        //ArrayList<Pair<Integer, Integer>> path =
        //        AStarKt.aStarSearch(new Pair<>(1, 1), new Pair<>(10, 10), map);
    }
}
