import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=HibbardTest ARGS=10000
#+END_SRC

#+RESULTS:
: before
: tree1 height: 31
: tree2 height: 31
: after
: tree1 height: 22
: tree2 height: 65
: info
: log2(10000) = 13
: sqrt(10000) = 100

*/

public class HibbardTest {
    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java HibbardTest <N>");
            return;
        }
        final int N = Integer.parseInt(args[0]);

        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = i;
        }
        StdRandom.shuffle(a);

        BSTSet<Integer> tree1 = new BSTSet<>();
        BSTSet<Integer> tree2 = new BSTSet<>();
        for (int i = 0; i < N; i++) {
            tree1.add(a[i]);
            tree2.add(a[i]);
        }
        StdOut.println("before");
        StdOut.println("tree1 height: " + tree1.height());
        StdOut.println("tree2 height: " + tree2.height());

        for (int i = 0; i < N*N; i++) {
            int j = StdRandom.uniform(N);
            int k = StdRandom.uniform(N);
            try { tree1.delete(tree1.select(j)); } catch (Exception e) {}
            try { tree2.deleteBuggy(tree2.select(j)); } catch (Exception e) {}
            tree1.add(k);
            tree2.add(k);
        }
        StdOut.println("after");
        StdOut.println("tree1 height: " + tree1.height());
        StdOut.println("tree2 height: " + tree2.height());
        StdOut.println("info");
        StdOut.println("log2(" + N + ") = " + (int) (Math.log(N)/Math.log(2)));
        StdOut.println("sqrt(" + N + ") = " + (int) Math.sqrt(N));

        /*
        String file1 = "hibbard-1-" + N + ".dot";
        String file2 = "hibbard-2-" + N + ".dot";
        Out out1 = new Out(file1);
        Out out2 = new Out(file2);
        out1.println(tree1.toDot());
        out2.println(tree2.toDot());
        StdOut.println(file1 + " created");
        StdOut.println(file2 + " created");
        */
    }
}
