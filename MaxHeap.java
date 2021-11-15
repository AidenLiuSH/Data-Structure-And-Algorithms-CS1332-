import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Shihui Liu
 * @version 1.0
 * @userid sliu733
 * @GTID 903631324
 *
 * Collaborators: The only two braincells I have left.
 *
 * Resources: lecture slides
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("ArrayList is null");
        }

        backingArray = (T[]) new Comparable[2 * data.size() + 1];

        size = data.size();
        for (int i = 1; i <= size; i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("Data is null.");
            }
            backingArray[i] = data.get(i - 1);
        }

        for (int index = size / 2; index >= 1; index--) {
            buildHeap(index);
        }
    }

    /**
     * recursive helper that creates a heap in O(n) time.
     * @param index root index for down heap.
     */
    private void buildHeap(int index) {
        int max = index;
        int leftIndex = 2 * index;
        int rightIndex = 2 * index + 1;

        if (leftIndex <= size) {
            if (backingArray[leftIndex].compareTo(backingArray[index]) > 0) {
                max = leftIndex;
            } else {
                max = index;
            }
        }

        if (rightIndex <= size && backingArray[rightIndex].compareTo(backingArray[max]) > 0) {
            max = rightIndex;
        }

        if (max != index) {
            T temp = backingArray[max];
            backingArray[max] = backingArray[index];
            backingArray[index] = temp;
            buildHeap(max);
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }

        if (size + 1 == backingArray.length) {
            resize(backingArray.length);
        }

        backingArray[size + 1] = data;
        int index = size + 1;
        int newIndex = index / 2;
        while (newIndex >= 1 && backingArray[index].compareTo(backingArray[newIndex]) > 0) {
            T temp = backingArray[index];
            backingArray[index] = backingArray[newIndex];
            backingArray[newIndex] = temp;
            index = newIndex;
            newIndex = newIndex / 2;
        }

        size++;
    }

    /**
     * resize helper method. Copy the original data to a double sized backing array.
     * @param size input size.
     */
    private void resize(int size) {
        T[] temp = backingArray;
        backingArray = (T[]) new Comparable[2 * size];
        for (int i = 1; i < size; i++) {
            backingArray[i] = temp[i];
        }
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }

        T removedData = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;

        for (int temp = 1; temp <= size; temp++) {
            buildHeap(temp);
        }
        return removedData;
    }


    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
