import edu.princeton.cs.algs4.*;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

/*
#+BEGIN_SRC sh :results output drawer
make run CLASS=QuickUnionUF < ../../data/tinyUF.txt
#+END_SRC

#+RESULTS:
:RESULTS:
4 3
3 8
6 5
9 4
2 1
5 0
7 2
6 1
2 components
id: [1, 1, 1, 8, 3, 0, 5, 1, 8, 8]
digraph {
  rankdir=BT
  0 -> 1
  2 -> 1
  3 -> 8
  4 -> 3
  5 -> 0
  6 -> 5
  7 -> 1
  9 -> 8
  {rank=same 1 8}
}

:END:

*/

public class QuickUnionUF implements UnionFind {
    private int[] id;
    private int count;
    private int cost;

    public QuickUnionUF(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
        count = n;
    }

    public String toString() {
        return "id: " + Arrays.toString(id);
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        Deque<Integer> roots = new ArrayDeque<>();
        sb.append("digraph {\n");
        sb.append("  rankdir=BT\n");
        for (int i = 0; i < id.length; i++) {
            if (i != id[i]) {
                sb.append("  " + i + " -> " + id[i] + "\n");
            } else {
                roots.add(i);
            }
        }
        sb.append("  {rank=same"); for (int r : roots) sb.append(" " + r); sb.append("}\n");
        sb.append("}\n");
        return sb.toString();
    }

    public int cost() {
        return cost;
    }

    public void clearCost() {
        cost = 0;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        while (p != id[p]) {
            p = id[p];
            cost += 2; // two array accesses: succeeded condition and body
        }
        cost++; // one array access: unsucceeded condition
        return p;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        id[i] = j;
        cost++; // one array access
        count--;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickUnionUF uf = new QuickUnionUF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count + " components");
        StdOut.println(uf);
        StdOut.println(uf.toDot());
    }
}
