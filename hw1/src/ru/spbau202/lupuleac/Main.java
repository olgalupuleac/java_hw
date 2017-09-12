package ru.spbau202.lupuleac;
import ru.spbau202.lupuleac.HashTable;

/**
 * Tests HashTable and CollisionList classes.
 * @see HashTable
 * @see CollisionList
 * @author Olga Lupuleac
 */

public class Main {

    /**
     * Tests HashTable.put(key, value) method if there was no mapping for the key.
     */
    public static void testAppend(){
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        assert hashTable.get("key").equals("value");
    }

    /**
     * Tests HashTable.remove(key, value) method if the key is present and otherwise.
     */
    public static void testRemove(){
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        assert hashTable.remove("key").equals("value");
        assert hashTable.remove("other") == null;
    }

    /**
     * Tests HashTable.size().
     */
    public static void testSize(){
        HashTable hashTable = new HashTable();
        hashTable.put("key1", "value1");
        hashTable.put("key2", "value2");
        hashTable.put("key3", "value3");
        assert hashTable.size() == 3;
    }

    /**
     * Tests HashTable.put(key, value) if the key is present.
     */
    public static void testChange(){
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        assert hashTable.put("key", "other_value").equals("value");
        assert hashTable.get("key").equals("other_value");
    }

    /**
     * Tests HashTable.contains(key)
     */
    public static void testContains(){
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        assert hashTable.contains("key") && !hashTable.contains("other_key");
    }

    /**
     * Tests HashTable.clear()
     */
    public static void testClear(){
        HashTable hashTable = new HashTable();
        hashTable.put("key", "value");
        hashTable.clear();
        assert !hashTable.contains("key");
        assert hashTable.size() == 0;
    }

    /**
     * Tests CollisionList (all methods).
     */
    public static void testList(){
        CollisionList list = new CollisionList();
        list.add("key1", "value1");
        list.add("key2", "value2");
        CollisionList.Node cur = list.getHead();
        assert cur.getKey().equals("value1");
        assert cur.getNext().getKey().equals("value2");
        assert list.contains("key1") && list.contains("key2");
        assert list.remove("key1").equals("value1");
        assert !list.contains("key1") && list.contains("key2");
    }


    public static void main(String[] args) {
        testAppend();
        testRemove();
        testSize();
        testChange();
        testContains();
        testClear();
        testList();



    }
}
