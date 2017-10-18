package ru.spbau202.lupuleac.BinarySearchTree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {
    @Test
    public void sizeOfEmpty() throws Exception {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        assertEquals(0, tree.size());
    }

    @Test
    public void size() throws Exception {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(6);
        assertEquals(1, tree.size());
    }

    @Test
    public void addEquals() throws Exception {
        BinarySearchTree<Character> tree = new BinarySearchTree<>();
        assertTrue(tree.add('a'));
        assertFalse(tree.add('a'));
        assertEquals(1, tree.size());
    }

    @Test
    public void addAlmostEqualDoubles() throws Exception {
        BinarySearchTree<Double> tree = new BinarySearchTree<>();
        tree.add(6.7);
        tree.add(6.7 + 1e-19);
        assertEquals(1, tree.size());
    }

    @Test
    public void contains() throws Exception {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.add("Yes");
        assertTrue(tree.contains("Yes"));
    }

    @Test
    public void notContains() throws Exception {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.add("Yes");
        assertFalse(tree.contains("yes"));
    }

    @Test
    public void add() throws Exception {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(0);
        assertTrue(tree.contains(0));
    }

    @Test
    public void addAscendingElements() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int i = 0; i < 100; i++) {
            tree.add(i);
        }
        assertEquals(100, tree.size());
        for (int i = 0; i < 100; i++) {
            assertTrue(tree.contains(i));
        }
    }

    @Test
    public void addRandomValues() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        HashSet<Integer> values = new HashSet<>();
        Random rand = new Random();
        for(int i = 0; i < 200; i++){
            Integer val = rand.nextInt();
            tree.add(val);
            values.add(val);
        }
        for(Integer val : values){
            assertTrue(tree.contains(val));
        }
    }

}