/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.expekt.threading;

/**
 *
 * @author malcolm
 */
public class FibCalcImpl implements FibCalc, Runnable {

    private StatisticsHolder statisticsHolder;
    private int n;

    FibCalcImpl(StatisticsHolder statisticsHolder, int n) {
        this.statisticsHolder = statisticsHolder;
        this.n = n;
    }

    public void run() {

        class StopWatch {
            private long start = System.currentTimeMillis();

            long stop() {
                return System.currentTimeMillis() - start;
            }
        }

        StopWatch stopWatch = new StopWatch();
        fib(n);
        statisticsHolder.updateStats(stopWatch.stop());
    }


    public long fib(int n) {
        if (0 >= n) throw new IllegalArgumentException("Minimum is 1");
        long a = 1, p = 0, tmp = 0;
        for (int i = 1; i < n; i++) {
            tmp = (a + p);
            p = a;
            a = tmp;
        }
        return a;
    }


}
