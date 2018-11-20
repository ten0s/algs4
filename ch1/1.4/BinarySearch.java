import edu.princeton.cs.algs4.*;
import java.util.Arrays;

// Ex 1.4.10, 1.4.11

public class BinarySearch {

    // find index of key in a sorted array
    public static int indexOf(int key, int[] a) {
        return indexOf(key, a, 0, a.length-1);
    }

    public static int indexOf(int key, int[] a, int lo, int hi) {
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    // Ex 1.4.10
    // find smallest index of key in a sorted array
    public static int smallestIndexOf(int key, int[] a) {
        return smallestIndexOf(key, a, 0, a.length-1);
    }

    public static int smallestIndexOf(int key, int[] a, int lo, int hi) {
        int mid = indexOf(key, a, lo, hi);
        if (mid == -1) {
            return -1;
        } else {
            lo = smallestIndexOf(key, a, lo, mid-1);
            if (lo == -1) {
                lo = mid;
            }
            return lo;
        }
    }

    // Ex 1.4.11
    // find largest index of key in a sorted array
    public static int largestIndexOf(int key, int[] a) {
        return largestIndexOf(key, a, 0, a.length-1);
    }

    public static int largestIndexOf(int key, int[] a, int lo, int hi) {
        int mid = indexOf(key, a, lo, hi);
        if (mid == -1) {
            return -1;
        } else {
            hi = largestIndexOf(key, a, mid+1, hi);
            if (hi == -1) {
                hi = mid;
            }
            return hi;
        }
    }

    public static void main(String[] args) {

    }
}
