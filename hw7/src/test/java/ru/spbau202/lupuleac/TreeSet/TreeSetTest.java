package ru.spbau202.lupuleac.TreeSet;

import org.junit.Test;
import ru.spbau202.MyTreeSet;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class TreeSetTest {

    @Test
    public void isEmpty() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
    }

    @Test
    public void contains() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(5);
        assertTrue(tree.contains(5));
        assertFalse(tree.contains(7));
    }

    @Test
    public void containsInTreeWithSeveralElements() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(5);
        tree.add(7);
        tree.add(3);
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(7));
        assertTrue(tree.contains(3));
    }

    @Test
    public void clear() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(6);
        tree.clear();
        assertFalse(tree.contains(6));
        assertTrue(tree.isEmpty());
    }

    @Test
    public void addToRight() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(5);
        tree.add(7);
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(7));
    }

    @Test
    public void addToLeft() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(5);
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(7));
    }

    @Test
    public void addEquals() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        assertTrue(tree.add(7));
        assertFalse(tree.add(7));
        assertEquals(1, tree.size());
        assertTrue(tree.contains(7));
    }

    @Test
    public void removeOnlyElement() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(6);
        assertTrue(tree.remove(6));
        assertEquals(0, tree.size());
        assertFalse(tree.contains(6));
    }

    @Test
    public void removeLeaf() throws Exception {
        TreeSet<String> tree = new TreeSet<>();
        tree.add("7");
        tree.add("67");
        assertTrue(tree.remove("67"));
        assertFalse(tree.contains("67"));
        assertTrue(tree.contains("7"));
        assertEquals(1, tree.size());
    }

    @Test
    public void removeRoot() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(6);
        tree.add(5);
        assertTrue(tree.remove(6));
        assertFalse(tree.contains(6));
        assertTrue(tree.contains(5));
        assertEquals(1, tree.size());
    }

    @Test
    public void removeElementWithNoRightTree() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(5);
        tree.add(4);
        tree.add(3);
        assertTrue(tree.remove(4));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(3));
        assertFalse(tree.contains(4));
        assertEquals(2, tree.size());
    }

    @Test
    public void removeFromTheCenter() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(8);
        tree.add(4);
        tree.add(12);
        tree.add(6);
        tree.add(2);
        tree.add(10);
        assertTrue(tree.remove(8));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(12));
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(6));
        assertFalse(tree.contains(8));
        assertEquals(5, tree.size());
    }

    @Test
    public void removeElementWhichIsNotPresent() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(5);
        assertFalse(tree.remove(6));
    }

    @Test
    public void size() throws Exception {
        TreeSet<String> tree = new TreeSet<>();
        tree.add("a");
        tree.add("b");
        assertEquals(2, tree.size());
    }

    @Test
    public void iterator() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(5);
        Iterator<Integer> iterator = tree.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(5, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void iteratorManyElements() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(5);
        tree.add(7);
        Iterator<Integer> iterator = tree.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(5, (int) iterator.next());
        assertEquals(7, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void iteratorGoingUp() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(5);
        Iterator<Integer> iterator = tree.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(5, (int) iterator.next());
        assertEquals(7, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void descendingIterator() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(5);
        Iterator<Integer> iterator = tree.descendingIterator();
        assertTrue(iterator.hasNext());
        assertEquals(5, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void descendingIteratorManyElements() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(5);
        Iterator<Integer> iterator = tree.descendingIterator();
        assertTrue(iterator.hasNext());
        assertEquals(7, (int) iterator.next());
        assertEquals(5, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void iterateOverBranchedTree() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(8);
        tree.add(4);
        tree.add(12);
        tree.add(6);
        tree.add(2);
        tree.add(10);
        Iterator<Integer> iterator = tree.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(2, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
        assertEquals(8, (int) iterator.next());
        assertEquals(10, (int) iterator.next());
        assertEquals(12, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void descendingIteratorOverBranchedTree() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(8);
        tree.add(4);
        tree.add(12);
        tree.add(6);
        tree.add(2);
        tree.add(10);
        Iterator<Integer> iterator = tree.descendingIterator();
        assertTrue(iterator.hasNext());
        assertEquals(12, (int) iterator.next());
        assertEquals(10, (int) iterator.next());
        assertEquals(8, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyTreeIterator() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.iterator().next();
    }

    @Test(expected = NoSuchElementException.class)
    public void lastIterator() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(5);
        Iterator<Integer> iterator = tree.iterator();
        iterator.next();
        iterator.next();
    }


    @Test
    public void descendingSet() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(8);
        tree.add(4);
        tree.add(12);
        tree.add(6);
        tree.add(2);
        tree.add(10);
        MyTreeSet<Integer> descendingSet = tree.descendingSet();
        Iterator<Integer> iterator = descendingSet.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(12, (int) iterator.next());
        assertEquals(10, (int) iterator.next());
        assertEquals(8, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void first() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(2, (int)tree.first());
    }

    @Test
    public void last() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(10, (int)tree.last());
    }

    @Test
    public void descendingFirst() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(10, (int)tree.descendingSet().first());
    }

    @Test
    public void descendingLast() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(2, (int)tree.descendingSet().last());
    }

    @Test
    public void lowerThanElementWhichIsPresent() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(2, (int)tree.lower(5));
    }

    @Test
    public void lowerThanElementWhichIsNotPresent() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(2, (int)tree.lower(3));
    }
    @Test
    public void descendingLower() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(2, (int)tree.descendingSet().lower(3));
    }

    @Test
    public void floorOfElementWhichIsPresent() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(5, (int)tree.floor(5));
    }

    @Test
    public void floorOfElementWhichIsNotPresent() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(2, (int)tree.floor(3));
    }
    @Test
    public void descendingFloor() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(2, (int)tree.descendingSet().floor(3));
    }

    @Test
    public void ceilingOfElementWhichIsPresent() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(5, (int)tree.ceiling(5));
    }

    @Test
    public void ceilingOfElementWhichIsNotPresent() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(5, (int)tree.ceiling(3));
    }
    @Test
    public void descendingCeiling() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(11);
        tree.add(5);
        tree.add(2);
        assertEquals(5, (int)tree.descendingSet().ceiling(3));
    }

    @Test
    public void higherThanElementWhichIsPresent() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(7, (int)tree.higher(5));
    }

    @Test
    public void higherThanElementWhichIsNotPresent() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(5, (int)tree.higher(3));
    }
    @Test
    public void higherCeiling() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(2);
        assertEquals(5, (int)tree.descendingSet().higher(3));
    }

    @Test
    public void comparator() throws Exception{
        TreeSet<Integer> tree = new TreeSet<>((x, y) -> y - x);
        tree.add(8);
        tree.add(4);
        tree.add(12);
        tree.add(6);
        tree.add(2);
        tree.add(10);
        Iterator<Integer> iterator = tree.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(12, (int) iterator.next());
        assertEquals(10, (int) iterator.next());
        assertEquals(8, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void methodsInEmptyTree() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        assertNull(tree.lower(0));
        assertNull(tree.higher(0));
        assertNull(tree.ceiling(0));
        assertNull(tree.floor(0));
    }

    @Test
    public void doubleDescendingSet() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(8);
        tree.add(4);
        tree.add(12);
        tree.add(6);
        tree.add(2);
        tree.add(10);
        MyTreeSet<Integer> doubleDescendingSet = tree.descendingSet().descendingSet();
        Iterator<Integer> iterator = doubleDescendingSet.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(2, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(6, (int) iterator.next());
        assertEquals(8, (int) iterator.next());
        assertEquals(10, (int) iterator.next());
        assertEquals(12, (int) iterator.next());
        assertFalse(iterator.hasNext());
    }

}