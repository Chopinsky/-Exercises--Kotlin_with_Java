package com.company;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ziwei on 1/22/2017.
 */
public class ListToTree {
    TreeNode root;

    static TreeNode convertListToTree(List<Integer> list) {
        Integer size = list == null ? 0 : list.size();

        if (size == 0)
            return null;

        Integer middle = size/2;
        TreeNode root = new TreeNode(list.get(middle));
        LinkedList<Integer> leftList = null;
        LinkedList<Integer> rightList = null;

        if (middle >= 0)
            leftList = new LinkedList<>(list.subList(0, middle));
            root.left = convertListToTree(leftList);

        if (middle+1 <= size)
            rightList = new LinkedList<>(list.subList(middle+1, size));
            root.right = convertListToTree(rightList);

        return root;
    }

    static void printTreeBFS(TreeNode treeRoot) {
        if (treeRoot == null)
            return;

        LinkedList<TreeNode> list = new LinkedList<>();
        list.addLast(treeRoot);

        ListToTree.printTreeBFSUtil(list);
    }

    private static void printTreeBFSUtil(LinkedList<TreeNode> list) {
        if (list.isEmpty())
            return;

        LinkedList<TreeNode> nextLevel = new LinkedList<>();
        TreeNode node;

        while (!list.isEmpty()) {
            node = list.poll();
            System.out.print(node.data + " ");

            if (node.left != null)
                nextLevel.addLast(node.left);

            if (node.right != null)
                nextLevel.addLast(node.right);
        }

        System.out.println();
        printTreeBFSUtil(nextLevel);
    }
}

class TreeNode {
    Integer data;
    TreeNode left = null;
    TreeNode right = null;

    TreeNode(Integer d) {
        data = d;
    }
}