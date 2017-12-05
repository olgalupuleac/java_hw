package ru.spbau202.lupuleac.TreeSet;

import org.jetbrains.annotations.NotNull;
import ru.spbau202.MyTreeSet;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * {@link java.util.TreeSet}
 **/
public class TreeSet<E> extends AbstractSet<E> implements MyTreeSet<E> {
    private Tree<E> tree;

    /**
     * {@inheritDoc}
     */
    public TreeSet() {
        tree = new Tree<>();
    }

    /**
     * {@inheritDoc}
     */
    public TreeSet(@NotNull Comparator<? super E> comparator) {
        tree = new Tree<>(comparator);
    }

    private TreeSet(Tree<E> tree) {
        this.tree = tree;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return tree.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Iterator<E> descendingIterator() {
        return tree.descendingIterator();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public MyTreeSet<E> descendingSet() {
        return new TreeSet<E>((Tree<E>) tree.descendingSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E first() {
        return iterator().next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E last() {
        return descendingIterator().next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E lower(@NotNull E e) {
        return tree.lower(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E floor(@NotNull E e) {
        return tree.floor(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E ceiling(@NotNull E e) {
        return tree.ceiling(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E higher(@NotNull E e) {
        return tree.higher(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return tree.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(@NotNull Object o) {
        return tree.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        tree.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(@NotNull E e) {
        return tree.add(e);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(@NotNull Object e) {
        return tree.remove(e);
    }

    private static class Tree<E> extends AbstractSet<E> implements MyTreeSet<E> {
        private boolean descending;
        private E elem;
        private Tree<E> left;
        private Tree<E> right;
        private Tree<E> parent;
        private Comparator<? super E> comparator;
        private int size;

        @SuppressWarnings("unchecked")
        public Tree() {
            comparator = (Comparator<E>) (e1, e2) -> ((Comparable<E>) e1).compareTo(e2);
        }

        public Tree(@NotNull Comparator<? super E> comparator) {
            this.comparator = comparator;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public Iterator<E> iterator() {
            return new TreeIterator(descending);
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public Iterator<E> descendingIterator() {
            return new TreeIterator(!descending);
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public MyTreeSet<E> descendingSet() {
            Tree<E> result = new Tree<>(comparator);
            result.descending = !descending;
            result.elem = elem;
            result.left = left;
            result.right = right;
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E first() {
            return iterator().next();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E last() {
            return descendingIterator().next();
        }

        private Tree<E> lowerNode(@NotNull E e) {
            if (elem == null) {
                return null;
            }
            if (!descending) {
                if (comparator.compare(elem, e) < 0) {
                    Tree<E> rightRes = null;
                    if (right != null) {
                        rightRes = right.lowerNode(e);
                    }
                    if (rightRes == null) {
                        return this;
                    }
                    return rightRes;
                }
                if (left == null) {
                    return null;
                }
                return left.lowerNode(e);
            }
            return ((Tree<E>) descendingSet()).higherNode(e);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E lower(@NotNull E e) {
            Tree<E> node = lowerNode(e);
            return node != null ? node.elem : null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E floor(@NotNull E e) {
            if (!contains(e)) {
                return lower(e);
            }
            return e;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E ceiling(@NotNull E e) {
            if (!contains(e)) {
                return higher(e);
            }
            return e;
        }

        private Tree<E> higherNode(@NotNull E e) {
            if (elem == null) {
                return null;
            }
            if (!descending) {
                if (comparator.compare(elem, e) > 0) {
                    Tree<E> leftRes = null;
                    if (left != null) {
                        leftRes = left.higherNode(e);
                    }
                    if (leftRes == null) {
                        return this;
                    }
                    return leftRes;
                }
                if (right == null) {
                    return null;
                }
                return right.higherNode(e);
            }
            return ((Tree<E>) descendingSet()).lowerNode(e);

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E higher(@NotNull E e) {
            Tree<E> node = higherNode(e);
            return node != null ? node.elem : null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public boolean contains(@NotNull Object o) {
            if (elem == null) {
                return false;
            }
            if (elem.equals(o)) {
                return true;
            }
            if (comparator.compare(elem, (E) o) > 0) {
                return left != null && left.contains(o);
            }
            return right != null && right.contains(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            size = 0;
            left = null;
            right = null;
            elem = null;
            parent = null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(@NotNull E e) {
            //checks only ones
            if (parent == null && contains(e)) {
                return false;
            }
            size++;
            if (elem == null) {
                elem = e;
                return true;
            }
            if (comparator.compare(e, elem) < 0) {
                if (left == null) {
                    left = new Tree<E>(comparator);
                    left.parent = this;
                    left.size = 1;
                    left.elem = e;
                    return true;
                }
                return left.add(e);
            }
            if (right == null) {
                right = new Tree<E>(comparator);
                right.parent = this;
                right.elem = e;
                right.size = 1;
                return true;
            }
            return right.add(e);

        }

        private void deleteLeaf() {
            if (parent != null) {
                if (parent.left == this) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
            clear();
        }

        private void deleteNodeWithEmptyRightTree() {
            if (parent == null) {
                elem = left.elem;
            } else {
                parent.left = left;
            }
            left.parent = parent;
        }

        private void swapWithLeafAndDelete() {
            E elemToSwap = right.first();
            elem = elemToSwap;
            right.remove(elemToSwap);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public boolean remove(@NotNull Object e) {
            //checks only ones
            if (parent == null && !contains(e)) {
                return false;
            }
            size--;
            if (elem.equals(e)) {
                if (left == null && right == null) {
                    deleteLeaf();
                    return true;
                }
                if (right == null) {
                    deleteNodeWithEmptyRightTree();
                    return true;
                }
                swapWithLeafAndDelete();
                return true;
            }
            if (comparator.compare(elem, (E) e) > 0) {
                return left.remove(e);
            }
            return right.remove(e);
        }

        private class TreeIterator implements Iterator<E> {
            private Tree<E> current;
            private boolean descending;

            private TreeIterator(boolean descending) {
                current = Tree.this;
                this.descending = descending;
                if (!descending) {
                    while (current.left != null) {
                        current = current.left;
                    }
                } else {
                    while (current.right != null) {
                        current = current.right;
                    }
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return current != null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public E next() {
                if (current == null || current.elem == null) {
                    throw new NoSuchElementException();
                }
                E res = current.elem;
                if (descending ^ Tree.this.descending) {
                    current = Tree.this.lowerNode(res);
                } else {
                    current = Tree.this.higherNode(res);
                }
                return res;
            }
        }
    }
}




