package ru.spbau202.lupuleac.LinkedHashMap;


import java.util.Map;

/**
 * The CollisionList class is used in HashTable.
 * It consists of elements with same hashCode
 *
 */

public class List<K, V> {
    /**
     * Class Node represents an element of the list.
     * It contains key, value and links to next and previous nodes.
     * Class Node is used only inside CollisionList.
     */


    private Node head;
    private Node tail;

    /**
     * Method to get reference to the first node in the list.
     *
     * @return head of the list
     */
    public Node getHead() {
        return head;
    }

    /**
     * Changes value matches the specified key to the specified value
     * or appends the specified element (key and value) to the end of this list.
     *
     * @param key   key to be added
     * @param value value to be added
     * @return value which the key has before or null if this key was not in the list
     */
    public V add(K key, V value) {
        if (head == null) {
            head = new Node(key, value);
            tail = head;
            return null;
        }
        Node curNode = head;
        while (curNode != null) {
            if (curNode.getKey().equals(key)) {
                V existedValue = (V)curNode.getValue();
                curNode.setValue(value);
                return existedValue;
            }
            curNode = curNode.next;
        }
        Node newNode = new Node(key, value);
        if (tail != null) {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        return null;
    }

    /**
     * Gets value by the specified key
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is in the list,
     * or null if this list contains no matching value for the key.
     */
    public V get(K key) {
        Node curNode = head;
        while (curNode != null) {
            if (curNode.getKey().equals(key)) {
                return (V)curNode.getValue();
            }
            curNode = curNode.next;
        }
        return null;
    }

    /**
     * Removes element by the specified key from the list if present.
     *
     * @param key the key matches the element to be removed.
     * @return value the previous value associated with key, or null if there was no element with this key
     */
    public V remove(K key) {
        Node curNode = head;
        while (curNode != null) {
            if (curNode.getKey().equals(key)) {
                if (curNode.next != null)
                    curNode.next.prev = curNode.prev;
                else
                    tail = curNode.prev;
                if (curNode.prev != null)
                    curNode.prev.next = curNode.next;
                else
                    head = curNode.next;
                return (V)curNode.getValue();
            }
            curNode = curNode.next;
        }
        return null;
    }

}