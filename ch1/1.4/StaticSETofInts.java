import java.util.Arrays;

// Ex 1.4.11

public class StaticSETofInts {
    private int[] a;

    public StaticSETofInts(int[] keys) {
        // defensive copy
        a = new int[keys.length];
        for (int i = 0; i < keys.length; i++)
            a[i] = keys[i];

        // sort the integers
        Arrays.sort(a);
    }

    public boolean contains(int key) {
        return BinarySearch.indexOf(key, a) != -1;
    }

    public int howMany(int key) {
        int lo = BinarySearch.smallestIndexOf(key, a);
        if (lo == -1) {
            return 0;
        }
        // no point to search for largest if key is NOT found
        int hi = BinarySearch.largestIndexOf(key, a);
        return hi - lo + 1;
    }
}
