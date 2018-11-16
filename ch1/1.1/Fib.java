import edu.princeton.cs.algs4.*;

// Ex 1.1.19
// $ make run CLASS=Ex1119

public class Fib
{
    public static long fib(int n)
    {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }

    public static long ffib(int n) {
        if (n == 0) return 0;

        long[] res = {0, 1};
        for (int i = 1; i <= n; i++) {
            long sum = res[0] + res[1];
            res[0] = res[1];
            res[1] = sum;
        }

        return res[1];
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 90; n++) {
            StdOut.println(n + " " + ffib(n));
        }
    }
}
