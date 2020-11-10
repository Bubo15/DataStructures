package BinaryTreesHeapsAndBST.lab.implementations;

import BinaryTreesHeapsAndBST.lab.interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {

    private List<E> elements;

    public MaxHeap() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size() - 1);
    }

    private void heapifyUp(int index) {
        int parentIndex = getParent(index);
        while (index > 0 && isLess(parentIndex, index)){
            Collections.swap(this.elements, parentIndex, index);
            index = parentIndex;
            parentIndex = getParent(index);
        }
    }

    private boolean isLess(int first, int second) {
        return this.elements.get(first).compareTo(this.elements.get(second)) < 0;
    }

    private int getParent(int index) {
        return (index - 1) / 2;
    }

    @Override
    public E peek() {
        if (this.size() == 0) {throw new IllegalStateException(); }
        return this.elements.get(0);
    }
}
