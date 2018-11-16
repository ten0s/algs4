import edu.princeton.cs.algs4.*;

// $ make run CLASS=Josephus ARGS="2 7"

public class Josephus {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);

        // put people into queue
        Queue<Integer> q = new Queue<>();
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }

        int i = 1;
        // until one person is left
        while (q.size() != 1) {
            int p = q.dequeue();
            if (i == m) {
                // eliminate
                StdOut.print(p + " ");
            } else {
                // put back into queue
                q.enqueue(p);
            }
            if (++i > m) {
                i = 1;
            }
        }
        int p = q.dequeue();
        StdOut.println(p);
    }
}
