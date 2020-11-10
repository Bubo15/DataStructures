package BinaryTreesHeapsAndBST.lab.implementations;

import BinaryTreesHeapsAndBST.lab.interfaces.AbstractQueue;

import java.util.*;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private List<E> elements;

    public PriorityQueue() {
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

    @Override
    public E peek() {
        ensureNotEmpty();
        return this.elements.get(0);
    }

    @Override
    public E poll() {
        ensureNotEmpty();

        E toRemove = this.elements.get(0);

//      Collections.swap(this.elements, 0, this.size() - 1);
//      this.elements.remove(this.size() - 1);
        this.elements.set(0, this.elements.get(this.size() - 1));
        this.elements.remove(this.size() - 1);
        this.heapifyDown(0);

        return toRemove;
    }

    private void heapifyDown(int index) {
        // First, this child is left
        int childToSwap = getLeftChildIndex(index);

        // Check whether left child index is in bound && whether index is less than left child, because
        // current index must be below left or right child if is smaller
        while (childToSwap < this.size() && this.isLess(index, childToSwap)){
            int rightChild = getRightChildIndex(index);

            // If right child is bigger than left we have to swap index with right
            if (rightChild < this.size() && this.isLess(childToSwap, rightChild)){
                childToSwap = rightChild;
            }

            Collections.swap(this.elements, index, childToSwap);
            index = childToSwap;
            childToSwap = getLeftChildIndex(index);
        }
    }

    private void heapifyUp(int index) {
        int parentIndex = getParent(index);
        while (index > 0 && isLess(parentIndex, index)){
            Collections.swap(this.elements, parentIndex, index);
            index = parentIndex;
            parentIndex = getParent(index);
        }
    }

    private int getLeftChildIndex(int index){
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index){
        return 2 * index + 2;
    }

    private int getParent(int index) {

        Map asd = new TreeMap();
        return (index - 1) / 2;
    }

    private boolean isLess(int first, int second) {
        return this.elements.get(first).compareTo(this.elements.get(second)) < 0;
    }

    private void ensureNotEmpty(){
        if (this.size() == 0){
            throw new IllegalStateException();
        }
    }
}
