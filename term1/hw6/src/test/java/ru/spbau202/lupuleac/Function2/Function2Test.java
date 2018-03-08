package ru.spbau202.lupuleac.Function2;

import org.junit.Test;
import ru.spbau202.lupuleac.Function1.Function1;
import ru.spbau202.lupuleac.Predicate.Predicate;

import java.util.Random;

import static org.junit.Assert.*;

public class Function2Test {
    @Test
    public void apply() throws Exception {
        Sum sum = new Sum();
        assertEquals(4, (int) sum.apply(1, 3));
    }

    @Test
    public void bind1() throws Exception {
        assertEquals(3, (int) (new Divide()).bind1(7).apply(2));
        assertEquals(7, (int) (new Divide()).bind1(7).apply(1));
    }

    @Test
    public void bind2() throws Exception {
        assertEquals(3, (int) (new Divide()).bind2(2).apply(7));
        assertEquals(7, (int) (new Divide()).bind2(1).apply(7));
    }

    @Test
    public void compose() throws Exception {
        assertEquals("2", (new Divide()).compose(
                new ToString()).apply(8, 4));
    }

    @Test
    public void composeWithPredicate() throws Exception {
        assertEquals(true, (new Divide()).compose(
                new IsPositive()).apply(8, 4));
    }

    @Test
    public void curry() throws Exception {
        assertEquals(2, (int) (new Divide()).curry().apply(4).apply(2));
    }

    @Test
    public void curryRandom() throws Exception {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int a = rand.nextInt();
            int b = rand.nextInt();
            assertEquals((new Sum()).apply(a, b), (new Sum()).curry().apply(a).apply(b));
        }
    }

    public static class Sum implements Function2<Integer, Integer, Integer> {
        @Override
        public Integer apply(Integer a, Integer b) {
            return a + b;
        }
    }

    public static class Divide implements Function2<Integer, Integer, Integer> {
        @Override
        public Integer apply(Integer a, Integer b) {
            return a / b;
        }
    }

    public static class IsPositive implements Predicate<Integer> {
        @Override
        public Boolean apply(Integer x) {
            return x > 0;
        }
    }

    public static class ToString implements Function1<Object, String> {
        @Override
        public String apply(Object o) {
            return o.toString();
        }
    }

}