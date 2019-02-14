import edu.princeton.cs.algs4.*;
import java.util.Arrays;

/*
$ make run CLASS=QuickUnionUF < ../../data/tinyUF.txt
4 3
3 8
6 5
9 4
2 1
5 0
7 2
6 1
2 components
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
    }
}
