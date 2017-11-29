package ru.spbau202.lupuleac.Stack;
;

public class Stack<T> {
    private static int INITIAL_CAPACITY = 20;
    @SuppressWarnings("unchecked")
    private T[] data = (T[]) new Object[INITIAL_CAPACITY];
    private int capacity = INITIAL_CAPACITY;
    private int size = 0;

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void push(T t){
        if(size == capacity){
            capacity *= 2;
            @SuppressWarnings("unchecked")
            T[] newArray = (T[])new Object[capacity];
            System.arraycopy(data, 0, newArray, 0, size);
            data = newArray;
        }
        data[size++] = t;
    }

    public T top(){
        return data[size - 1];
    }

    public T pop(){
        return data[--size];
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        size = 0;
        capacity = INITIAL_CAPACITY;
        data = (T[]) new Object[INITIAL_CAPACITY];
    }

}
