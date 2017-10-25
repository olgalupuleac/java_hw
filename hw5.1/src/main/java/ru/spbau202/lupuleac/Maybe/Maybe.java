package ru.spbau202.lupuleac.Maybe;


import com.sun.istack.internal.Nullable;

import java.util.function.Function;

/**
 * Generic class-wrapper for one element, which could contain Just element or Nothing.
 *
 * @param <T> is the type of the element
 */
public class Maybe<T> {
    private T value;
    private boolean nothing;

    public Maybe(@Nullable T t) {
        value = t;
    }

    public Maybe() {
        nothing = true;
    }

    /**
     * Returns new Maybe containing the given element with the same generic type.
     *
     * @param t   is given object
     * @param <T> is the type of the given element and the generic type of this member of the class.
     * @return new member of the class containing t as a value.
     */
    public static <T> Maybe<T> just(T t) {
        return new Maybe<>(t);
    }

    /**
     * Returns new Maybe without any object.
     *
     * @param <T> is the generic type of this member and the generic type of returning value
     * @return new Maybe containing Nothing.
     */
    public static <T> Maybe<T> nothing() {
        return new Maybe<>();
    }

    /**
     * Return the value relevant to this Maybe.
     *
     * @return the value relevant to this Maybe.
     * @throws AccessToNothingException if Maybe is Nothing.
     */
    public T get() throws AccessToNothingException {
        if (nothing) {
            throw new AccessToNothingException();
        }
        return value;
    }

    /**
     * Checks if Maybe contains an element.
     *
     * @return true if Maybe is not Nothing.
     */
    public boolean isPresent() {
        return !nothing;
    }

    /**
     * Applies the given function to the value relevant to this Maybe and returns Maybe containing the result.
     * If this Maybe is Nothing it returns Nothing.
     *
     * @param mapper is function to be applied.
     * @param <U>    is the type of the returned value of the given function.
     * @return Maybe containing the result of this function applied to the value
     * or Nothing if this Maybe is nothing.
     */
    public <U> Maybe<U> map(Function<? super T, ? extends U> mapper) {
        if (nothing) {
            return new Maybe<>();
        }
        return new Maybe<>(mapper.apply(value));
    }
}
