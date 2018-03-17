package ru.spbau202.lupuleac.ThreadPool;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spbau202.lupuleac.ThreadPool.LightFuture.LightExecutionException;
import ru.spbau202.lupuleac.ThreadPool.LightFuture.LightFuture;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An implementation of thread pool pattern.
 * Class which provides multithreaded task execution.
 * Number of working threads is set in the constructor.
 * Tasks could be added to the pool where they
 * will be distributed between working threads and executed.
 */
public class ThreadPoolImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolImpl.class);
    private final Queue<Task<?>> queue = new LinkedList<>();
    private Thread[] workers;
    private boolean isWorking = true;

    public ThreadPoolImpl(int numberOfThreads) {
        workers = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            workers[i] = new Thread(new Worker());
            workers[i].start();
        }
    }

    /**
     * Adds task to the pool and notifies a working thread waiting for
     * the task that queue of the task is not empty.
     *
     * @param supplier is an instance of Supplier with the task
     *                 (executing the task is calling supplier.get())
     * @param <T>      is a generic type of
     * @return an instance of LightFuture representing this task
     */
    @NotNull
    public <T> LightFuture<T> addTask(@NotNull Supplier<T> supplier) {
        Task<T> task = new Task<>(supplier);
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
        return task;
    }

    /**
     * Stops the working pool.
     * Notifies all threads waiting for the task and interrupts them.
     * After that all task in the queue that haven't been executed
     * are marked as executed with exception to avoid deadlock.
     */
    public void shutdown() {
        synchronized (queue) {
            LOGGER.debug("in shutdown");
            isWorking = false;
            queue.notifyAll();
        }
        for (Thread worker : workers) {
            worker.interrupt();
        }
        for (Task<?> task : queue) {
            task.markAsNotExecuted();
        }
    }

    /**
     * Class which implements LightFuture interface and represents task in the pool.
     *
     * @param <T> is a generic type of the task return value
     *            (is equal to the supplier generic type which is given in the constructor).
     */
    public class Task<T> implements LightFuture<T> {
        /**
         * Exception which may occur during the task execution.
         **/
        private LightExecutionException exception;
        private Supplier<T> supplier;
        private volatile boolean isCompleted = false;

        /**
         * Result of the computation.
         **/
        private T result;

        private Task(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        /**
         * Executes the task (computes the result by calling supplier.get()).
         * If the exception occurs during the computation,
         * it is transformed to the LightExecutionException to be thrown when method get() is called.
         *
         * @see LightExecutionException
         */
        private synchronized void execute() {
            try {
                result = supplier.get();
            } catch (Exception e) {
                if (e.getMessage() != null) {
                    exception = new LightExecutionException(e.getMessage());
                } else {
                    exception = new LightExecutionException();
                }
            }
            isCompleted = true;
            notifyAll();
        }

        /**
         * If the shutdown was called and task wasn't executed
         */
        private synchronized void markAsNotExecuted() {
            if (!isCompleted) {
                isCompleted = true;
                exception = new LightExecutionException(
                        "The thread pool was shut down before executing this task");
            }
            notifyAll();
        }

        @Override
        public boolean isReady() {
            return isCompleted;
        }

        @Override
        public T get() {
            if (!isCompleted) {
                synchronized (this) {
                    while (!isCompleted) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
            if (exception != null) {
                throw exception;
            }
            return result;
        }

        /**
         * Adds the resulting task to the pool.
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public <S> LightFuture<S> thenApply(@NotNull Function<? super T, S> function) {
            return ThreadPoolImpl.this.addTask(() -> function.apply(Task.this.get()));
        }
    }

    /**
     * Class representing the working thread in the pool.
     */
    private class Worker implements Runnable {
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                Task<?> task;
                synchronized (queue) {
                    while (queue.isEmpty() && ThreadPoolImpl.this.isWorking) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (!ThreadPoolImpl.this.isWorking) {
                        return;
                    }
                    task = queue.poll();
                    LOGGER.debug("thread {} got task", Thread.currentThread().getId());
                }
                if (task != null) {
                    task.execute();
                }
            }
        }
    }

}
