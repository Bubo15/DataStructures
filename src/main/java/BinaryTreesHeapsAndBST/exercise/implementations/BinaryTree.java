package BinaryTreesHeapsAndBST.exercise.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BinaryTree {

    private int key;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(int key, BinaryTree first, BinaryTree second) {
        this.key = key;
        this.left = first;
        this.right = second;
    }

    public Integer findLowestCommonAncestor(int first, int second) {
        List<Integer> firstPath = findPath(first);
        List<Integer> secondPath = findPath(second);

        int smallerSize = Math.min(firstPath.size(), secondPath.size());

        int i = 0;
        for (; i < smallerSize; i++) {
            if (!firstPath.get(i).equals(secondPath.get(i))){ break; }
        }

        if (i == 0){
            return -1;
        }

        return firstPath.get(i - 1);
    }

    private List<Integer> findPath(int index) {
        List<Integer> path = new ArrayList<>();
        findNodePath(this, index, path);
        return path;
    }

    private boolean findNodePath(BinaryTree binaryTree, int index, List<Integer> path) {
        if (binaryTree == null) { return false;}
        if (binaryTree.key == index) { return true;}

        path.add(binaryTree.key);

        if (findNodePath(binaryTree.left,index,path)){ return true; }
        if (findNodePath(binaryTree.right,index,path)){ return true; }

        path.remove(Integer.valueOf(binaryTree.key));

        return false;
    }

    public List<Integer> topView() {
        Map<Integer, Pair<Integer, Integer>> offsetToValueLevel = new TreeMap<>();
        traverseTree(this, 0, 1, offsetToValueLevel);
        return offsetToValueLevel
                .values()
                .stream()
                .map(Pair::getKey)
                .collect(Collectors.toList());
    }

    private void traverseTree(BinaryTree binaryTree, int offset, int level, Map<Integer, Pair<Integer, Integer>> offsetToValueLevel) {
        if (binaryTree == null) { return; }

        Pair<Integer, Integer> current = offsetToValueLevel.get(offset);

        if (current == null || level < current.getValue()){
            offsetToValueLevel.put(offset, new Pair<>(binaryTree.key, level));
        }

        traverseTree(binaryTree.left, offset - 1, level + 1, offsetToValueLevel);
        traverseTree(binaryTree.right, offset + 1, level + 1, offsetToValueLevel);
    }
}
