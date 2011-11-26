/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.expekt.threading;

/**
 *
 * @author malcolm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        int n = 0, calculationCount = 0, threadPoolSize = 0, r = 0;
        boolean argsOk = true;
        try {
            for (int i = 0; i < args.length; i+=2) {
                if ("-n".equals(args[i])) {
                    n = Integer.parseInt(args[i+1]);
                    r = r | 0x1;
                } else if ("-c".equals(args[i])) {
                    calculationCount = Integer.parseInt(args[i+1]);
                    r = r | 0x2;
                } else if ("-t".equals(args[i])) {
                    threadPoolSize = Integer.parseInt(args[i+1]);
                    r = r | 0x4;
                }
            }
            if (r != 7) {
                argsOk = false;
            }
        } catch (Exception e) {
            argsOk = false;
        }

        if (!argsOk) {
            printUsage();
        } else {
            PerformanceTester tester = new PerformanceTesterImpl();
            printResults(
                    tester.runPerformanceTest(
                    new FibCalcImpl((StatisticsHolder) tester, n),
                    calculationCount,
                    threadPoolSize)
                    );
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("java -jar Expekt-Multithreading.jar -n <n> -c <c> -t <t>");
        System.out.println("n: which fibonacci number to calculate");
        System.out.println("c: how many fibonacci calculations to run in total during the test");
        System.out.println("t: how many threads should be used to run the calculations");
    }

    private static void printResults(PerformanceTestResult result) {
        System.out.println("Results\n=======");
        System.out.println("Total time taken: " + result.getTotalTime());
        System.out.println("Min time: " + result.getMinTime());
        System.out.println("Max time: " + result.getMaxTime());
    }

}
