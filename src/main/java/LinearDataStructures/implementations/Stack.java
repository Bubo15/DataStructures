package LinearDataStructures.implementations;

import LinearDataStructures.interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {

    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E value) {
            this.element = value;
        }
    }

    private int size;
    private Node<E> top;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(E element) {
        Node<E> toPush = new Node<>(element);
        toPush.next = top;
        top = toPush;
        size++;
    }

    @Override
    public E pop() {
        ensureNonEmpty();
        E removedElement = this.top.element;
        top = top.next;
        this.size--;
        return removedElement;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.top.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Stack.Node<E> current = top;
            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public E next() {
                E value =  this.current.element;
                this.current = this.current.next;
                return value;
            }
        };
    }

    private void ensureNonEmpty() {
        if (isEmpty()){
            throw new IllegalStateException();
        }
    }
}
