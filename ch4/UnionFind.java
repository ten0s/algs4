import java.util.List;
import java.util.ArrayList;

public class UnionFind {
    private int[] parent;
    private int[] size;
    private int count;

    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        count = n;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        // https://en.wikipedia.org/wiki/Disjoint-set_data_structure#Path_compression
        // Path compression (every node on the path points to the root)
        if (p != parent[p]) {
            parent[p] = find(parent[p]);
            p = parent[p];
        }
        /*
        // Path halving (every other node on the path points to its grandparent)
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        */
        return p;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        // always connect smaller tree to larger
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    // size of p's component
    public int size(int p) {
        return size[find(p)];
    }
}
