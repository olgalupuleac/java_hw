package ru.spbau202.lupuleac.Lazy.LazyFactory;

import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.Lazy.Lazy;
import ru.spbau202.lupuleac.Lazy.MultiThreadedLazy;
import ru.spbau202.lupuleac.Lazy.SingleThreadedLazy;

import java.util.function.Supplier;

/**
 * Creates the instances of the Lazy interface.
 */
public class LazyFactory {
    /**
     * Creates the instance of SingleThreadedLazy
     *
     * @param supplier is a supplier which provides a task
     * @param <T>      is a generic type of the supplier and of created instance
     * @return the instance of SingleThreadedLazy which represents the task provided by supplier
     * @see SingleThreadedLazy
     */
    @NotNull
    public static <T> Lazy<T> createSingleThreadedLazy(
            @NotNull Supplier<T> supplier) {
        return new SingleThreadedLazy<>(supplier);
    }

    /**
     * Creates the instance of MultiThreadedLazy
     *
     * @param supplier is a supplier which provides a task
     * @param <T>      is a generic type of the supplier and of created instance
     * @return the instance of MultiThreadedLazy which represents the task provided by supplier
     * @see MultiThreadedLazy
     */
    @NotNull
    public static <T> Lazy<T> createMultiThreadedLazy(
            @NotNull Supplier<T> supplier) {
        return new MultiThreadedLazy<>(supplier);
    }
}
