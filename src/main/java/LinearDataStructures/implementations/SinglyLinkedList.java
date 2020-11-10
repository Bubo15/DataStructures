package LinearDataStructures.implementations;

import LinearDataStructures.interfaces.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {

    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E value) {
            this.element = value;
        }
    }

    private int size;
    private Node<E> head;
    private Node<E> tail;

    public SinglyLinkedList() {
        this.size = 0;
        this.head = null;
    }

    @Override
    public void addFirst(E element) {
        Node<E> newElement = new Node<>(element);
        if (isEmpty()){
            this.tail = newElement;
        }
        newElement.next = this.head;
        this.head = newElement;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element);

        if (isEmpty()) {
            this.head = this.tail = newNode;
        } else {
            this.tail.next = newNode;
            this.tail = newNode;
        }

        this.size++;
    }

    @Override
    public E removeFirst() {
        ensureNonEmpty();
        E toRemove = this.head.element;
        this.head = this.head.next;
        this.size--;
        return toRemove;
    }

    @Override
    public E removeLast() {
        ensureNonEmpty();

        if (this.size == 1){
            E value = this.head.element;
            this.head = this.tail = null;
            return value;
        }

        Node<E> becomeLastElement = this.head;

        while (becomeLastElement.next.next != null){
            becomeLastElement = becomeLastElement.next;
        }

        E removedElement = becomeLastElement.next.element;

        becomeLastElement.next = null;
        this.tail = becomeLastElement;

        return removedElement;
    }

    @Override
    public E getFirst() {
        ensureNonEmpty();
        return this.head.element;
    }

    @Override
    public E getLast() {
        ensureNonEmpty();
        return this.tail.element;
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
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public E next() {
                E element = this.current.element;
                this.current = this.current.next;
                return element;
            }
        };
    }

    private void ensureNonEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
    }
}
