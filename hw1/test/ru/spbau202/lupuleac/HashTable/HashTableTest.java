package ru.spbau202.lupuleac.HashTable;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for class HashTable
 */
public class HashTableTest {
    /**
     * Tests size method of the HashTable if the table is empty.
     *
     * @throws Exception
     * @see HashTable#size()
     */
    @Test
    public void sizeEmpty() throws Exception {
        HashTable hashTable = new HashTable();
        assertEquals(0, hashTable.size());
    }

    /**
     * Tests size method of the HashTable if it contains several elements.
     *
     * @throws Exception
     * @see HashTable#size()
     */
    public void size() throws Exception {
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        hashTable.put("key2", "value2");
        assertEquals(2, hashTable.size());
    }

    /**
     * Tests contains method of the HashTable if it contains certain element.
     *
     * @throws Exception
     * @see HashTable#contains
     */
    @Test
    public void contains() throws Exception {
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        assertEquals(true, hashTable.contains("key"));
    }

    /**
     * Tests contains method of the HashTable if it does not contain a certain element.
     *
     * @throws Exception
     * @see HashTable#contains
     */
    @Test
    public void notContains() throws Exception {
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        assertEquals(false, hashTable.contains("key2"));
    }

    /**
     * Tests get method of the HashTable if there is a mapping for a certain key.
     *
     * @throws Exception
     * @see HashTable#get
     */
    @Test
    public void get() throws Exception {
        HashTable hashTable = new HashTable();
        hashTable.put("some_key", "some_value");
        assertEquals("some_value", hashTable.get("some_key"));

    }

    /**
     * Tests get method of the HashTable if there is no mapping for a certain key.
     *
     * @throws Exception
     * @see HashTable#get
     */
    @Test
    public void getNull() throws Exception {
        HashTable hashTable = new HashTable();
        hashTable.put("some_key", "some_value");
        assertEquals(null, hashTable.get("key"));

    }

    /**
     * Tests put method of the HashTable.
     *
     * @throws Exception
     * @see HashTable#put
     */
    @Test
    public void put() throws Exception {
        HashTable hashTable = new HashTable();
        hashTable.put("some_key", "value");
        assertEquals("value", hashTable.put("some_key", "other_value"));
    }

    /**
     * Tests remove method of the HashTable.
     *
     * @throws Exception
     * @see HashTable#remove
     */
    @Test
    public void remove() throws Exception {
        HashTable hashTable = new HashTable();
        hashTable.put("some_key", "value");
        assertEquals("value", hashTable.remove("some_key"));
    }

    /**
     * Tests clear method of the HashTable.
     *
     * @throws Exception
     * @see HashTable#clear
     */
    @Test
    public void clear() throws Exception {
        HashTable hashTable = new HashTable();
        hashTable.put("some_key", "value");
        hashTable.clear();
        assertEquals(0, hashTable.size());
    }

    /**
     * Appends to the hash table more elements than initial capacity and tests if the rebuild method does not change
     * the hash table content.
     *
     * @throws Exception
     * @see HashTable#clear
     */
    @Test
    public void rebuild() throws Exception {
        HashTable hashTable = new HashTable();
        for (int i = 0; i < 2000; i++) {
            Integer keyAndVal = i;
            hashTable.put(keyAndVal.toString(), keyAndVal.toString());
        }
        for (int i = 0; i < 2000; i++) {
            Integer keyAndVal = i;
            assertEquals(keyAndVal.toString(), hashTable.get(keyAndVal.toString()));
        }
    }

    /**
     * Appends to the hash table two elements with same hash to test the collision.
     *
     * @throws Exception
     */
    @Test
    public void collision() throws Exception {
        HashTable hashTable = new HashTable();
        Integer x = 0;
        String firstString = x.toString();
        String secondString = null;
        int hash = (firstString.hashCode() % 1000 + 1000) % 1000;
        for (int i = 1; ; i++) {
            Integer stringGenerator = i;
            int anotherHash = (stringGenerator.toString().hashCode() + 1000) % 1000;
            if (hash == anotherHash) {
                secondString = stringGenerator.toString();
                hashTable.put(firstString, "1");
                hashTable.put(secondString, "2");
                break;
            }
        }
        assertEquals("1", hashTable.get(firstString));
        assertEquals("2", hashTable.get(secondString));
    }

    /**
     * Tests the hash table if the given key has negative hashCode.
     *
     * @throws Exception
     */
    @Test
    public void negativeHashCode() throws Exception {
        String string = null;
        HashTable hashTable = new HashTable();
        for (int i = 1; ; i++) {
            Integer stringGenerator = i;
            string = stringGenerator.toString();
            if (string.hashCode() < 0) {
                hashTable.put(string, "value");
                break;
            }
        }
        assertEquals("value", hashTable.get(string));
    }

}