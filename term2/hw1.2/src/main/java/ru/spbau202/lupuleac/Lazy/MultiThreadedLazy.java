package ru.spbau202.lupuleac.Lazy;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Thread-safe implementation of Lazy interface.
 *
 * @param <T> is a generic type of the result of the execution.
 */
public class MultiThreadedLazy<T> implements Lazy<T> {
    private volatile T value;
    private volatile Supplier<T> supplier;

    public MultiThreadedLazy(@NotNull Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * {@inheritDoc}
     * If the computation is not completed it synchronizes and only one thread makes the computation.
     */
    @Override
    public T get() {
        if (supplier != null) {
            synchronized (this) {
                if (supplier != null) {
                    value = supplier.get();
                    supplier = null;
                }
            }
        }
        return value;
    }
}
