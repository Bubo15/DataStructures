package BinaryTreesHeapsAndBST.lab.implementations;

import BinaryTreesHeapsAndBST.lab.interfaces.AbstractBinarySearchTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {

    private Node<E> root;
    private Node<E> leftChild;
    private Node<E> rightChild;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Node<E> root) {
        this.copy(root);
    }

    private void copy(Node<E> node) {
        if (node != null) {
            this.insert(node.value);
            this.copy(node.leftChild);
            this.copy(node.rightChild);
        }
    }

    @Override
    public void insert(E element) {
        Node<E> newElement = new Node<>(element);

        if (this.getRoot() == null) {
            this.root = newElement;
        } else {
            Node<E> current = this.getRoot();
            Node<E> prev = current;

            while (current != null) {
                prev = current;
                // If element is smaller than current ==> go left
                if (isLess(element, current.value)) {
                    current = current.leftChild;
                    // If element is bigger than current ==> go right
                } else if (isGreater(element, current.value)) {
                    current = current.rightChild;
                } else {
                    return;
                }
            }

            if (isLess(element, prev.value)) {
                prev.leftChild = newElement;
            } else if (isGreater(element, prev.value)) {
                prev.rightChild = newElement;
            }
        }
    }

    @Override
    public boolean contains(E element) {
        Node<E> current = this.getRoot();
        while (current != null) {
            if (isLess(element, current.value)) {
                current = current.leftChild;
            } else if (isGreater(element, current.value)) {
                current = current.rightChild;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {
        AbstractBinarySearchTree<E> result = new BinarySearchTree<>();

        Node<E> current = this.getRoot();
        while (current != null) {
            if (isLess(element, current.value)) {
                current = current.leftChild;
            } else if (isGreater(element, current.value)) {
                current = current.rightChild;
            } else {
                return new BinarySearchTree<>(current);
            }
        }

        return result;
    }

    public List<E> range(E lower, E upper) {
        List<E> result = new ArrayList<>();
        Deque<Node<E>> queue = new ArrayDeque<>();

        if (this.root == null) {
            return result;
        }
        queue.offer(this.root);

        while (!queue.isEmpty()) {
            Node<E> current = queue.poll();

            if (current.leftChild != null) {
                queue.offer(current.leftChild);
            }
            if (current.rightChild != null) {
                queue.offer(current.rightChild);
            }

            if (isLess(lower, current.value) && isGreater(upper, current.value)) {
                result.add(current.value);
            } else if (isEqual(lower, current.value) || isEqual(upper, current.value)) {
                result.add(current.value);
            }
        }

        return result;
    }

    public void deleteMin() {
        if (this.root == null) {
            throw new IllegalArgumentException();
        }

        if (this.root.leftChild == null) {
            this.root = this.root.rightChild;
            return;
        }

        Node<E> current = this.root;

        while (current != null) {
            if (current.leftChild.leftChild == null) {
                // Here when have to delete left child (min element)
                // if there is right element we will get it and push on left
                // if there isn't we will set null
                current.leftChild = current.leftChild.rightChild;
                break;
            }
            current = current.leftChild;
        }
    }

    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalArgumentException();
        }

        if (this.root.rightChild == null) {
            this.root = this.root.leftChild;
            return;
        }

        Node<E> current = this.root;

        while (current != null) {
            if (current.rightChild.rightChild == null) {
                current.rightChild = current.rightChild.leftChild;
                break;
            }
            current = current.rightChild;
        }
    }

    public E ceil(E element) {
        if (this.root == null) {
            return null;
        }

        Node<E> current = this.root;
        E nearestBigger = null;

        while (current != null) {

            if (isLess(element, current.value)) {
                nearestBigger = current.value;
                current = current.leftChild;
            } else if (isGreater(element, current.value)) {
                current = current.rightChild;
            } else {
                Node<E> right = current.rightChild;

                if (right != null && nearestBigger != null) {
                    nearestBigger = isLess(right.value, nearestBigger) ? right.value : nearestBigger;
                } else if (nearestBigger == null && right != null) {
                    nearestBigger = isGreater(element, right.value) ? element : right.value;
                }
                break;
            }
        }

        return nearestBigger;
    }

    public E floor(E element) {
        if (this.root == null) {
            return null;
        }

        Node<E> current = this.root;
        E nearestSmaller = null;

        while (current != null) {
            if (isGreater(element, current.value)) {
                nearestSmaller = current.value;
                current = current.rightChild;
            } else if (isLess(element, current.value)) {
                current = current.leftChild;
            } else {
                Node<E> left = current.leftChild;

                if (left != null) {
                    if (nearestSmaller != null) {
                        nearestSmaller = isGreater(left.value, nearestSmaller) ? left.value : nearestSmaller;
                    } else {
                        nearestSmaller = isLess(element, left.value) ? element : left.value;
                    }
                }
                break;
            }
        }
        return nearestSmaller;
    }

    @Override
    public AbstractBinarySearchTree.Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.rightChild;
    }

    @Override
    public E getValue() {
        return this.root.value;
    }

    private boolean isLess(E first, E second) {
        return first.compareTo(second) < 0;
    }

    private boolean isGreater(E first, E second) {
        return first.compareTo(second) > 0;
    }

    private boolean isEqual(E first, E second) {
        return first.compareTo(second) == 0;
    }
}