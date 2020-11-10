package BinaryTreesHeapsAndBST.exercise.implementations;


import BinaryTreesHeapsAndBST.exercise.interfaces.Decrease;
import BinaryTreesHeapsAndBST.exercise.interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {

    private List<E> elements;

    public MinHeap() {
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
        int parentIndex = (index - 1) / 2;
        while (index > 0 && isLess(index, parentIndex)){
            Collections.swap(this.elements, index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private boolean isLess(int index, int parentIndex) {
        return this.elements.get(index).compareTo(this.elements.get(parentIndex)) < 0;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.elements.get(0);
    }

    @Override
    public E poll() {
        ensureNonEmpty();

        E returnedValue = this.elements.get(0);
        Collections.swap(this.elements, 0, this.size() - 1);
        this.elements.remove(this.size() - 1);

        this.heapifyDown(0);

        return returnedValue;
    }

    private void heapifyDown(int index) {

        while (this.getLeftChildIndex(index) < this.size() && this.isLess(index, this.getLeftChildIndex(index))){
            int childToSwap = this.getLeftChildIndex(index);
            int rightIndex = this.getRightChildIndex(index);

            if (rightIndex < this.size() && this.isLess(childToSwap, rightIndex)){
                childToSwap = rightIndex;
            }

            Collections.swap(this.elements, index, childToSwap);
            index = childToSwap;
        }
    }

    private int getLeftChildIndex(int index){
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index){
        return 2 * index + 2;
    }

    @Override
    public void decrease(E element) {
        int index = this.elements.indexOf(element);
        E heapElement = this.elements.get(index);
        heapElement.decrease();
        this.heapifyUp(index);
    }

    private void ensureNonEmpty() {
        if (this.size() == 0){
            throw new IllegalStateException();
        }
    }
}
