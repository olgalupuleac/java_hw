package ru.spbau202.lupuleac.LinkedHashMap;

import java.util.*;


public class LinkedHashMap<K, V> extends AbstractMap<K, V> {

    private int size;
    private static final int INITIAL_CAPACITY = (int) 1e6 + 7;
    private Object[] array = new Object[INITIAL_CAPACITY];
    private K[] keys = (K[]) new Object[INITIAL_CAPACITY];
    private int hash(Object key) {
        return (key.hashCode() % array.length + array.length) % array.length;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size != 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) == null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        int arrayIndex = hash(key);
        if (array[arrayIndex] == null)
            return null;
        return ((List<K, V>)array[arrayIndex]).get((K)key);
    }

    @Override
    public V put(K key, V value) {
        int arrayIndex = hash(key);
        if(array[hash(key)] == null){
            array[hash(key)] = new List<K, V>();
        }
        V existedValue = ((List<K, V>)array[arrayIndex]).add(key, value);
        if (existedValue == null)
            size++;
        return existedValue;
    }

    @Override
    public V remove(Object key) {
        int arrayIndex = hash(key);
        if (array[arrayIndex] == null)
            return null;
        V value = ((List<K, V>)array[arrayIndex]).remove((K)key);
        if (value != null)
            size--;
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        size = 0;
        array = new Object[INITIAL_CAPACITY];
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    public class MySet implements Set<Entry<K, V>>{

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Entry<K, V> kvEntry) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Entry<K, V>> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        public class MyIterator<ListNode<K, V>> implements Iterator {

            protected ListNode<K, V> curNode;

            @Override
            public boolean hasNext() {
                return curNode.next() != null;
            }

            @Override
            public Object next() {
                return curNode.next();
            }
        }
    }
}
