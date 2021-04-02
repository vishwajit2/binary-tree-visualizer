package binaryTree;

/**
 * BSTNode represents a single node in binary tree
 * 
 * @param <T> - data to be stored in each node
 */
public class BSTNode<T extends Comparable<T>> {
    private T data;
    private BSTNode<T> parent;
    private BSTNode<T> left;
    private BSTNode<T> right;

    // Constructor
    public BSTNode(T data) {
        this.data = data;
        parent = null;
        left = null;
        right = null;
    }

    // basic access methods
    public T getData() {
        return data;
    }

    public BSTNode<T> getParent() {
        return parent;
    }

    public BSTNode<T> getRight() {
        return right;
    }

    public BSTNode<T> getLeft() {
        return left;
    }

    // basic modification methods

    public void setData(T data) {
        this.data = data;
    }

    public void setParent(BSTNode<T> parent) {
        this.parent = parent;
    }

    public void setRight(BSTNode<T> right) {
        this.right = right;
    }

    public void setLeft(BSTNode<T> left) {
        this.left = left;
    }

    // Determine if a given pointer is pointing to real child or a wire
    public boolean isPointerWired(BSTNode<T> child) {
        if (child == null || !(this.equals(child.getParent())))
            return true;
        else
            return false;
    }

    /** String representation of the tree's node. */
    @Override
    public String toString() {
        String data = getData().toString();
        String parent = (getParent() == null) ? "NIL" : getParent().getData().toString();
        String left = (getLeft() == null) ? "NIL" : getLeft().getData().toString();
        String right = (getRight() == null) ? "NIL" : getRight().getData().toString();
        return String.format("[key: %3s | p:%3s | l:%3s | r:%3s]", data, parent, left, right);
    }

}
