package binaryTree;

/**
 * @author Vishwajit Kadam ADT Interface of binary search tree operations
 * @param <T> - the data type sqaved in tree
 */

public interface BSTInterface<T extends Comparable<T>> {
    public BSTNode<T> getRoot();

    public void setRoot(BSTNode<T> newRoot);

    public BSTNode<T> insert(T data);

    public BSTNode<T> delete(BSTNode<T> node);

    public BSTNode<T> search(BSTNode<T> node, T data);

    public BSTNode<T> getSuccessor(BSTNode<T> x);

    public BSTNode<T> getPredecessor(BSTNode<T> x);

    public BSTNode<T> getMinimum(BSTNode<T> node);

    public BSTNode<T> getMaximum(BSTNode<T> node);

    public String inorderTraversal(BSTNode<T> node);

    public String preorderTraversal(BSTNode<T> node);

    public String postorderTraversal(BSTNode<T> node);

}