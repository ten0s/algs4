public class ArrayUtil {
    public static int[] shuffle(int[] a) {
        // Knuth's shuffle O(n)
        int n = a.length;
        for (int i = 0; i < n; i++) {
            // choose uniformly [0, i]
            int r = (int) (Math.random() * (i + 1));
            int tmp = a[i];
            a[i] = a[r];
            a[r] = tmp;
        }
        return a;
    }

    public static <T> T[] shuffle(T[] a) {
        // Knuth's shuffle O(n)
        int n = a.length;
        for (int i = 0; i < n; i++) {
            // choose uniformly [0, i]
            int r = (int) (Math.random() * (i + 1));
            T tmp = a[i];
            a[i] = a[r];
            a[r] = tmp;
        }
        return a;
    }

    public static int[] copy(int[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++)
            b[i] = a[i];
        return b;
    }

    public static String toString(int[] a) {
        String s = "[";
        for (int i = 0; i < a.length; i++) {
            s += a[i];
            if (i != a.length-1) {
                s += ",";
            }
        }
        s += "]";
        return s;
    }

    public static String toString(Object[] a) {
        String s = "[";
        for (int i = 0; i < a.length; i++) {
            s += a[i];
            if (i != a.length-1) {
                s += ",";
            }
        }
        s += "]";
        return s;
    }
}
