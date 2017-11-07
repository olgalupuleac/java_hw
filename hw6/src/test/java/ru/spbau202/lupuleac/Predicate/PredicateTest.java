package ru.spbau202.lupuleac.Predicate;

import com.sun.istack.internal.NotNull;
import org.junit.Test;
import ru.spbau202.lupuleac.Collections.Collections;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class PredicateTest {
    @Test
    public void orTrue() throws Exception {
        assertTrue((new StartsWithA()).or(new IsAQuestion()).apply("Anna"));
    }

    @Test
    public void orFalse() throws Exception {
        assertFalse((new StartsWithA()).or(new IsAQuestion()).apply("Olga"));
    }

    @Test
    public void andTrue() throws Exception {
        assertTrue((new StartsWithA()).and(new IsAQuestion()).apply("Am I very wrong?"));
    }

    @Test
    public void andFalse() throws Exception {
        assertFalse((new IsAQuestion()).and(new StartsWithA()).apply("all in all"));
    }

    @Test
    public void not() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                assertFalse((new HasEvenSize<Integer>()).not().apply(list));
            } else {
                assertTrue((new HasEvenSize<Integer>()).not().apply(list));
            }
            list.add(i);

        }
    }

    public static class StartsWithA implements Predicate<String> {
        @Override
        public Boolean apply(String s) {
            return s.startsWith("a") || s.startsWith("A");
        }
    }

    public static class IsAQuestion implements Predicate<String> {
        @Override
        public Boolean apply(String s) {
            return s.endsWith("?");
        }
    }

    public static class HasEvenSize<T> implements Predicate<Collection<T>> {
        @Override
        public Boolean apply(Collection<T> collection) {
            return collection.size() % 2 == 0;
        }
    }

}