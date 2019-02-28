import edu.princeton.cs.algs4.*;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

/*
#+BEGIN_SRC sh :results output drawer
make run CLASS=WeightedQuickUnionPathCompressionUF < ../../data/tinyUF.txt
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
id: [6, 6, 6, 4, 4, 6, 6, 6, 4, 4]
sz: [1, 1, 3, 1, 4, 1, 6, 1, 1, 1]
digraph {
  rankdir="BT"
  0 -> 6
  1 -> 6
  2 -> 6
  3 -> 4
  5 -> 6
  7 -> 6
  8 -> 4
  9 -> 4
  {rank=same 4 6}
}

:END:

*/
public class WeightedQuickUnionPathCompressionUF implements UnionFind {
    private int[] id;
    private int[] sz;
    private int count;

    public WeightedQuickUnionPathCompressionUF(int n) {
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
        count = n;
    }

    public String toString() {
        return "id: " + Arrays.toString(id) + "\n" +
               "sz: " + Arrays.toString(sz);
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        Deque<Integer> roots = new ArrayDeque<>();
        sb.append("digraph {\n");
        sb.append("  rankdir=\"BT\"\n");
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
        throw new UnsupportedOperationException();
    }

    public void clearCost() {
        throw new UnsupportedOperationException();
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        while (p != id[p]) {
            // path compression
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else               { id[j] = i; sz[i] += sz[j]; }
        count--;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        WeightedQuickUnionPathCompressionUF uf = new WeightedQuickUnionPathCompressionUF(n);
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
