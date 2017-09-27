package com.company;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Ziwei on 1/21/2017.
 */
class Graph {
    private int V;
    private LinkedList<Integer>[] adj;

    Graph(int v)
    {
        this.V = v;
        adj = new LinkedList[v];
        for (int i=0; i < v; i++)
            adj[i] = new LinkedList<>();
    }

    void addEdge(int v, int w) { adj[v].add(w); }

    void topologicalSort()
    {
        Stack<Integer> stack = new Stack<>();

        boolean visited[] = new boolean[this.V];
        for (int i=0; i<V; i++)
            visited[i] = false;

        for (int i=0; i<V; i++)
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);

        while (!stack.isEmpty())
        {
            System.out.print(stack.pop() + " ");
        }
    }

    private void topologicalSortUtil(int i, boolean visited[], Stack<Integer> stack)
    {
        visited[i] = true;

        for (Integer v: adj[i]) {
            if (!visited[v])
                topologicalSortUtil(v, visited, stack);
        }

//        Integer v;
//        Iterator<Integer> it = adj[i].iterator();
//        while (it.hasNext())
//        {
//            v = it.next();
//            if (visited[v] == false)
//                topologicalSortUtil(v, visited, stack);
//        }

        stack.push(i);
    }
}
