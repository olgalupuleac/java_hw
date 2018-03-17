package ru.spbau202.lupuleac.ThreadPool;

import org.junit.Test;
import ru.spbau202.lupuleac.ThreadPool.LightFuture.LightExecutionException;
import ru.spbau202.lupuleac.ThreadPool.LightFuture.LightFuture;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ThreadPoolImplTest {
    @Test
    public void simple() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(2);
        LightFuture<?> task1 = threadPool.addTask((
                Supplier<Void>) () -> {
            System.out.println(
                    Thread.currentThread().getId());
            return null;
        });
        LightFuture<?> task2 = threadPool.addTask((
                Supplier<Void>) () -> {
            System.out.println(
                    Thread.currentThread().getId());
            return null;
        });
        assertEquals(null, task2.get());
        assertEquals(null, task1.get());
        assertTrue(task1.isReady());
        assertTrue(task2.isReady());
    }

    //test if no exception occurs
    @Test
    public void shutdown() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(4);
        for (int i = 0; i < 100; i++) {
            threadPool.addTask((
                    Supplier<Void>) () -> {
                System.out.println(
                        Thread.currentThread().getId());
                return null;
            });
        }
        threadPool.shutdown();
    }

    @Test(expected = LightExecutionException.class)
    public void shutDownNotAllTasksAreReady() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(2);
        LightFuture<Long> task = null;
        for (int i = 0; i < 100; i++) {
            task = threadPool.addTask(() -> factorial(30000));
        }
        threadPool.shutdown();
        task.get();
    }

    @Test
    public void threadsAreWorking() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(4);
        ArrayList<LightFuture<Void>> tasks = new ArrayList<>();
        MyCountDownLatch countDownLatch = new MyCountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            tasks.add(threadPool.addTask(() -> {
                countDownLatch.countDown();
                return null;
            }));
        }
        countDownLatch.await();
        for (LightFuture<Void> task : tasks) {
            task.get();
        }
        for (LightFuture<Void> task : tasks) {
            assertTrue(task.isReady());
        }
        assertEquals(0, countDownLatch.getCount());
    }

    /**
     * Test if the tasks are executed one by one.
     * If the fifth task is taken before the fourth,
     * the process  will never stop.
     */
    @Test
    public void tasksAreExecutedOneByOne() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(4);
        ArrayList<LightFuture<Void>> tasks = new ArrayList<>();
        MyCountDownLatch countDownLatch = new MyCountDownLatch(1);
        for (int i = 0; i < 3; i++) {
            tasks.add(threadPool.addTask(() -> {
                countDownLatch.await();
                return null;
            }));
        }
        tasks.add(threadPool.addTask(() -> {
            countDownLatch.countDown();
            return null;
        }));
        tasks.add(threadPool.addTask(() -> {
            countDownLatch.await();
            return null;
        }));
        countDownLatch.await();
        for (LightFuture<Void> task : tasks) {
            task.get();
        }
        for (LightFuture<Void> task : tasks) {
            assertTrue(task.isReady());
        }
        assertEquals(0, countDownLatch.getCount());
    }

    @Test
    public void allThreadsAreWorking() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(4);
        ArrayList<LightFuture<Void>> tasks = new ArrayList<>();
        MyCountDownLatch countDownLatch = new MyCountDownLatch(1);
        for (int i = 0; i < 3; i++) {
            tasks.add(threadPool.addTask(() -> {
                countDownLatch.await();
                return null;
            }));
        }
        tasks.add(threadPool.addTask(() -> {
            countDownLatch.countDown();
            return null;
        }));
        countDownLatch.await();
        for (LightFuture<Void> task : tasks) {
            task.get();
        }
        for (LightFuture<Void> task : tasks) {
            assertTrue(task.isReady());
        }
    }

    @Test
    public void taskNotReady() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(4);
        ArrayList<LightFuture<Void>> tasks = new ArrayList<>();
        MyCountDownLatch countDownLatch = new MyCountDownLatch(1);
        for (int i = 0; i < 4; i++) {
            tasks.add(threadPool.addTask(() -> {
                countDownLatch.await();
                return null;
            }));
        }
        for (int i = 0; i < 4; i++) {
            assertFalse(tasks.get(i).isReady());
        }
        countDownLatch.countDown();
    }

    @Test
    public void get() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(2);
        LightFuture<Integer> task1 = threadPool.addTask(() -> 1);
        LightFuture<Integer> task2 = threadPool.addTask(() -> 2);
        assertEquals(1, (int) task1.get());
        assertEquals(2, (int) task2.get());
    }

    @Test
    public void getLongComputation() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(2);
        long expected = factorial(1000);
        LightFuture<Long> task1 = threadPool.addTask(() -> factorial(1000));
        LightFuture<Long> task2 = threadPool.addTask(() -> factorial(1000));
        assertEquals(expected, (long) task1.get());
        assertEquals(expected, (long) task2.get());
    }

    @Test
    public void thenApplyWaitingForFirstTask() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(2);
        MyCountDownLatch countDownLatch = new MyCountDownLatch(1);
        LightFuture<Void> task1 = threadPool.addTask(() -> {
            countDownLatch.await();
            return null;
        });
        LightFuture<Void> task2 = task1.thenApply(x -> null);
        assertFalse(task2.isReady());
        countDownLatch.countDown();
        assertEquals(null, task1.get());
        assertEquals(null, task2.get());
    }

    @Test
    public void thenApplySum() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(6);
        LightFuture<Integer> task = threadPool.addTask(() -> 0);
        for (int i = 0; i < 5; i++) {
            int j = i;
            task = task.thenApply(x -> x + j);
        }
        assertEquals(10, (int) task.get());
    }

    @Test
    public void thenApplyDifferentGenericTypes() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(2);
        LightFuture<Integer> task = threadPool.addTask(() -> 0);
        LightFuture<String> task2 = task.thenApply(Object::toString);
        assertEquals("0", task2.get());
    }

    @Test(expected = LightExecutionException.class)
    public void lightExecutionException() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(2);
        LightFuture<Integer> task = threadPool.addTask(() -> null);
        LightFuture<String> task2 = task.thenApply(Integer::toBinaryString);
        task2.get();
    }

    @Test
    public void singleThread() {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(1);
        LightFuture<Integer> task = null;
        for (int i = 0; i < 5; i++) {
            int j = i;
            task = threadPool.addTask(() -> j);
        }
        assertEquals(4, (int) task.get());
    }

    @Test
    public void randomTest() {
        Random rand = new Random();
        ThreadPoolImpl pool = new ThreadPoolImpl(rand.nextInt(1000) + 1);
        int numberOfTasks = rand.nextInt(2000 + 1);
        LightFuture<Void> task = null;
        for (int j = 0; j < numberOfTasks; j++) {
            task = pool.addTask(() -> null);
        }
        assertFalse(task == null);
        assertEquals(null, task.get());
    }

    /**
     * {@link CountDownLatch}
     **/
    private static class MyCountDownLatch {
        private volatile int cnt;

        private MyCountDownLatch(int n) {
            cnt = n;
        }

        /**
         * {@link CountDownLatch#countDown()}
         **/
        private synchronized void countDown() {
            cnt--;
            notifyAll();
        }

        /**
         * {@link CountDownLatch#await()}
         **/
        private synchronized void await() {
            while (cnt != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        /**
         * {@link CountDownLatch#getCount()}
         **/
        private synchronized int getCount() {
            return cnt;
        }

    }

    //I don't care for overflow
    private static Long factorial(long n) {
        long res = 1;
        for (long i = 1; i <= n; i++) {
            res *= i;
        }
        return res;
    }
}