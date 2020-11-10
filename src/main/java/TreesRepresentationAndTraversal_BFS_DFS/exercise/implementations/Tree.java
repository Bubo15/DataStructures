package TreesRepresentationAndTraversal_BFS_DFS.exercise.implementations;

import TreesRepresentationAndTraversal_BFS_DFS.exercise.interfaces.AbstractTree;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

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
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.value;
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();
        eachLayerWith2SpacesInside(this, sb, 0);
        return sb.toString().trim();
    }

    @Override
    public List<E> getLeafKeys() {
        return getAllNodes()
                .stream()
                .filter(node -> node.children.size() == 0)
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        return this.getAllNodes()
                .stream()
                .filter(eTree -> eTree.children.size() != 0 && eTree.parent != null)
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> deepestLeaf = new ArrayList<>();
        deepestLeaf.add(null);
        int[] maxLevel = new int[1];
        deepestNodeWithDFS(maxLevel, 0, this, deepestLeaf);
        return deepestLeaf.get(0);
    }

    @Override
    public List<E> getLongestPath() {
        Tree<E> deepestNode = getDeepestLeftmostNode();
        List<E> allNodesElement = new ArrayList<>();

        while (deepestNode.getParent() != null) {
            allNodesElement.add(deepestNode.value);
            deepestNode = deepestNode.getParent();
        }

        allNodesElement.add(deepestNode.value);

        Collections.reverse(allNodesElement);
        return allNodesElement;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<Tree<E>> allNodes = new ArrayList<>();
        List<List<E>> result = new ArrayList<>();

        getAllTreesBySum(sum, 0, this, allNodes);

        allNodes.forEach(node -> {
            List<E> list = new ArrayList<>();
            Tree<E> current = node.getParent();

            while (current != null) {
                list.add(current.value);
                current = current.getParent();
            }

            Collections.reverse(list);
            result.add(list);
        });

        return result;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();

        queue.offer(this);

        while (!queue.isEmpty()) {
            List<Tree<E>> currentChildren = new ArrayList<>();
            Tree<E> current = queue.poll();

            for (Tree<E> child : current.children) {
                queue.offer(child);
                currentChildren.add(child);
            }

            int currentSum = getSumOfTree(currentChildren) + Integer.parseInt(current.value.toString());

            if (currentSum == sum) {
                result.add(current);
            }
        }

        return result;
    }

    private int getSumOfTree(List<Tree<E>> currentChildren) {
        int currentSum = 0;

        for (Tree<E> child : currentChildren) {
            currentSum += Integer.parseInt(child.value.toString());
        }

        return currentSum;
    }

    private List<Tree<E>> getAllNodes() {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        List<Tree<E>> allNodes = new ArrayList<>();

        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            allNodes.add(current);
            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }
        return allNodes;
    }

    private void deepestNodeWithDFS(int[] maxLevel, int currentLevel, Tree<E> eTree, List<Tree<E>> deepestLeaf) {
        if (maxLevel[0] < currentLevel) {
            deepestLeaf.set(0, eTree);
            maxLevel[0] = currentLevel;
        }

        for (Tree<E> child : eTree.children) {
            deepestNodeWithDFS(maxLevel, currentLevel + 1, child, deepestLeaf);
        }
    }

    private void eachLayerWith2SpacesInside(Tree<E> eTree, StringBuilder sb, int spaces) {
        sb.append(" ".repeat(spaces)).append(eTree.value).append(System.lineSeparator());
        for (Tree<E> child : eTree.children) {
            eachLayerWith2SpacesInside(child, sb, spaces + 2);
        }
    }

    private void getAllTreesBySum(int givenSum, int currentSum, Tree<E> eTree, List<Tree<E>> allNodes) {
        if (givenSum == currentSum) {
            allNodes.add(eTree);
        }
        for (Tree<E> child : eTree.children) {
            getAllTreesBySum(givenSum, currentSum + Integer.parseInt(child.value.toString()), child, allNodes);
        }
    }
}



