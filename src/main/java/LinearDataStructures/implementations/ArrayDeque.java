package LinearDataStructures.implementations;

import LinearDataStructures.interfaces.Deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {

    private static final int INITIAL_CAPACITY = 7;

    private Object[] elements;
    private int head;
    private int tail;
    private int size;

    public ArrayDeque() {
        this.elements = new Object[INITIAL_CAPACITY];
        int middle = INITIAL_CAPACITY / 2;
        this.head = this.tail = middle;
    }

    @Override
    public void add(E element) {
        if (isEmpty()) {
            this.elements[this.tail] = element;
        } else {
            if (this.tail == this.elements.length - 1) {
                this.elements = grow();
            }
            this.elements[++this.tail] = element;
        }
        this.size++;
    }

    @Override
    public void offer(E element) {
        add(element);
    }

    @Override
    public void addFirst(E element) {
        if (isEmpty()) {
            this.elements[this.tail] = element;
        } else {
            if (this.head == 0) {
                this.elements = grow();
            }
            this.elements[--this.head] = element;
        }
        this.size++;
    }

    @Override
    public void addLast(E element) {
        add(element);
    }

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public void insert(int index, E element) {
        if (this.head == 0 || this.tail >= this.elements.length){
            this.elements = grow();
        }

        int realIndex = this.head + index;
        ensureIndex(realIndex);

        if (realIndex - head < this.tail - realIndex){
            moveElementsLeft(realIndex);
            this.elements[realIndex - 1] = element;
        }else {
            moveElementsRight(realIndex);
            this.elements[realIndex] = element;
        }
        this.size++;
    }

    @Override
    public void set(int index, E element) {
        ensureIndex(this.head + index);
        this.elements[this.head + index] = element;
    }

    @Override
    public E peek() {
        if (this.size != 0){
            return (E) this.elements[this.head];
        }
        return null;
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeLast();
    }

    @Override
    public E get(int index) {
        ensureIndex(this.head + index);
        return (E) this.elements[this.head + index];
    }

    @Override
    public E get(Object object) {
        if (isEmpty()){
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)){
                return (E) this.elements[i];
            }
        }
        return null;
    }

    @Override
    public E remove(int index) {
        int realIndex = this.head + index;
        ensureIndex(realIndex);

        E element = (E) this.elements[realIndex];
        moveElementsLeftByRemove(realIndex);
        removeFirst();

        return element;
    }

    @Override
    public E remove(Object object) {
        if (isEmpty()){
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)){
                E element  = (E) this.elements[i];
                remove(i - this.head);
                return element;
            }
        }
        return null;
    }

    @Override
    public E removeFirst() {
        if (!isEmpty()){
            E element = (E) this.elements[this.head];

            this.elements[this.head] = null;
            this.head++;
            this.size--;

            return element;
        }
        return null;
    }

    @Override
    public E removeLast() {
        if (!isEmpty()){
            E element = (E) this.elements[this.tail];

            this.elements[this.tail] = null;
            this.tail--;
            this.size--;

            return element;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {
        Object[] trimElements = new Object[this.size];
        int index = 0;
        for (Object element : this.elements) {
            if (element != null) {
                trimElements[index++] = element;
            }
        }
        this.head = 0;
        this.tail = this.size - 1;
        this.elements = trimElements;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = head;
            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(head - index++);
            }
        };
    }

    private Object[] grow() {
        Object[] newElements = new Object[this.elements.length * 2 + 1];

        int middle = newElements.length / 2;
        int startFrom = middle - this.size / 2;
        int head = this.head;

        for (int i = head; i <= this.tail; i++) {
            newElements[startFrom++] = this.elements[head++];
        }

        this.head = startFrom - this.size;
        this.tail = this.head + this.size - 1;

        return newElements;
    }

    private void ensureIndex(int index) {
        if (index < this.head || index > this.tail){
            throw new IndexOutOfBoundsException();
        }
    }

    private void moveElementsLeft(int index) {
        for (int i = this.head; i <= index; i++) {
            this.elements[i - 1] = this.elements[i];
        }
        this.head--;
    }

    private void moveElementsLeftByRemove(int index) {
        for (int i = index; i > this.head; i--) {
            this.elements[i] = this.elements[i - 1];
        }
    }

    private void moveElementsRight(int index) {
        for (int i = this.tail; i >= index; i--) {
            this.elements[i + 1] = this.elements[i];
        }
        this.tail++;
    }
}
