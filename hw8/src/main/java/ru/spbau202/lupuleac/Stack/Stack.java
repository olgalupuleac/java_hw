package ru.spbau202.lupuleac.Stack;

/**
 * The Stack class represents a last-in-first-out (LIFO) stack of objects.
 * The usual push and pop operations are provided,
 * as well as a method to peek at the top item on the stack,
 * a method to test for whether the stack is empty and method to clear the contents.
 *
 * @param <T> is a type of the objects in stack.
 */
public class Stack<T> {
    private static int INITIAL_CAPACITY = 20;
    @SuppressWarnings("unchecked")
    private T[] data = (T[]) new Object[INITIAL_CAPACITY];
    private int capacity = INITIAL_CAPACITY;
    private int size = 0;

    /**
     * Returns number of elements in stack.
     *
     * @return number of elements in stack
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Pushes an item onto the top of this stack.
     *
     * @param t the item to be pushed onto this stack
     */
    public void push(T t) {
        if (size == capacity) {
            capacity *= 2;
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) new Object[capacity];
            System.arraycopy(data, 0, newArray, 0, size);
            data = newArray;
        }
        data[size++] = t;
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack.
     *
     * @return the object at the top of this stack
     */
    public T top() {
        return data[size - 1];
    }

    /**
     * Removes the object at the top of this stack
     * and returns that object as the value of this function.
     *
     * @return the object at the top of this stack to be removed
     */
    public T pop() {
        return data[--size];
    }

    /**
     * Clears the stack content.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        size = 0;
        capacity = INITIAL_CAPACITY;
        data = (T[]) new Object[INITIAL_CAPACITY];
    }

}
