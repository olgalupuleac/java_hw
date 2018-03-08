package ru.spbau202.lupuleac.HashTable;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for class CollisionList
 */
public class CollisionListTest {
    /**
     * Tests getHead method of the CollisionList.
     *
     * @throws Exception
     */
    @Test
    public void getHead() throws Exception {
        CollisionList list = new CollisionList();
        list.add("key", "value");
        assertEquals("key", list.getHead().getKey());
    }

    /**
     * Tests an iteration through the CollisionList.
     *
     * @throws Exception
     */
    @Test
    public void getHeadNext() throws Exception {
        CollisionList list = new CollisionList();
        list.add("key", "value");
        list.add("key2", "value2");
        assertEquals("value2", list.getHead().getNext().getValue());
    }

    /**
     * Tests get method of the CollisionList
     * using method add.
     *
     * @throws Exception
     * @see CollisionList#get
     * @see CollisionList#add
     */
    @Test
    public void get() throws Exception {
        CollisionList list = new CollisionList();
        list.add("key", "value");
        assertEquals("value", list.get("key"));
    }

    /**
     * Tests add method of the CollisionList
     *
     * @throws Exception
     * @see CollisionList#add
     */
    @Test
    public void add() throws Exception {
        CollisionList list = new CollisionList();
        assertEquals(null, list.add("key", "value"));
        assertEquals("value", list.add("key", "value2"));
    }

    /**
     * Tests remove method of the CollisionList
     * using method add
     *
     * @throws Exception
     * @see CollisionList#remove
     * @see CollisionList#add
     */
    @Test
    public void remove() throws Exception {
        CollisionList list = new CollisionList();
        list.add("key", "value");
        assertEquals("value", list.remove("key"));
        assertEquals(null, list.remove("key"));
    }

}