package ru.spbau202.lupuleac.Function1;

import com.sun.istack.internal.NotNull;

/**
 * Interface which represents a function with one parameter.
 * Method apply indicates which function is represented and should be overridden.
 *
 * @param <ARG> is a type of the argument
 * @param <R>   is a type of the returned value
 */
public interface Function1<ARG, R> {
    /**
     * Applies this function to the argument.
     *
     * @param arg is the function argument
     * @return the function result
     */

    R apply(ARG arg);

    /**
     * Returns composition of given function with this function.
     *
     * @param g   is function to be composed with this function
     * @param <T> is type of returned value of the given function
     * @return function which behaves as a composition of the given function
     * and this function
     */
    @NotNull
    default <T> Function1<ARG, T> compose(@NotNull Function1<? super R, T> g) {
        return t -> g.apply(apply(t));
    }
}
