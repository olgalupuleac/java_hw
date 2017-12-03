package ru.spbau202.lupuleac.Function1;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function1Test {
    @Test
    public void apply() throws Exception {

        assertEquals(25, (int) (new Square()).apply(5));
    }

    @Test
    public void compose() throws Exception {
        assertEquals(27, (int) (new Square()).compose(new AddTwo()).apply(5));

    }

    @Test
    public void applyDescendant() throws Exception {
        Integer i = 5;
        assertEquals(i.hashCode(), (int) (new HashCode()).apply(i));
    }

    @Test
    public void composeDescendant() throws Exception {
        Integer i = 25;
        assertEquals(i.hashCode(), (int) (new Square()).compose(
                new HashCode()).apply(5));

    }

    public static class Square implements Function1<Integer, Integer> {
        @Override
        public Integer apply(Integer x) {
            return x * x;
        }
    }

    public static class AddTwo implements Function1<Integer, Integer> {
        @Override
        public Integer apply(Integer x) {
            return x + 2;
        }
    }

    public static class HashCode implements Function1<Object, Integer> {
        @Override
        public Integer apply(Object o) {
            return o.hashCode();
        }
    }

}