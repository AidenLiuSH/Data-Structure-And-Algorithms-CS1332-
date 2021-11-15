import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.*;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 * @userid YOUR USER ID HERE sliu733
 * @GTID YOUR GT ID HERE 903631324
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int j = i + 1;
            while (j != 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }




    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException(
                    "Comparator is null");
        }
        int i = 1;
        while (i < arr.length) {
            int j = i;
            T temp = arr[i];
            while (j > 0) {
                int k = comparator.compare(temp, arr[j - 1]);
                if (k < 0) {
                    arr[j] = arr[j - 1];
                } else {
                    break;
                }
                j--;
            }
            arr[j] = temp;
            i++;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        if (arr.length <= 1) {
            return;
        }
        int leftLen = arr.length / 2;
        int rightLen = arr.length - leftLen;
        T[] leftArr = (T[]) (new Object[leftLen]);
        T[] rightArr = (T[]) (new Object[rightLen]);
        for (int i = 0; i < leftLen; i++) {
            leftArr[i] = arr[i];
        }
        for (int i = 0; i < rightLen; i++) {
            rightArr[i] = arr[i + leftLen];
        }
        mergeSort(leftArr, comparator);
        mergeSort(rightArr, comparator);
        mergeSortH(leftArr, rightArr, arr, comparator);
    }

    /**
     * MergeSort hepler method
     * @param left Left arr
     * @param right Right arr
     * @param arr Array
     * @param comparator Comparator
     * @param <T> data type
     */
    private static <T> void mergeSortH(T[] left, T[] right, T[] arr, Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) > 0) {
                arr[i + j] = right[j];
                j++;
            } else {
                arr[i + j] = left[i];
                i++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        ArrayList<Integer>[] buckets = new ArrayList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new ArrayList<>();
        }
        int max = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }
        int j = 0;
        while (max > 0) {
            max /= 10;
            j++;
        }
        for (int i = 0; i < j; i++) {
            for (int value : arr) {
                int curr = value;
                for (int k = 0; k < i; k++) {
                    curr /= 10;
                }
                curr %= 10;
                buckets[curr + 9].add(value);
            }
            int n = 0;
            for (ArrayList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[n] = bucket.get(0);
                    n++;
                    bucket.remove(0);
                }
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException(
                    "Comparator is null");
        } else if (rand == null) {
            throw new IllegalArgumentException(
                    "Random is null");
        }
        quickSortH(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * QuickSort Helper
     *
     * @param <T> data type
     * @param arr array to sort
     * @param left the left index
     * @param right right index
     * @param comparator Comparator
     * @param rand random object for pivots
     */
    private static <T> void quickSortH(T[] arr, int left, int right,
                                   Comparator<T> comparator, Random rand) {
        if (left >= right) {
            return;
        }
        int rightIndex = right;
        int leftIndex = left + 1;
        int pivot = rand.nextInt(right - left + 1) + left;
        T p = arr[pivot];
        arr[pivot] = arr[left];
        arr[left] = p;
        while (leftIndex <= rightIndex) {
            while (leftIndex <= rightIndex) {
                if (comparator.compare(arr[leftIndex], p) <= 0) {
                    leftIndex++;
                } else {
                    break;
                }
            }
            while (leftIndex < rightIndex) {
                if (comparator.compare(arr[rightIndex], p) >= 0) {
                    rightIndex--;
                } else {
                    break;
                }
            }
            if (leftIndex == rightIndex) {
                rightIndex--;
            }
            if (leftIndex < rightIndex) {
                T temp = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = temp;
                leftIndex++;
                rightIndex--;
            }
        }
        T temp = arr[rightIndex];
        arr[rightIndex] = arr[left];
        arr[left] = temp;
        quickSortH(arr, left, rightIndex - 1, comparator, rand);
        quickSortH(arr, rightIndex + 1, right, comparator, rand);
    }



    public static <T> void selectionSort(T[] arr) {
        int i = 0;
        int j = 0;
        T minVal = null;
        int minIndex = 0;
        T temp;
        for (i = 0; i < arr.length - 1; i++) {
            minVal = arr[i];
            minIndex = i;
            for (j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(minVal) < 0) {
                    minVal = arr[j];
                    minIndex = j;
                }
            }
            if (minVal.compareTo(arr[i])) {
                temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
}
