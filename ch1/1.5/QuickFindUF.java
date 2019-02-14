import edu.princeton.cs.algs4.*;
import java.util.Arrays;

/*
$ make run CLASS=QuickFindUF < ../../data/tinyUF.txt
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

public class QuickFindUF implements UnionFind {
    private int[] id;
    private int count;
    private int cost;

    public QuickFindUF(int n) {
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
        cost++; // one array access
        return id[p];
    }

    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);
        if (pId == qId) return;

        int n = id.length;
        for (int i = 0; i < n; i++) {
            if (id[i] == pId) {
                id[i] = qId;
                cost++; // one array access
            }
            cost += n; // n condition array accesses
        }
        count--;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickFindUF uf = new QuickFindUF(n);
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
