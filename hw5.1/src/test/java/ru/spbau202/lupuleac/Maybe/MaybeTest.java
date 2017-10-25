package ru.spbau202.lupuleac.Maybe;

import org.junit.Test;

import static org.junit.Assert.*;

public class MaybeTest {
    @Test
    public void just() throws Exception {
        Maybe<String> maybe = Maybe.just("just");
        assertEquals("just", maybe.get());
    }

    @Test
    public void nothing() throws Exception {
        Maybe<String> maybe = Maybe.nothing();
        assertFalse(maybe.isPresent());
    }

    @Test
    public void get() throws Exception {
        Maybe<Integer> maybe = new Maybe<>(6);
        assertEquals(6, (int) maybe.get());
    }

    @Test
    public void isPresentTrue() throws Exception {
        Maybe<Integer> maybe = new Maybe<>(6);
        assertTrue(maybe.isPresent());
    }

    @Test
    public void isPresentFalse() throws Exception {
        Maybe<Integer> maybe = new Maybe<>();
        assertFalse(maybe.isPresent());
    }

    @Test
    public void mapJust() throws Exception {
        Maybe<Integer> maybe = new Maybe<>(7);
        Maybe<Integer> newMaybe = maybe.map(x -> 2 * x);
        assertEquals(14, (int) newMaybe.get());
    }

    @Test
    public void mapNothing() throws Exception {
        Maybe<Integer> maybe = new Maybe<>();
        Maybe<Integer> newMaybe = maybe.map(x -> 2 * x);
        assertFalse(newMaybe.isPresent());
    }

    @Test(expected = AccessToNothingException.class)
    public void getFromNothing() throws Exception {
        Maybe<Integer> maybe = new Maybe<>();
        maybe.get();
    }

}