package ru.spbau202.lupuleac;

/**
 * The CollisionList class is used in HashTable.
 * It consists of elements with same hashCode
 * @see HashTable
 * @author Olga Lupuleac
 */

public class CollisionList {
    /**
     * Class Node represents an element of the list.
     * It contains key, value and links to next and previous nodes.
     * Class Node is used only inside CollisionList.
     */
    public class Node {
        private Node next;
        private Node prev;
        private String key;
        private String value;

        private Node(String key, String value){
            next = null;
            prev = null;
            this.key = key;
            this.value = value;
        }

        /**
         * The method allows iterating
         * through CollisionList.
         * @return link to next Node
         */
        public Node getNext(){
            return next;
        }

        /**Returns key for this node.
         * @return key for this node
         */
        public String getKey(){
            return key;
        }

        /**Returns key for this node.
         * @return value for this node
         */
        public String getValue(){
            return value;
        }
    }

    private Node head;
    private Node tail;

    /**
     * Method to get reference to the first node in the list.
     * @return head of the list
     */
    public Node getHead(){
        return head;
    }

    /**
     * The method checks if the list contains the specified key.
     * @param key key whose presence in this list is to be tested
     * @return true if key in the list false otherwise
     */
    public boolean contains(String key){
        Node curNode = head;
        while(curNode != null){
            if(curNode.key.equals(key)){
                return true;
            }
            curNode = curNode.next;
        }
        return false;
    }

    /**
     * Changes value matches the specified key to the specified value
     * or appends the specified element (key and value) to the end of this list.
     * @param key key to be added
     * @param value value to be added
     * @return value which the key has before or null if this key was not in the list
     */
    public String add(String key, String value){
        if(head == null){
            head = new Node(key, value);
            tail = head;
            return null;
        }
        Node curNode = head;
        while(curNode != null){
            if(curNode.key.equals(key)){
                String existedValue = curNode.value;
                curNode.value = value;
                return existedValue;
            }
            curNode = curNode.next;
        }
        Node newNode = new Node(key, value);
        if(tail != null){
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        return null;
    }

    /**
     * Gets value by the specified key
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is in the list,
     * or null if this list contains no matching value for the key.
     */
    public String get(String key){
        Node curNode = head;
        while(curNode != null){
            if(curNode.key.equals(key)){
                return curNode.value;
            }
            curNode = curNode.next;
        }
        return null;
    }

    /**
     * Removes element by the specified key from the list if present.
     * @param key the key matches the element to be removed.
     * @return valuethe previous value associated with key, or null if there was no element with this key
     */
    public String remove(String key){
        Node curNode = head;
        while(curNode != null){
            if(curNode.key.equals(key)){
                if(curNode.next != null)
                    curNode.next.prev = curNode.prev;
                else
                    tail = curNode.prev;
                if(curNode.prev != null)
                    curNode.prev.next = curNode.next;
                else
                    head = curNode.next;
                return curNode.value;
            }
            curNode = curNode.next;
        }
        return null;
    }

}
