import edu.princeton.cs.algs4.*;

// $ make run CLASS=Ex119 ARGS=13

// Put the binary representation of a positive integer to a string
public class Ex119 {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        String s = "";
        for (int k = n; k > 0; k /= 2) {
            s = (k % 2) + s;
        }
        StdOut.println(s);
    }
}
