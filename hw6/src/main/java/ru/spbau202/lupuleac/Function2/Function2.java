package ru.spbau202.lupuleac.Function2;

import com.sun.istack.internal.NotNull;
import ru.spbau202.lupuleac.Function1.Function1;

/**
 * Interface which represents a function with two parameters.
 * Method apply indicates which function is represented and should be overridden.
 *
 * @param <ARG1> is a type of the first argument
 * @param <ARG2> is a type of the second argument
 * @param <R>    is a type of the returned value
 */
public interface Function2<ARG1, ARG2, R> {
    /**
     * Applies this function to the given arguments.
     *
     * @param arg1 is the first function argument
     * @param arg2 is the second function argument
     * @return the function result
     */
    R apply(ARG1 arg1, ARG2 arg2);

    /**
     * Binds the first argument of the function with the given value.
     * Returns function which takes one argument - the second argument of the original function,
     * which behaves as the original function which uses the given value as the first argument.
     *
     * @param arg1 is a value to be bound with the first argument
     * @return the function which takes one argument and behaves as the original function which uses
     * the given value as the first argument.
     */
    default Function1<ARG2, R> bind1(ARG1 arg1) {
        return t -> apply(arg1, t);
    }

    /**
     * Binds the second argument of the function with the given value.
     * Returns function which takes one argument - the first argument of the original function,
     * which behaves as the original function which uses the given value as the second argument.
     *
     * @param arg2 is a value to be bound with the second argument
     * @return the function which takes one argument and behaves as the original function which uses
     * the given value as the second argument.
     */
    @NotNull
    default Function1<ARG1, R> bind2(ARG2 arg2) {
        return t -> apply(t, arg2);
    }

    /**
     * Returns composition of given function with this function.
     *
     * @param g   is the given function which takes one argument
     * @param <T> is a type of the returned value of the given function
     * @return a composed function that first applies this function
     * and then applies the given function
     */
    @NotNull
    default <T> Function2<ARG1, ARG2, T> compose(@NotNull Function1<? super R, ? extends T> g) {
        return (x, y) -> g.apply(apply(x, y));
    }

    /**
     * Returns the function which takes one argument and returns the function which
     * also takes one argument. The type of the argument of the returned function is ARG1
     * and the type of the argument of the returned function of the returned function is ARG2.
     * If we apply the returned function to the argument a and when apply the result to the argument b,
     * the final result will be the same with the original function applied to a and b.
     *
     * @return this function curried
     */
    @NotNull
    default Function1<ARG1, Function1<ARG2, R>> curry() {
        return t -> bind1(t);
    }

}
