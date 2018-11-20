// $ make run CLASS=Ex1412
// 2
// 3
// 4

public class Ex1412 {

    public static void main(String[] args) {
        int[] a = new int[] {1, 2, 3, 4, 5};
        int[] b = new int[] {2, 3, 4, 6, 7};

        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            int ai = a[i];
            int bj = b[j];
            if (ai == bj) {
                i++; j++;
                System.out.println(ai);
            } else if (ai < bj) {
                i++;
            } else {
                j++;
            }
        }
    }
}
