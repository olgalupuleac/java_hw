package ru.spbau202.lupuleac.ThreadPool.LightFuture;

import java.util.function.Function;

/**
 * Interface which represents the task to be executed.
 * @param <T> is a type of the computation result.
 */
public interface LightFuture<T> {
    /**
     * Checks if the task has been executed.
     * @return true if the task has been executed, false otherwise.
     */
    boolean isReady();

    /**
     * The result of the computation.
     * If the computation is not finished, waits till it ends.
     * @throws LightExecutionException if the exception occurs during the execution
     * @return the result of the computation.
     */
    T get();

    /**
     * Takes the argument of type Function which can be applied to the result of this future
     * and returns new LightFuture which applies the given function to the result of this future.
     * The returned future cannot be executed before this future.
     * @param function is a function to be applied
     * @param <S> is a generic type of the resulting future
     * @return the future which method get returns
     * application of the given function to the result of this future
     */
    <S> LightFuture<S> thenApply(Function<? super T, S> function);
}
