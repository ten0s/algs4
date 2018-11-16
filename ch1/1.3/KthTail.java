import edu.princeton.cs.algs4.*;

// Exercise 1.3.15

// $ echo -e "1\n2\n3\n4\n5\n" | make run CLASS=KthTail ARGS=0
// 5

// $ echo -e "1\n2\n3\n4\n5\n" | make run CLASS=KthTail ARGS=1
// 4

// $ echo -e "1\n2\n3\n4\n5\n" | make run CLASS=KthTail ARGS=4
// 1

// After closer examination this implementation is not quite right.
// The exercise requires to use O(k) memory.
// This implemenation uses O(n) memory, where n is stdin size.

public class KthTail {
    // print kth from that last string on stdin
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        DynArrayQueue<String> q = new DynArrayQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            //StdOut.println("+" + s);
            q.enqueue(s);
        }

        for (int i = q.size() - k - 1; i > 0; i--) {
            String s = q.dequeue();
            //StdOut.println("-" + s);
        }

        StdOut.println(q.dequeue());
    }
}
