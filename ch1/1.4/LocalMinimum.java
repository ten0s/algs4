import edu.princeton.cs.algs4.*;

public class LocalMinimum {
    private LocalMinimum() {}

/*
http://flexaired.blogspot.com/2013/02/local-minimum-of-array.html
https://www.geeksforgeeks.org/find-local-minima-array/

Solution:
If a[0] < a[1] or a[N-2] > a[N-1] then a[0] or a[N-1] is LM, respectively.

Otherwise, pick a[mid] where mid = N/2. If it's not the answer, we have three cases:
1)  If a[mid-1] < a[mid] < a[mid+1], lower half will contain an LM.
2)  If a[mid-1] > a[mid] > a[mid+1], upper half will contain an LM.
3)  If a[mid-1] < a[mid] > a[mid+1], either half will contain an LM.

Search on the new interval recursively.

Explanation:
If a[1] < a[2], a[1] is an LM. Otherwise, if a[n-1] > a[n], a[n] is an LM.
Otherwise, the array entries start out descending (going from a[1] to a[2]) and
ends up ascending (going from a[n-1] to a[n]). It follows that an LM must exist
somewhere in between.  Examine a[n/2].  If a[n/2] > a[n/2+1], then by the same
reasoning there is an LM in the upper half of the array.  Similarly,
if a[n/2] < a[n/2+1] there is an LM in the lower half of the array.
Solve recursively in the appropriate half.
*/

    public static int indexOf(int[] a) {
        return indexOf(a, 0, a.length-1);
    }

    private static int indexOf(int[] a, int start, int end) {
        if (start > end) {
            return -1;
        }

        if (start == end) {
            return start;
        }

        if (end - start == 1) {
            int diff = a[start] - a[end];
            if (diff < 0) {
                return start;
            } else if (diff > 0) {
                return end;
            } else {
                // Some not distinct values
                throw new IllegalArgumentException();
            }
        }

        int mid = (start + end) / 2;
        if (a[mid-1] > a[mid] && a[mid] < a[mid+1]) {
            return mid;
        } else if (a[mid-1] < a[mid]) {
            return indexOf(a, start, mid-1);
        } else {
            return indexOf(a, mid+1, end);
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int[] a = in.readAllInts();

        int idx = indexOf(a);
        StdOut.println(idx);
    }
}
