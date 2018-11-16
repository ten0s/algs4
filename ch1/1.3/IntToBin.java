import edu.princeton.cs.algs4.*;

// Exercise 1.3.5
// $ echo 50 | make run CLASS=IntToBin
// 110010

public class IntToBin {
    // convert integer to binary
    public static String toBinary(int n) {
        DynArrayStack<Integer> stack = new DynArrayStack<>();
        while (n > 0) {
            stack.push(n % 2);
            n = n / 2;
        }

        String bin = "";
        for (int b : stack) {
            bin += b;
        }

        return bin;
    }

    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            int n = StdIn.readInt();
            String bin = toBinary(n);
            StdOut.println(bin);
        }
    }
}
