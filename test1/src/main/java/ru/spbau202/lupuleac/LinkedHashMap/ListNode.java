package ru.spbau202.lupuleac.LinkedHashMap;

import java.util.Map;

public class ListNode<K, V> implements Map.Entry<K, V> {
    public ListNode next;
    public ListNode prev;
    private K key;
    private V value;

    public ListNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * The method allows iterating
     * through CollisionList.
     *
     * @return link to next Node
     */
    public ListNode getNext() {
        return next;
    }

    /**
     * Returns key for this node.
     *
     * @return key for this node
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns key for this node.
     *
     * @return value for this node
     */
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V existedValue = this.value;
        this.value = value;
        return existedValue;
    }
}
