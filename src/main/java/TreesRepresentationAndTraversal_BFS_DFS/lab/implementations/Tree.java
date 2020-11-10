package TreesRepresentationAndTraversal_BFS_DFS.lab.implementations;

import TreesRepresentationAndTraversal_BFS_DFS.lab.interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {

    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... children) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();

        for (Tree<E> child : children) {
            this.children.add(child);
            child.parent = this;
        }
    }

    @Override
    public List<E> orderBfs() {
        List<E> elements = new ArrayList<>();

        if (this.value == null) {
            return elements;
        }

        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            for (Tree<E> child : current.children) {
                queue.offer(child);
            }

            elements.add(current.value);
        }
        return elements;
    }

    @Override
    public List<E> orderDfs() {
        List<E> elements = new ArrayList<>();

        if (this.value == null) {
            return elements;
        }

        dfs(this, elements);
        return elements;
    }

    private void dfs(Tree<E> element, List<E> elements) {
        for (Tree<E> child : element.children) {
            dfs(child, elements);
        }
        elements.add(element.value);
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parent = findParentByDFS(this, parentKey);

        if (parent == null) {
            throw new IllegalStateException();
        }

        parent.children.add(child);
        child.parent = parent;
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> toRemove = findParentByDFS(this, nodeKey);

        if (toRemove == null) {
            throw new IllegalStateException();
        }

        Tree<E> parentOnElementToRemove = toRemove.parent;
        if (parentOnElementToRemove != null) {
            parentOnElementToRemove.children.remove(toRemove);
        }

        toRemove.value = null;
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstNode = findParentByDFS(this, firstKey);
        Tree<E> secondNode = findParentByDFS(this, secondKey);

        if (firstNode == null || secondNode == null) {
            throw new IllegalStateException();
        }

        Tree<E> firstParent = firstNode.parent;
        Tree<E> secondParent = secondNode.parent;

        if (firstParent == null){
            swapRoot(secondNode);
            return;
        } else if (secondParent == null){
            swapRoot(firstNode);
            return;
        }

        firstNode.parent = secondParent;
        secondNode.parent = firstParent;

        int firstIndex = firstParent.children.indexOf(firstNode);
        int secondIndex = secondParent.children.indexOf(secondNode);

        firstParent.children.set(firstIndex, secondNode);
        secondParent.children.set(secondIndex, firstNode);
    }

    private Tree<E> findParentByDFS(Tree<E> eTree, E parentKey) {
        if (parentKey == eTree.value) {
            return eTree;
        }

        for (Tree<E> child : eTree.children) {
            Tree<E> found = findParentByDFS(child, parentKey);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    private void swapRoot(Tree<E> node) {
        this.value = node.value;
        this.children = node.children;
        this.parent = null;
        node.parent = null;
    }
}



