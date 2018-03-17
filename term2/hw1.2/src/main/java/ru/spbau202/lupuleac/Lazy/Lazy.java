package ru.spbau202.lupuleac.Lazy;

/**
 * Lazy interface represents the task which is executed only once.
 *
 * @param <T> is a generic type of the result of the execution.
 */
public interface Lazy<T> {
    /**
     * Returns the result of the computation.
     * If the computation was already made, it does not make it again.
     *
     * @return the result of the computation
     */
    T get();
}
