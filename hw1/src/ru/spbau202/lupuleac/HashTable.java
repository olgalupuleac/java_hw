package ru.spbau202.lupuleac;

/**
 * Class HashTable is a simple implementation of hash table. The type for
 * keys and values is String. The implementation uses String.hashCode() as a hash function
 * and resolves collisions using separate chaining method.
 * @author Olga Lupuleac
 */
public class HashTable {
    private static final int INITIAL_CAPACITY = (int)1e6 + 7;
    private int size = 0;
    private CollisionList [] array = new CollisionList[INITIAL_CAPACITY];

    private int getArrayIndex(String key){
        return key.hashCode() % array.length;
    }

    /**
     * Returns the number of key-value mappings in this hash table.
     * @return the number of key-value mappings in this hash table
     */
    public int size(){
        return size;
    }

    /**
     * Returns true if this hash table contains a mapping for the specified key.
     * @param key key whose presence in this hash table is to be tested
     * @return true if this hash table contains a mapping for the specified key, false otherwise
     */
    public boolean contains(String key){
        int arrayIndex = getArrayIndex(key);
        return array[arrayIndex] != null && array[arrayIndex].contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this hash table contains no mapping for the key
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped,
     * or null if this hash table contains no mapping for the key
     */
    public String get(String key){
        int arrayIndex = getArrayIndex(key);
        if(array[arrayIndex] == null)
            return null;
        return array[arrayIndex].get(key);
    }

    private void rebuild(){
        if(0.75 * array.length < size){
            CollisionList[] newArray = new CollisionList[2 * array.length + 1];
            for(CollisionList list : array) {
                if (list != null) {
                    CollisionList.Node listIterator = list.getHead();
                    while (listIterator != null) {
                        int newArrayIndex = getArrayIndex(listIterator.getKey());
                        if(newArray[newArrayIndex] == null)
                            newArray[newArrayIndex] = new CollisionList();
                       newArray[newArrayIndex].add(listIterator.getKey(),
                                listIterator.getValue());
                        listIterator = listIterator.getNext();
                    }
                }
            }

        }
    }

    /**
     * Associates the specified value with the specified key in this hash table.
     * If the hash table previously contained a mapping for the key, the old value is replaced.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    public String put(String key, String value){
        rebuild();
        int arrayIndex = getArrayIndex(key);
        if(array[arrayIndex] == null)
            array[arrayIndex] = new CollisionList();
        String existedValue = array[arrayIndex].add(key, value);
        if(existedValue == null)
            size++;
        return existedValue;
    }

    /**
     * Removes the mapping for the specified key from this hash table if present.
     * @param key key whose mapping is to be removed from the table
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    public String remove(String key){
        int arrayIndex = getArrayIndex(key);
        if(array[arrayIndex] == null)
            return null;
        String value = array[arrayIndex].remove(key);
        if(value != null)
            size--;
        return value;
    }

    /**
     * Removes all of the mappings from this hash table.
     * The hash table will be empty after this call returns.
     */
    public void clear(){
        size = 0;
        array = new CollisionList[INITIAL_CAPACITY];
    }
}
