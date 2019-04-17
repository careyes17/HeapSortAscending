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
    if (increment) { // if a new element is to be inserted
      child = pos; // adds child location to the end
      parent = (child - 1) / 2; // finds parent of child
      while (child > 0
          && heap[parent] < newEle) { // while bubbling up is possible and child isn't 0 or less
        heap[child] = heap[parent]; // child takes parent's value
        child = parent; // child goes to the parent's level
        parent = (child - 1) / 2; // parent finds new children
      }
      heap[child] = newEle;

    } else { // if a heap must be altered

      if (newEle < heap[0]) { // only access if the next value is larger than the max
        heap[pos] = newEle; // adds new value to the end of the array
        child = pos; // sets child location in array
        parent = (child - 1) / 2; // finds parent of child
        while (child > 0
            && heap[parent] < newEle) { // bubble up the newly inserted value as much as possible
          heap = swap(heap, parent, child);
          child = parent;
          parent = (child - 1) / 2;
        }
        heap[0] = heap[pos]; // putting pos value in root, no need to swap values
        parent = 0; // setting bubble down root
        while (parent * 2 + 1 < pos) { // bubble down as long as the bottom isn't hit
          int l = parent * 2 + 1; // left "node"
          int r = parent * 2 + 2; // right "node"
          if (heap[l]
              >= heap[r]) { // if left node is >= right, swap values of left and parent and make left the new parent node
            heap = swap(heap, parent, l);
            parent = l; // parent now becomes left "node"
          } else { // if right node is > left, swap values of right and parent and make right the new parent node
            heap = swap(heap, parent, r);
            parent = r; // parent now becomes right "node"
          }
        }
      }
    }
  }

  // swaps parent and child "node" values and returns the resulting array to be reassigned
  private static int[] swap(int[] heap, int parent, int child) {
    if (heap[parent] < heap[child]) { // if the child value is greater than parent value, swap
      int temp = heap[parent];
      heap[parent] = heap[child];
      heap[child] = temp;
    }
    return heap;
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

    // for k, bubble down logk times
    for (int i = k - 1; i >= 0; i--) {
      result = swap(result, i, 0); // swaps root to the back of the array
      int parent = 0;
      while (parent * 2 + 1 < i - 1) { // for current k size of array, bubble down
        int l = parent * 2 + 1; // left "node"
        int r = parent * 2 + 2; // right "node"
        if (result[l] >= result[r]) { // if left "node" is >= right "node"
          result = swap(result, parent, l); // swap values of parent and child if possible
          parent = l; // left "node" is new parent
        } else { // if right node is > left, swap values of right and parent and make right the new parent node
          result = swap(result, parent, r);
          parent = r;
        }
      }
    }

    if (result[0] > result[1]) {
      result = swap(result, 1, 0); // swaps top 2 values of heap if not in order
    }

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