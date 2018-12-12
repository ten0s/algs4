import edu.princeton.cs.algs4.*;

// $ make run CLASS=MergeBU

class MergeBU {
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> void sort(T[] a) {
        int n = a.length;
        T[] aux = (T[]) new Comparable[a.length];
        for (int len = 1; len < n; len *= 2)
            for (int lo = 0; lo < n-len; lo += 2*len)
                merge(a, aux, lo, lo+len-1, Math.min(lo+len+len-1, n-1));
        assert isSorted(a);
    }

    private static <T extends Comparable<T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else                           a[k] = aux[j++];
        }
    }

    private static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    private static <T extends Comparable<T>> void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }

    private static <T> void show(T[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    private static <T> void show(T[] a) {
        show(a, 0, a.length-1);
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
