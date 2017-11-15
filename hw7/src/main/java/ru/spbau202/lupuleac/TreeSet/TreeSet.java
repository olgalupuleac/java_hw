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
    private boolean descending;
    private E elem;
    private TreeSet<E> left;
    private TreeSet<E> right;
    private TreeSet<E> parent;
    private Comparator<? super E> comparator;
    private int size;

    @SuppressWarnings("unchecked")
    public TreeSet() {
        comparator = (Comparator<E>) (e1, e2) -> ((Comparable<E>) e1).compareTo(e2);
    }

    public TreeSet(@NotNull Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new TreeSetIterator(descending);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Iterator<E> descendingIterator() {
        return new TreeSetIterator(!descending);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public MyTreeSet<E> descendingSet() {
        TreeSet<E> result = new TreeSet<>(comparator);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public E lower(@NotNull E e) {
        if (elem == null) {
            return null;
        }
        if (!descending) {
            if (comparator.compare(elem, e) < 0) {
                E rightRes = null;
                if (right != null) {
                    rightRes = right.lower(e);
                }
                if (rightRes == null) {
                    return elem;
                }
                return rightRes;
            }
            if (left == null) {
                return null;
            }
            return left.lower(e);
        }
        return descendingSet().higher(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E floor(@NotNull E e) {
        if (elem == null) {
            return null;
        }
        if (!descending) {
            if (comparator.compare(elem, e) <= 0) {
                E rightRes = null;
                if (right != null) {
                    rightRes = right.floor(e);
                }
                if (rightRes == null) {
                    return elem;
                }
                return rightRes;
            }
            if (left == null) {
                return null;
            }
            return left.floor(e);
        }
        return descendingSet().ceiling(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E ceiling(@NotNull E e) {
        if (elem == null) {
            return null;
        }
        if (!descending) {
            if (comparator.compare(elem, e) >= 0) {
                E leftRes = null;
                if (left != null) {
                    leftRes = left.ceiling(e);
                }
                if (leftRes == null) {
                    return elem;
                }
                return leftRes;
            }
            if (right == null) {
                return null;
            }
            return right.ceiling(e);
        }
        return descendingSet().floor(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E higher(@NotNull E e) {
        if (elem == null) {
            return null;
        }
        if (!descending) {
            if (comparator.compare(elem, e) > 0) {
                E leftRes = null;
                if (left != null) {
                    leftRes = left.higher(e);
                }
                if (leftRes == null) {
                    return elem;
                }
                return leftRes;
            }
            if (right == null) {
                return null;
            }
            return right.higher(e);
        }
        return descendingSet().lower(e);

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
                left = new TreeSet<E>(comparator);
                left.parent = this;
                left.size = 1;
                left.elem = e;
                return true;
            }
            return left.add(e);
        }
        if (right == null) {
            right = new TreeSet<E>(comparator);
            right.parent = this;
            right.elem = e;
            right.size = 1;
            return true;
        }
        return right.add(e);

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
                if (parent != null) {
                    if (parent.left == this) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                }
                clear();
                return true;
            }
            if (right == null) {
                if (parent == null) {
                    elem = left.elem;
                } else {
                    parent.left = left;
                }
                left.parent = parent;
                return true;
            }
            E elemToSwap = right.first();
            elem = elemToSwap;
            right.remove(elemToSwap);
            return true;
        }
        if (comparator.compare(elem, (E) e) > 0) {
            return left.remove(e);
        }
        return right.remove(e);
    }

    private class TreeSetIterator implements Iterator<E> {
        private TreeSet<E> current;
        private boolean descending;

        private TreeSetIterator(boolean descending) {
            current = TreeSet.this;
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
            if (descending) {
                if (current.left != null) {
                    current = ((TreeSetIterator) current.left.descendingIterator()).current;
                    return res;
                } else {
                    while (current != null && (current.parent == null
                            || TreeSet.this.comparator.compare(
                            current.elem, current.parent.elem) < 0)) {
                        current = current.parent;
                    }
                    if (current != null) {
                        current = current.parent;
                    }
                }
            } else {
                if (current.right != null) {
                    current = ((TreeSetIterator) current.right.iterator()).current;
                    return res;
                } else {
                    while (current != null && (current.parent == null
                            || TreeSet.this.comparator.compare(
                            current.elem, current.parent.elem) > 0)) {
                        current = current.parent;
                    }
                    if (current != null) {
                        current = current.parent;
                    }
                }
            }
            return res;
        }
    }


}




