package LinearDataStructures.implementations;

import LinearDataStructures.interfaces.List;

import java.util.Iterator;

public class ArrayList<E> implements List<E> {

    private static final int INITIAL_CAPACITY = 4;
    private Object[] elements;
    private int size;

    public ArrayList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public boolean add(E element) {
        if (notEnoughCapacity()) {
            resize();
        }

        elements[this.size] = element;
        this.size++;
        return true;
    }

    @Override
    public boolean add(int index, E element) {
        ensureIndex(index);
        moveElementsRight(index);
        this.elements[index] = element;
        this.size++;
        return true;
    }

    @Override
    public E get(int index) {
        return (E) this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        ensureIndex(index);

        Object oldElement = this.elements[index];
        this.elements[index] = element;

        return (E) (oldElement);
    }

    @Override
    public E remove(int index) {
        ensureIndex(index);
        Object element = this.elements[index];
        moveElementsLeft(index);
        this.elements[this.size - 1] = null;
        this.size--;
        return (E) element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.elements.length; i++) {
            if (element.equals(this.elements[i])){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        for (Object o : this.elements) {
            if (element.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }

    private void resize() {
        Object[] newElements = new Object[elements.length * 2];

        for (int i = 0; i < this.elements.length; i++) {
            newElements[i] = this.elements[i];
        }

        this.elements = newElements;
    }

    private void moveElementsRight(int index) {
        for (int i = this.size - 1; i >= index; i--) {
            if (notEnoughCapacity()){ resize(); }
            this.elements[i + 1] = this.elements[i];
        }
    }

    private void moveElementsLeft(int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }

    private boolean notEnoughCapacity() {
        return this.size + 1 == this.elements.length;
    }

    private void ensureIndex(int index) {
        if (index >= this.size || index < 0){
            throw new IndexOutOfBoundsException();
        }
    }
}
