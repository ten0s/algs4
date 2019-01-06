public class Histogram {
    public static void print(int[] a) {
        print(a, 20);
    }

    public static void print(int[] a, int height) {
        int max = a[0];
        int min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < min) min = a[i];
            if (max < a[i]) max = a[i];
        }
        // min is a lower bound
        for (int i = 0; i < a.length; i++) {
            a[i] -= min - 1;
        }
        max -= min - 1;
        min = 1;
        int diff = Math.abs(max - min);
        double koef = 1.0 * height / diff;
        for (int row = (int) (diff * koef); row >= 0; row--) {
            for (int col = 0; col < a.length; col++) {
                if (a[col] * koef - row > 0)
                    System.out.print("x");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}
