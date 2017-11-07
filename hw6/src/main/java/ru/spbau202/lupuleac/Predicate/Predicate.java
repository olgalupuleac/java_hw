package ru.spbau202.lupuleac.Predicate;

import com.sun.istack.internal.NotNull;
import ru.spbau202.lupuleac.Function1.Function1;

/**
 * @param <T>
 */
public interface Predicate<T> extends Function1<T, Boolean> {
    /**
     * Predicate which returns true regardless of its argument.
     */
    Predicate<Object> ALWAYS_TRUE = o -> true;

    /**
     * Predicate which returns false regardless of its argument.
     */
    Predicate<Object> ALWAYS_FALSE = o -> false;

    /**
     * Returns predicate which result is true if either of this and given predicates is true.
     *
     * @param p is the given predicate
     * @return predicate which is true if either of this and given predicates is true.
     */
    @NotNull
    default Predicate<T> or(@NotNull Predicate<? super T> p) {
        return t -> apply(t) || p.apply(t);
    }

    /**
     * Returns predicate which is true if both of this and given predicates are true.
     *
     * @param p is the given predicate
     * @return predicate which is true if both of this and given predicates are true
     */
    @NotNull
    default Predicate<T> and(@NotNull Predicate<? super T> p) {
        return t -> apply(t) && p.apply(t);
    }

    /**
     * Returns predicate which result is opposite to this predicate.
     *
     * @return predicate which result is true if this predicate's result is false
     */
    @NotNull
    default Predicate<T> not() {
        return t -> !apply(t);
    }

}
