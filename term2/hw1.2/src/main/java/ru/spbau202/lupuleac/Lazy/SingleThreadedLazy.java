package ru.spbau202.lupuleac.Lazy;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Implementation of the Lazy interface. Cannot be used in multithreaded program.
 *
 * @param <T> is a generic type of the result of the execution.
 */
public class SingleThreadedLazy<T> implements Lazy<T> {
    private T value;
    private Supplier<T> supplier;

    public SingleThreadedLazy(@NotNull Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * {@inheritDoc}
     * If the supplier is null it means that the supplier
     * was in the beginning or the computation was already made.
     * It returns the result in both cases (in second case it will return null).
     */
    @Override
    public T get() {
        if (supplier != null) {
            value = supplier.get();
            supplier = null;
        }
        return value;
    }
}
