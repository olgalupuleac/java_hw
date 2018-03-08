package ru.spbau202.lupuleac.SmartList;

import static org.junit.Assert.*;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;



public class SmartListTest {

    @Test
    public void testSimple() {
        List<Integer> list = newList();

        assertEquals(Collections.<Integer>emptyList(), list);

        list.add(1);
        assertEquals(Collections.singletonList(1), list);

        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);
    }

    @Test
    public void testGetSet() {
        List<Object> list = newList();

        list.add(1);

        assertEquals(1, list.get(0));
        assertEquals(1, list.set(0, 2));
        assertEquals(2, list.get(0));
        assertEquals(2, list.set(0, 1));

        list.add(2);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));

        assertEquals(1, list.set(0, 2));

        assertEquals(Arrays.asList(2, 2), list);
    }

    @Test
    public void testRemove() throws Exception {
        List<Object> list = newList();

        list.add(1);
        list.remove(0);
        assertEquals(Collections.emptyList(), list);

        list.add(2);
        list.remove((Object) 2);
        assertEquals(Collections.emptyList(), list);

        list.add(1);
        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);

        list.remove(0);
        assertEquals(Collections.singletonList(2), list);

        list.remove(0);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void testIteratorRemove() throws Exception {
        List<Object> list = newList();
        assertFalse(list.iterator().hasNext());

        list.add(1);

        Iterator<Object> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);

        list.addAll(Arrays.asList(1, 2));

        iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertTrue(iterator.hasNext());
        assertEquals(Collections.singletonList(2), list);
        assertEquals(2, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);
    }


    @Test
    public void testCollectionConstructor() throws Exception {
        assertEquals(Collections.emptyList(), newList(Collections.emptyList()));
        assertEquals(
                Collections.singletonList(1),
                newList(Collections.singletonList(1)));

        assertEquals(
                Arrays.asList(1, 2),
                newList(Arrays.asList(1, 2)));
    }

    @Test
    public void testAddManyElementsThenRemove() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 7; i++) {
            list.add(i + 1);
        }

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), list);

        for (int i = 0; i < 7; i++) {
            list.remove(list.size() - 1);
            assertEquals(6 - i, list.size());
        }

        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void containsWithManyElements() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 7; i++) {
            list.add(i + 1);
        }
        assertTrue(list.contains(5));
        assertFalse(list.contains(0));
    }

    @Test
    public void containsWithFewElements() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 3; i++) {
            list.add(i + 1);
        }
        assertTrue(list.contains(2));
        assertFalse(list.contains(0));
    }

    @Test
    public void containsWithOneElement() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 1; i++) {
            list.add(i + 1);
        }
        assertTrue(list.contains(1));
        assertFalse(list.contains(0));
    }

    @Test
    public void rangeBasedForWithManyElements() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 7; i++) {
            list.add(i + 1);
        }
        Integer cnt = 1;
        for(Object i : list){
            assertEquals(cnt++, i);
        }
    }

    @Test
    public void rangeBasedForWithFewElements() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
        Integer cnt = 0;
        for(Object i : list){
            assertEquals(cnt++, i);
        }
    }

    @Test
    public void iteratorFewElements() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
        Iterator iterator = list.iterator();
        Integer cnt = 0;
        while(iterator.hasNext()){
            assertEquals(cnt++, iterator.next());
        }
    }

    @Test
    public void iteratorManyElements() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        Iterator iterator = list.iterator();
        Integer cnt = 0;
        while(iterator.hasNext()){
            assertEquals(cnt++, iterator.next());
        }
    }

    @Test
    public void iteratorOneElement() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 1; i++) {
            list.add(i);
        }
        Iterator iterator = list.iterator();
        Integer cnt = 0;
        while(iterator.hasNext()){
            assertEquals(cnt++, iterator.next());
        }
    }

    @Test
    public void size(){
        List<Object> list = newList();
        for (int i = 0; i < 1; i++) {
            assertEquals(i, list.size());
            list.add(i);
        }
    }

    @Test
    public void clear() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 1; i++) {
            list.add(i);
        }
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void isEmpty() throws Exception {
        List<Object> list = newList();
        assertTrue(list.isEmpty());
        list.add(7);
        assertFalse(list.isEmpty());
    }

    @Test
    public void toArray() throws Exception {
        Object[] expected = new Object[3];
        for(int i = 0; i < 3; i++){
            expected[i] = i - 1;
        }
        List<Object> list = newList(Arrays.asList(expected));
        assertArrayEquals(expected, list.toArray());
    }

    private static <T> List<T> newList() {
        try {
            return (List<T>) getListClass().getConstructor().newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> newList(Collection<T> collection) {
        try {
            return (List<T>) getListClass().getConstructor(Collection.class).newInstance(collection);
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getListClass() throws ClassNotFoundException {
        return Class.forName("ru.spbau202.lupuleac.SmartList.SmartList");
    }
}