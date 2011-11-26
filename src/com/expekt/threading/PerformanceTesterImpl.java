/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.expekt.threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author malcolm
 */
public class PerformanceTesterImpl implements PerformanceTester, StatisticsHolder {

    private long totalTime;
    private long minTime;
    private long maxTime;

    public synchronized void updateStats(long timeTaken) {
        this.totalTime += timeTaken;
        this.minTime = minTime == 0 || timeTaken < minTime ? timeTaken : minTime;
        this.maxTime = timeTaken > maxTime ? timeTaken : maxTime;
    }

    private ThreadPoolExecutor createThreadPoolExecuter(int executionCount, int threadPoolSize) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(executionCount);
        return new ThreadPoolExecutor(threadPoolSize, threadPoolSize, 0, TimeUnit.DAYS, queue);
    }

    public PerformanceTestResult runPerformanceTest(Runnable task, int executionCount, int threadPoolSize) throws InterruptedException {
        ThreadPoolExecutor e = createThreadPoolExecuter(executionCount, threadPoolSize);
        e.prestartAllCoreThreads();
        for (int i = 1; i <= executionCount; i++)
            e.execute(task);
        e.shutdown();
        if (!e.awaitTermination(5, TimeUnit.MINUTES)) throw new IllegalStateException("Timeout elapsed before termination");
        return new PerformanceTestResult(totalTime, minTime, maxTime);
    }


}
