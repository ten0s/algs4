import edu.princeton.cs.algs4.*;
import java.lang.Math.*;

// $ make run CLASS=Ex1114 ARGS=255

// takes int n and returns the largest int not larger than log2(n)
public class Ex1114 {
    public static int lg(int n) {
        int m = 0;
        int p = 1;
        for (;;) {
            p *= 2;
            if (p <= n) m++;
            else break;
        }
        return m;
    }

    public static int lg2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = lg(n);
        int m2 = lg2(n);
        StdOut.println(m + " " + m2);
    }
}
