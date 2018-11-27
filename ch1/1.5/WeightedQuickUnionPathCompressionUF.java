import edu.princeton.cs.algs4.*;
import java.util.Arrays;

// $ make run CLASS=WeightedQuickUnionPathCompressionUF < ../../data/tinyUF.txt
// 4 3
// 3 8
// 6 5
// 9 4
// 2 1
// 5 0
// 7 2
// 6 1
// 2 components

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
    }
}
