import edu.princeton.cs.algs4.*;

// $ echo -e "one 2 3\ntwo 4 5\nthree 2 1" | make run CLASS=Ex1121

public class Ex1121 {
    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            String n = StdIn.readString();
            int i1 = StdIn.readInt();
            int i2 = StdIn.readInt();
            StdOut.printf("%s %d %d %.3f\n", n, i1, i2, 1.0*i1/i2);
        }
    }
}
