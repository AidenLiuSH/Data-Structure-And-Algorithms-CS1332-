import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Shihui Liu
 * @version 1.0
 * @userid sliu733
 * @GTID 903631324
 *
 * Collaborators:
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        size = 0;
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }

        root = addDataHelper(root, data);
        size++;

    }

    /**
     * Recursive add helper that traverse through the BST.
     * @param node root node for the helper
     * @param data data to add to the tree
     * @return BSTNode after operation
     */
    private BSTNode<T> addDataHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return new BSTNode<T>(data);
        }
        if (node.getData().compareTo(data) == 0) {
            remove(data);
            return node;
        }
        if (node.getData().compareTo(data) < 0) {
            node.setRight(addDataHelper(node.getRight(), data));
        } else {
            node.setLeft(addDataHelper(node.getLeft(), data));
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        BSTNode<T> temp = new BSTNode<>(null);
        root = removeDataHelper(temp, root, data);
        return temp.getData();
    }

    /**
     * Recursive remove helper that traverse through the BST.
     * @param dummyNode dummy node that stores value.
     * @param node root of the binary tree.
     * @param data data that needs to be removed.
     * @return removed node.
     */
    private BSTNode<T> removeDataHelper(BSTNode<T> dummyNode, BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("data is null");
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(removeDataHelper(dummyNode, node.getRight(), data));
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(removeDataHelper(dummyNode, node.getLeft(), data));
        } else {
            size--;
            dummyNode.setData(node.getData());
            if (node.getRight() == null && node.getLeft() == null) {
                return null;
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else {
                BSTNode<T> newDummy = new BSTNode<>(null);
                node.setRight(removeNextHelper(node.getRight(), newDummy));
                node.setData(newDummy.getData());
            }
        }
        return node;
    }

    /**
     * recursive helper that traverse through the BST to remove the next node.
     * @param node root
     * @param dummy dummy node that stores data
     * @return next node
     */
    private BSTNode<T> removeNextHelper(BSTNode<T> node, BSTNode<T> dummy) {
        if (node.getLeft() == null) {
            dummy.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(removeNextHelper(node.getLeft(), dummy));
        }
        return node;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        return getHelper(root, data);
    }

    /**
     * Recursive get helper that traverse through the BST.
     * @param node root node.
     * @param data data fetching.
     * @return the fetched data.
     */
    private T getHelper(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        } else {
            if (node.getData().compareTo(data) < 0) {
                return getHelper(node.getRight(), data);
            } else if (node.getData().compareTo(data) > 0) {
                return getHelper(node.getLeft(), data);
            } else {
                return node.getData();
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        try {
            get(data);
        } catch (NoSuchElementException dataNotFound) {
            return false;
        }
        return true;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        list = (ArrayList<T>) preorderHelper(list, root);
        return list;
    }

    /**
     * recursive helper for preorder.
     * @param preorderList list of pre order values.
     * @param node root.
     * @return new list.
     */
    private List<T> preorderHelper(List<T> preorderList, BSTNode<T> node) {
        if (node == null) {
            return preorderList;
        } else {
            preorderList.add(node.getData());
            preorderHelper(preorderList, node.getLeft());
            preorderHelper(preorderList, node.getRight());
        }
        return preorderList;
    }



    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        list = (ArrayList<T>) inorderHelper(list, root);
        return list;
    }

    /**
     * Recursive helper for in-order.
     * @param inorderList list of in order values.
     * @param node root.
     * @return new list of values.
     */
    private List<T> inorderHelper(List<T> inorderList, BSTNode<T> node) {
        if (node == null) {
            return inorderList;
        } else {
            inorderHelper(inorderList, node.getLeft());
            inorderList.add(node.getData());
            inorderHelper(inorderList, node.getRight());
        }
        return inorderList;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        list = (ArrayList<T>) postOrderHelper(list, root);
        return list;
    }

    /**
     * recursive helper for post order.
     * @param postorderList list of post order values.
     * @param node root.
     * @return new list of values.
     */
    private List<T> postOrderHelper(List<T> postorderList, BSTNode<T> node) {
        if (node == null) {
            return postorderList;
        } else {
            postOrderHelper(postorderList, node.getLeft());
            postOrderHelper(postorderList, node.getRight());
            postorderList.add(node.getData());
        }
        return postorderList;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        LinkedList<T> data = new LinkedList<>();
        LinkedList<BSTNode<T>> list = new LinkedList<BSTNode<T>>();
        list.add(root);
        while (!list.isEmpty()) {
            BSTNode<T> node = list.removeFirst();
            if (node != null) {
                data.add(node.getData());
                list.add(node.getLeft());
                list.add(node.getRight());
            }
        }
        return data;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Recursive helper for height.
     * @param node root.
     * @return height of node.
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            int rightIndex = heightHelper(node.getRight());
            int leftIndex = heightHelper(node.getLeft());
            if (leftIndex > rightIndex) {
                return leftIndex + 1;
            } else {
                return rightIndex + 1;
            }
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
