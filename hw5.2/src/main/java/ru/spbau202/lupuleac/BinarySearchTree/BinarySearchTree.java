package ru.spbau202.lupuleac.BinarySearchTree;

import com.sun.istack.internal.NotNull;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Unbalanced binary search tree (BST) containing comparable elements.
 * It can add element to BST, check if the certain element is present or get the BST size.
 *
 * @param <T> is the generic type of the element in the BinarySearchTree. T must implement Comparable.
 */
public class BinarySearchTree<T extends Comparable> {
    private int size;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;
    private T value;

    /**
     * Returns the nearest node relevant to the specified value
     * or a node which would be parent to the value if it were added to the BST at this moment.
     *
     * @param t is the value which relevant is to be got.
     * @return the nearest node relevant to the given value
     * or a node which would be parent to the value if it were added to the BST at this moment.
     */
    private BinarySearchTree<T> getNode(T t) {
        if (value == null || t.compareTo(value) == 0) {
            return this;
        }
        if (t.compareTo(value) < 0) {
            if (left == null) {
                return this;
            }
            return left.getNode(t);
        }
        if (right == null) {
            return this;
        }
        return right.getNode(t);
    }

    /**
     * Returns number of elements in the BinarySearchTree.
     *
     * @return number of elements in the BST.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the BST conains the given element.
     *
     * @param t is the element which presence in the BST is to be checked.
     * @return true if the element is in the BST.
     */
    public boolean contains(@NotNull T t) {
        return t.compareTo(getNode(t).value) == 0;
    }


    /**
     * Adds the specified element to the BST.
     *
     * @param t is the element to be added.
     * @return true if this BST did not already contain the specified element
     */
    public boolean add(@NotNull T t) {
        BinarySearchTree<T> node = getNode(t);
        if (node.value == null) {
            node.value = t;
            size++;
            return true;
        }
        if (t.compareTo(node.value) == 0) {
            return false;
        }
        size++;
        if (t.compareTo(node.value) < 0) {
            node.left = new BinarySearchTree<>();
            node.left.value = t;
        } else {
            node.right = new BinarySearchTree<>();
            node.right.value = t;
        }
        return true;
    }


}
