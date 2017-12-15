package ru.spbau202.lupuleac.Stack;

import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class StackTest {
    @Test
    public void size() throws Exception {
        Stack<Integer> stack = new Stack<>();
        assertEquals(0, stack.size());
        stack.push(1);
        assertEquals(1, stack.size());
    }

    @Test
    public void isEmpty() throws Exception {
        Stack<Integer> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void push() throws Exception {
        Stack<Character> stack = new Stack<>();
        stack.push('a');
        assertEquals('a', (char) stack.top());
    }

    @Test
    public void capacity() throws Exception {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 42; i++) {
            stack.push(i);
        }
        for (int i = 41; i >= 0; i--) {
            assertEquals(i, (int) stack.pop());
        }
    }

    @Test
    public void top() throws Exception {
        Stack<Character> stack = new Stack<>();
        stack.push('(');
        assertEquals('(', (char) stack.top());
    }

    @Test
    public void pop() throws Exception {
        Stack<Character> stack = new Stack<>();
        stack.push('r');
        assertEquals('r', (char) stack.pop());
    }

    @Test
    public void clear() throws Exception {
        Stack<Character> stack = new Stack<>();
        stack.push('r');
        stack.clear();
        assertTrue(stack.isEmpty());
    }

    @Test(expected = EmptyStackException.class)
    public void popEmptyStack() throws Exception {
        Stack<Character> stack = new Stack<>();
        stack.pop();
    }

}