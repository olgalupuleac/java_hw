package ru.spbau202.lupuleac.Collections;

import com.sun.istack.internal.NotNull;
import ru.spbau202.lupuleac.Function1.Function1;
import ru.spbau202.lupuleac.Function2.Function2;
import ru.spbau202.lupuleac.Predicate.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implements some methods which could be applied to the collections.
 */
public class Collections {
    /**
     * Takes the function and the collection, applies function to all elements in the collection
     * and returns list with results.
     *
     * @param f          is the function to be applied
     * @param collection is the collection containing elements to which the given function will be applied
     * @param <E>        is a type of element in the collection
     * @param <R>        a type of a returned value of the function
     * @return list which contains the results of all applications
     */
    @NotNull
    public static <E, R> List<R> map(@NotNull Function1<? super E, R> f,
                                     @NotNull Collection<E> collection) {
        List<R> list = new ArrayList<>();
        for (E arg : collection) {
            list.add(f.apply(arg));
        }
        return list;
    }

    /**
     * Returns the list of elements from the collection for which the given predicate returns true.
     *
     * @param p          is the given predicate to filter elements
     * @param collection is the collection from which the elements are to be taken
     * @param <E>        is a type of elements in the collection
     * @return a list containing only elements for which the given predicate returned true
     */
    @NotNull
    public static <E> List<E> filter(@NotNull Predicate<? super E> p,
                                     @NotNull Collection<E> collection) {
        List<E> list = new ArrayList<>();
        for (E t : collection) {
            if (p.apply(t)) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * Takes elements from the collection while the given predicate applied to them returns true.
     * Returns the list of the taken elements.
     *
     * @param p          is the given predicate which indicates the end of the list if the application returned false
     * @param collection is the collection from which the elements are to be taken
     * @param <E>        is a type of elements in the collection
     * @return the list containing elements from the beginning of the collection for which the predicate returned true
     */
    @NotNull
    public static <E> List<E> takeWhile(@NotNull Predicate<? super E> p,
                                        @NotNull Collection<E> collection) {
        List<E> list = new ArrayList<>();
        for (E t : collection) {
            if (!p.apply(t)) {
                break;
            }
            list.add(t);
        }
        return list;
    }

    /**
     * Takes elements from the collection until the given predicate applied to them returns true.
     * Returns the list of the taken elements.
     *
     * @param p          is the given predicate which indicates the end of the list if the application returned true
     * @param collection is the collection from which the elements are to be taken
     * @param <E>        is a type of elements in the collection
     * @return the list containing elements from the beginning of the collection for which the predicate returned false
     */
    @NotNull
    public static <E> List<E> takeUntil(@NotNull Predicate<? super E> p,
                                        @NotNull Collection<E> collection) {
        List<E> list = new ArrayList<>();
        for (E t : collection) {
            if (p.apply(t)) {
                break;
            }
            list.add(t);
        }
        return list;
    }

    /**
     * Applied to a function with two arguments, a starting value
     * and a collection of elements, reduces the collection using the function from left to right.
     * First it is applied to the starting value and the first element of the collection, after that it is applied to
     * the result of the previous application and the next element in the collection.
     *
     * @param f          is function to be applied
     * @param a          is starting value
     * @param collection is collection which is to be reduced
     * @param <T>        is a type of elements in the collection
     * @return a final result of all applications
     */
    @NotNull
    public static <T> T foldl(@NotNull Function2<? super T, ? super T, ? extends T> f, @NotNull T a,
                              @NotNull Collection<T> collection) {
        for (T t : collection) {
            a = f.apply(a, t);
        }
        return a;
    }

    /**
     * Applied to a function with two arguments, a starting value
     * and a collection of elements, reduces the collection using the function from right to left.
     * First it is applied to the last element of the collection and to the starting value, after that it is applied
     * the next element in the collection and to the result of the previous application.
     *
     * @param f          is function to be applied
     * @param a          is starting value
     * @param collection is collection which is to be reduced
     * @param <T>        is a type of elements in the collection
     * @return a final result of all applications
     */
    @NotNull
    public static <T> T foldr(@NotNull Function2<? super T, ? super T, ? extends T> f, @NotNull T a,
                              @NotNull Collection<T> collection) {
        ArrayList<T> list = new ArrayList<>();
        list.addAll(collection);
        for (int i = list.size() - 1; i >= 0; i--) {
            a = f.apply(list.get(i), a);
        }
        return a;
    }

}
