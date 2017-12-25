package ru.spbau202.lupuleac.SmartList;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Class SmartList is used for optimized storage small number of elements.
 * Contains null if number of elements is 0, reference to the element if it is only element in the list,
 * array if number of elements is less than 6 and ArrayList otherwise.
 * @param <E> is a type of an element in the list
 */
public class SmartList<E> extends AbstractList<E> {
    private int size;

    private Object data;

    public SmartList(){
    }

    /**
     * Creates list which contains all objects in the collection.
     * @param collection is collection with objects to be added
     */
    public SmartList(Collection<E> collection){
        addAll(collection);
    }

    /**
     * Returns element relevant to this index
     * @param index is index to get relevant element
     * @return element relevant to this index
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(size == 1){
            return (E)data;
        }
        if(size < 6){
            return ((E[]) data)[index];
        }
        return ((ArrayList<E>)data).get(index);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean add(E elem){
        size++;
        if(size == 1){
            data = elem;
            return true;
        }
        if(size < 6){
            if(size == 2){
                Object[] array = new Object[5];
                array[0] = data;
                array[1] = elem;
                data = array;
                return true;
            }
            ((E[]) data)[size - 1] = elem;
            return true;
        }
        if(size == 6){
            ArrayList<E> arrayList = new ArrayList<>();
            arrayList.addAll(Arrays.asList(((E[]) data)));
            arrayList.add(elem);
            data = arrayList;
            return true;
        }
        return ((ArrayList<E>)data).add(elem);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E e){
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(size == 1){
            E previous = (E)data;
            data = e;
            return previous;
        }
        if(size < 6){
            E previous = ((E[]) data)[index];
            ((E[]) data)[index] = e;
            return previous;
        }
        return ((ArrayList<E>)data).set(index, e);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index){
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        size--;
        if(size == 0){
            E previous = (E)data;
            data = null;
            return previous;
        }
        if(size == 1){
            E previous = ((E[]) data)[index];
            data = ((E[]) data)[1 - index];
            return previous;
        }
        if(size < 5){
            E previous = ((E[]) data)[index];
            for(int i = index; i < size - 1; i++){
                ((E[]) data)[i] = ((E[]) data)[i + 1];
            }
            ((E[]) data)[size - 1] = null;
            return previous;
        }
        if(size == 5){
            E previous = ((ArrayList<E>)data).remove(index);
            data = ((ArrayList<E>)data).toArray();
            return previous;

        }
        return ((ArrayList<E>)data).remove(index);
    }

    @Override
    public void clear(){
        size = 0;
        data = null;
    }


}
