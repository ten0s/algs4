import edu.princeton.cs.algs4.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// Ex 1.1.23, 1.1.28,

// $ make run CLASS=BinarySearch ARGS=../data/tinyW.txt < ../data/tinyT.txt
// $ make run CLASS=BinarySearch ARGS="../data/tinyW.txt +" < ../data/tinyT.txt
// 50
// 99
// 13

// $ make run CLASS=BinarySearch ARGS=../data/largeW.txt < ../data/largeT.txt | wc -l
// 367966

public class BinarySearch {

    // find index of key in a sorted array
    public static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    // deduplicate a sorted array
    public static int[] dedup(int[] a) {
        if (a.length == 0) {
            return new int[0];
        }

        // deduplicate
        ArrayList<Integer> b = new ArrayList<Integer>();
        int t = a[0];
        b.add(t);
        for (int i = 1; i < a.length; i++) {
            if (a[i] != t) {
                t = a[i];
                b.add(t);
            }
        }

        // Array<Integer> -> int[]
        int[] c = new int[b.size()];
		for (int i = 0; i < b.size(); i++) {
			c[i] = b.get(i).intValue();
		}

        return c;
    }

    // number of elements that are smaller then the key in a sorted array
    public static int rank(int[] a, int key) {
        int count = 0;

        for (int i = 0; i < a.length; i++) {
            if (a[i] < key) count++;
            if (a[i] >= key) break;
        }

        return count;
    }

    // number of elements that are equal to the key in a sorted array
    public static int count(int[] a, int key) {
        int count = 0;

        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) count++;
            if (a[i] > key) break;
        }

        return count;
    }

    public static void main(String[] args) {
        // read the integers from a file
        In in = new In(args[0]);
        int[] whitelist = in.readAllInts();

        boolean shouldBeInList = false;
        if (args.length == 2 && args[1].equals("-")) {
            shouldBeInList = true;
        }

        // sort the array
        Arrays.sort(whitelist);
        int[] whitelist2 = dedup(whitelist);

        // read integer key from standard input;
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();

            boolean found = BinarySearch.indexOf(whitelist2, key) != -1;

            if ((!shouldBeInList && !found) || (shouldBeInList && found)) {
                StdOut.println(key);
            }
        }
    }
}
