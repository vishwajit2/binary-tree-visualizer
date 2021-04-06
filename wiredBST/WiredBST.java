package wiredBST;

import binaryTree.BSTInterface;
import binaryTree.BSTNode;

import javax.management.openmbean.KeyAlreadyExistsException;

public class WiredBST<T extends Comparable<T>> implements BSTInterface<T> {
    // Instance variables
    private BSTNode<T> root;
    private BSTNode<T> median;
    private int elementsSmallerThanMedian;
    private int elementsLargerThanMedian;

    // default Constructor : Construct an empty wired BST(binary search tree).

    public WiredBST() {
        root = null;
        median = null;
        elementsSmallerThanMedian = 0;
        elementsLargerThanMedian = 0;
    }

    /**
     * Construct a wired BST rooted at given node.
     * 
     * @param node - the root of the new tree.
     */
    public WiredBST(BSTNode<T> node) {
        root = node;
        median = node;
        elementsSmallerThanMedian = 0;
        elementsLargerThanMedian = 0;
    }

    public BSTNode<T> getRoot() {
        return root;
    }

    public void setRoot(BSTNode<T> newRoot) {
        this.root = newRoot;
    }

    // insert new element in array
    @Override
    public BSTNode<T> insert(T newElement) {
        // Initialization:
        BSTNode<T> x; // utility pointer to find appropriate position for new node z
        BSTNode<T> z; // the new node for the new Elements data argument

        x = root;
        z = new BSTNode<T>(newElement); // create node for the new dataElement

        // First we find suitable place on tree to place new node z
        while (x != null) {
            // navigate right if new key is larger & right child is not wired:
            if ((z.getData().compareTo(x.getData()) == 0))
                throw new KeyAlreadyExistsException(String.format("Duplicate key for element <%s>", z.getData()));

            // navigate left if new key is smaller & left child is not wired:
            else if ((z.getData().compareTo(x.getData()) < 0) && !(x.isPointerWired(x.getLeft())))
                x = x.getLeft();

            // navigate right if new key is larger & right child is not wired:
            else if ((z.getData().compareTo(x.getData()) > 0) && !(x.isPointerWired(x.getRight())))
                x = x.getRight();

            // end navigation if next node is either wired or NIL
            else
                break;
        }

        // Now we connect & wire z to it's place:

        // case 1: tree was empty, set new node to be it's root
        if (x == null)
            this.setRoot(z);

        // case 2: z should be a left child of x:
        else if ((z.getData().compareTo(x.getData()) < 0)) {
            z.setParent(x);
            z.setLeft(x.getLeft());
            z.setRight(x);
            x.setLeft(z);
        }

        else // case 3: z should be a right child of x:
        {
            z.setParent(x);
            z.setLeft(x);
            z.setRight(x.getRight());
            x.setRight(z);
        }

        if (median == null) // tree was empty, set the new node z to be the median
            median = z;
        // compare new key to median key and update the appropriate counter accordingly:
        else if (z.getData().compareTo(median.getData()) < 0)
            this.elementsSmallerThanMedian++;
        else if (z.getData().compareTo(median.getData()) > 0)
            this.elementsLargerThanMedian++;
        // update the median if necessary according to the counters updated state:
        updateMedian();

        return z; // return the new node inserted
    }

    /**
     * returns median node in the tree which has the (lower)median key(data) in the
     * collection of all elements in tree, or NIL if tree is empty.
     */

    public BSTNode<T> getMedian() {
        return median;
    }

    /**
     * Maintain median during insertion and deletion of elements, by keeping track
     * on how much elements or lager than current median, how much lower, and making
     * sure it is balanced towards the lower median.
     */
    private void updateMedian() {
        // case 1: counters are equally balanced, no need for update -
        if (elementsSmallerThanMedian == elementsLargerThanMedian)
            return;

        // case 2: unbalanced from above by more than 1 - make median's successor new
        // median:
        if (elementsLargerThanMedian - elementsSmallerThanMedian > 1) {// set the median's successor to be the new
                                                                       // median:
            median = getSuccessor(median); // this is because our median is lower median
            // update counters according to new median:
            elementsSmallerThanMedian++;
            elementsLargerThanMedian--;
        }

        // case 3: unbalanced from bellow - - make median's predecessor new median:
        else if (elementsSmallerThanMedian > elementsLargerThanMedian) {
            // set the median's predecessor to be the new // median:
            median = getPredecessor(median); // this is because our median is lower median
            // update counters according to new median:
            elementsSmallerThanMedian--;
            elementsLargerThanMedian++;
        }
    }

    /**
     * getSuccessor
     * 
     * @param node whose successor is to be found.
     * @return reference to node which is the in-order successor of the input node.
     */
    @Override
    public BSTNode<T> getSuccessor(BSTNode<T> node) {
        // if given node is NIL or maximum node in tree, just return NIL:
        if (node == null || node.getRight() == null)
            return null;

        // if right child is wired, then it's the successor - just return it in O(1):
        if (node.isPointerWired(node.getRight()))
            return node.getRight();

        // if right child is "real", return minimum of right sub-tree:
        node = node.getRight();
        while (!(node.isPointerWired(node.getLeft())))
            node = node.getLeft();
        return node;
    }

    /**
     * getPredecessor
     * 
     * @param node whose predecessor is to be found.
     * @return reference to node which is the in-order predecessor of the input
     *         node.
     */
    @Override
    public BSTNode<T> getPredecessor(BSTNode<T> node) {
        // if given node is NIL or minimum node in tree, just return NIL:
        if (node == null || node.getLeft() == null)
            return null;

        // if left child is wired, then it's the predecessor - just return it:
        if (node.isPointerWired(node.getLeft()))
            return node.getLeft();

        // if left child is "real", return maximum of left sub-tree:
        node = node.getLeft();
        while (!(node.isPointerWired(node.getRight())))
            node = node.getRight();
        return node;
    }

    /**
     * getMinimum:
     * 
     * @param node of sub-tree in which we're interested in finding it's minimum.
     * @return reference to node with local minimum key in sub-tree, or NIL if
     *         sub-tree is empty.
     */
    @Override
    public BSTNode<T> getMinimum(BSTNode<T> node) {
        // if sub-tree is empty, just return NIL
        if (node == null)
            return null;

        // follow left path until NIL or a left wire is reached:
        while (!node.isPointerWired(node.getLeft()))
            node = node.getLeft();

        // return node (if left is NIL or a wire, it's the local minimum of the given
        // sub-tree):
        return node;
    }

    /**
     * getMaximum:
     * 
     * @param node of sub-tree in which we're interested in finding it's maximum.
     * @return reference to node with local maximum key in sub-tree, or NIL if
     *         sub-tree is empty.
     */
    @Override
    public BSTNode<T> getMaximum(BSTNode<T> node) {
        // if sub-tree is empty, just return NIL
        if (node == null)
            return null;

        // follow right path until NIL or a right wire is reached:
        while (!node.isPointerWired(node.getRight()))
            node = node.getRight();

        // return node (if right is NIL or a wire, it's the local maximum of the given
        // sub-tree):
        return node;
    }

    /**
     * Search:
     * 
     * @param x - the root node of the tree.
     * @param k - the data (key) to search for.
     * @return reference to node if k was found, or NIL otherwise.
     */
    @Override
    public BSTNode<T> search(BSTNode<T> x, T k) {
        while (x != null) { // check the order relationship between x data & k:
            int comparisonResult = x.getData().compareTo(k);

            // if keys are equal (we found k) - just return result:
            if (comparisonResult == 0)
                return x;

            // if k > x.key, continue searching on right sub-tree:
            else if ((comparisonResult < 0) && !(x.isPointerWired(x.getRight())))
                x = x.getRight();

            // if k < x.key, continue searching on left sub-tree:
            else if ((comparisonResult > 0) && !(x.isPointerWired(x.getLeft())))
                x = x.getLeft();
            else
                return null;
        }
        // if k isn't found (or if the tree is empty) return NIL:
        return null;
    }

    /**
     * Delete (pointer version) - delete & return given node.
     * 
     * @param z node to be deleted.
     * @return z node which was deleted, or null if the tree is empty
     */
    @Override
    public BSTNode<T> delete(BSTNode<T> z) {
        // if the given node for deletion is null, do nothing:
        if (z == null)
            return null;

        // Initialize utility pointers for forward processing:
        BSTNode<T> zParent = z.getParent();
        BSTNode<T> zLeft = z.getLeft();
        BSTNode<T> zRight = z.getRight();
        BSTNode<T> zSuccessor = this.getSuccessor(z);
        BSTNode<T> zPredecessor = this.getPredecessor(z);

        // Handle case 1: z has two "real" children (NOT wires):
        if (!(z.isPointerWired(zLeft)) && !(z.isPointerWired(zRight))) {
            if ((zRight != zSuccessor) && (!zSuccessor.isPointerWired(zSuccessor.getRight()))) {
                zSuccessor.getRight().setParent(zSuccessor.getParent());
                zSuccessor.getParent().setLeft(zSuccessor.getRight());
            }

            zSuccessor.setLeft(z.getLeft());
            zSuccessor.getLeft().setParent(zSuccessor);

            if (zSuccessor != zRight) {
                zSuccessor.setRight(zRight);
                zRight.setParent(zSuccessor);
            }

            zSuccessor.setParent(zParent);
            if (zSuccessor.getParent() == null)
                setRoot(zSuccessor);
            else if (z == zSuccessor.getParent().getLeft())
                zSuccessor.getParent().setLeft(zSuccessor);
            else
                zSuccessor.getParent().setRight(zSuccessor);

            zPredecessor.setRight(zSuccessor);
        }

        // Handle case 2: z has a child on right, and a wire on left
        else if (!z.isPointerWired(zRight) && z.isPointerWired(zLeft)) {// we want to replace z with it's right child:

            // set z'ds parent to be the parent of z'ds child:
            zRight.setParent(zParent);

            // if z was the root of the tree then make it's child the new root:
            if (zParent == null)
                this.setRoot(zRight);

            // if z was a left child, set it's parent left pointer to z'ds child:
            else if (z == zParent.getLeft())
                zParent.setLeft(zRight);

            // Similarly, if z was a right child, set it's parent right pointer to z'ds
            // child:
            else
                zParent.setRight(zRight);

            // set z'ds successor left pointer point to z'ds predecessor:
            zSuccessor.setLeft(zLeft);
        }

        // Case 3: z has a child on left & a wire on right:
        else if (!z.isPointerWired(zLeft) && z.isPointerWired(zRight)) {// we want to replace z with it's left child

            // set z'ds parent to be the parent of z'ds child:
            zLeft.setParent(zParent);

            // if z was the root of the tree then make it's child the new root:
            if (zParent == null)
                this.setRoot(zLeft);

            // if z was a left child, set it's parent left pointer to z'ds child:
            else if (z == zParent.getLeft())
                zParent.setLeft(zLeft);

            // Similarly, if z was a right child, set it's parent right pointer to z'ds
            // child:
            else
                zParent.setRight(zLeft);

            // set z'ds predecessors right pointer point to z'ds successor:
            zPredecessor.setRight(zRight);
        }

        // Handle case 4: z has two "leaves" i.e, both left & right pointers are wires
        else if (z.isPointerWired(zLeft) && (z.isPointerWired(zRight))) {
            // if z is the root then make the root NIL
            if (zParent == null)
                this.setRoot(null);

            // if z is a left child, set it's parent left pointer to z'ds predecessor:
            else if (z == zParent.getLeft())
                zParent.setLeft(zPredecessor);

            // if z is a right child, set it's parent right pointer to z'ds successor:
            else
                zParent.setRight(zSuccessor);
        }

        /* median maintenance */
        // if we just deleted the median, set a new one according to new balance:
        if (z == median) {
            if (elementsLargerThanMedian == elementsSmallerThanMedian) {
                median = zPredecessor;
                if (median != null)
                    elementsSmallerThanMedian--;
            } else if (elementsLargerThanMedian > elementsSmallerThanMedian) {
                median = zSuccessor;
                if (median != null)
                    elementsLargerThanMedian--;
            }
        }

        else // we didn't delete median, update new balance and update median if necessary:
        {// compare new key to median key and update the appropriate counter accordingly:
            if (z.getData().compareTo(median.getData()) < 0)
                elementsSmallerThanMedian--;
            else if (z.getData().compareTo(median.getData()) > 0)
                elementsLargerThanMedian--;
            // update the median if necessary according to the counters updated state:
            updateMedian();
        }

        return z; // return node which was deleted
    }

    /**
     * Delete (key version) - delete & return node which contains the given data.
     * This method is an overloaded version of Delete, enabling applications to
     * invoke deletion directly by a given key, without the need to search for it's
     * node explicit reference pointer.
     * 
     * @param k key(data) to be deleted.
     * @return z node which was deleted, or null if k does not exist in tree.
     */

    public BSTNode<T> delete(T k) {
        // search for a node containing the key k:
        BSTNode<T> z = search(this.getRoot(), k);

        // invoke the pointer version deletion method with the node which was found:
        return delete(z);
    }
}
