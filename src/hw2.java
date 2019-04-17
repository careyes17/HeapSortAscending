
public class hw2 {

  /*
   * Definitions of the parameters
   *    1) heap: the array where the heap (sweeping window) is implemented
   *    2) newEle: the new element to insert
   *    3) pos: where to insert the new element initially.
   *            note it does not mean newEle is going to
   *            stay at pos after this function
   *    4) increment
   *    	a) true: insert newEle, that is all
   *    	b) false: insert newEle, then remove the root
   */
  static void insertAt(int[] heap, int newEle,
      int pos, boolean increment) {
    int parent, child;
    if (increment) {
      child = pos;
      parent = (child - 1) / 2;
      while (child > 0 && heap[parent] < newEle) {
        heap[child] = heap[parent];
        child = parent;
        parent = (child - 1) / 2;
      }
      heap[child] = newEle;

    } else {

      if (newEle < heap[0]) {
        heap[pos] = newEle;
        child = pos;
        parent = (child - 1) / 2;
        while (child > 0 && heap[parent] < newEle) {
          int temp = heap[child];
          heap[child] = heap[parent];
          heap[parent] = temp;
          child = parent;
          parent = (child - 1) / 2;
        }
        heap[0] = heap[pos];
        parent = 0;
        while (parent * 2 + 1 < pos) {
          int l = parent * 2 + 1;
          int r = parent * 2 + 2;
          if (heap[l] >= heap[r]) {
            if (heap[parent] < heap[l]) {
              int temp = heap[parent];
              heap[parent] = heap[l];
              heap[l] = temp;
              parent = l;
            } else {
              break;
            }
          } else {
            if (heap[parent] < heap[r]) {
              int temp = heap[parent];
              heap[parent] = heap[r];
              heap[r] = temp;
              parent = r;
            } else {
              break;
            }
          }
        }
      }
    }
  }


  /*
   * get the smallest k elements in array x in O(nlogk) time, where
   * n is the size of array x.
   *
   * It is supposed to return an array, containing the smallest k elements
   * in the increasing order.
   */
  static int[] getSmallestK(int x[], int k) {

    if (k > x.length) {
      return null;
    }

    int[] result = new int[k + 1];

    // use the first k elements in x to construct an
    // almost complete binary tree, where parent >= children
    result[0] = x[0];
    for (int i = 1; i < k; i++) {
      insertAt(result, x[i], i, true);
    }

    System.out.print("Original heap: ");
    for (int i = 0; i < k; i++) {
      System.out.print(result[i] + " ");
    }
    System.out.println();

    // for each element in the rest of array x,
    // insert it in the almost complete binary tree, and then
    // remove the root in the tree.
    for (int i = k; i < x.length; i++) {
      insertAt(result, x[i], k, false);
    }

    // now the first k elements in result are the smallest k elements in x
    System.out.print("Resulting heap: ");
    for (int i = 0; i < k; i++) {
      System.out.print(result[i] + " ");
    }
    System.out.println();

    // sort the first k elements in result in O(klogk) time

    // insert your code here

    int[] temp = new int[k];

    for (int i = k-1; i >= 0; i--) {
      temp[i] = result[0];
      result[0] = result[i];
      int parent = 0;
      while (parent * 2 + 1 < i) {
        int l = parent * 2 + 1;
        int r = parent * 2 + 2;
        if (result[l] >= result[r]) {
          if (result[parent] < result[l]) {
            int tempInt = result[parent];
            result[parent] = result[l];
            result[l] = tempInt;
            parent = l;
          } else {
            break;
          }
        } else {
          if (result[parent] < result[r]) {
            int tempInt = result[parent];
            result[parent] = result[r];
            result[r] = tempInt;
            parent = r;
          } else {
            break;
          }
        }
      }
    }

    result = temp;

    return result;

  }

  public static void main(String[] args) {
    // Test cases
    int[] data = {31, 44, 64, 5, 61,
        43, 6, 88, 59, 90,
        39, 97, 77, 62, 99,
        34, 57, 53, 60, 29};

    int i, k = 5;
    System.out.println(" k = " + k);

    int[] largestK = getSmallestK(data, k);

    System.out.print("Sorted result (smallest k elements): ");
    for (i = 0; i < k; i++) {
      System.out.print(largestK[i] + " ");
    }

    k = 8;
    System.out.println("\n k = " + k);

    largestK = getSmallestK(data, k);

    System.out.print("Sorted result (smallest k elements): ");
    for (i = 0; i < k; i++) {
      System.out.print(largestK[i] + " ");
    }
  }
}