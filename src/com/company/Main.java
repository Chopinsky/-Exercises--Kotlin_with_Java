package com.company;

import com.company.TestCases.*;

public class Main {
    public static void main(String[] args) {
        TestEnum test = TestEnum.TopologicalSort;

        if (args.length > 0) {
            try {
                test = TestEnum.valueOf(args[0]);
            } catch (Exception e) {
                System.out.println("Illegal arguments: " + e.getMessage());
            }
        }

        TestCases.runTestCase(test);
    }
}
