package ru.spbau202.lupuleac.Collections;

import org.junit.Test;
import ru.spbau202.lupuleac.Function1.Function1;
import ru.spbau202.lupuleac.Function2.Function2;
import ru.spbau202.lupuleac.Predicate.Predicate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CollectionsTest {
    @Test
    public void map() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(-1);
        List<Integer> result = Collections.map(new Negative(), list);
        List<Integer> expected = new ArrayList<>();
        expected.add(-8);
        expected.add(1);
        assertEquals(expected, result);
    }

    @Test
    public void filter() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i);
        }
        List<Integer> result = Collections.filter(new IsEven(), list);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            expected.add(2 * i);
        }
        assertEquals(expected, result);
    }

    @Test
    public void takeWhile() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(-2);
        list.add(7);
        List<Integer> result = Collections.takeWhile(new IsEven(), list);
        List<Integer> expected = new ArrayList<>();
        expected.add(6);
        expected.add(-2);
        assertEquals(expected, result);
    }

    @Test
    public void takeUntil() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(1);
        list.add(8);
        List<Integer> result = Collections.takeUntil(new IsEven(), list);
        List<Integer> expected = new ArrayList<>();
        expected.add(9);
        expected.add(1);
        assertEquals(expected, result);
    }

    @Test
    public void foldl() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(2);
        int result = Collections.foldl(new Power(), 2, list);
        assertEquals(64, result);
    }

    @Test
    public void foldr() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(2);
        int result = Collections.foldr(new Power(), 1, list);
        assertEquals(512, result);
    }

    public static class Negative implements Function1<Integer, Integer> {
        @Override
        public Integer apply(Integer x) {
            return -x;
        }
    }

    public static class IsEven implements Predicate<Integer> {
        @Override
        public Boolean apply(Integer x) {
            return x % 2 == 0;
        }
    }

    public static class Power implements Function2<Integer, Integer, Integer> {
        @Override
        public Integer apply(Integer a, Integer p) {
            int res = 1;
            for (int i = 0; i < p; i++) {
                res *= a;
            }
            return res;
        }

    }


}