import edu.princeton.cs.algs4.*;

// $ make run CLASS=KruskalMST ARGS="../data/tinyEWG.txt"
// 0-7 0.16000
// 2-3 0.17000
// 1-7 0.19000
// 0-2 0.26000
// 5-7 0.28000
// 4-5 0.35000
// 6-2 0.40000
// 1.81000

// $ make run CLASS=KruskalMST ARGS="../data/largeEWG.txt weight"
// Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

public class KruskalMST {
    private Queue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<>();
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }
        UnionFind uf = new UnionFind(G.V());

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.enqueue(e);
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : mst) {
            weight += e.weight();
        }
        return weight;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java KruskalMST <file> [<edges> | weight]");
            return;
        }
        EdgeWeightedGraph G = new EdgeWeightedGraph(new In(args[0]));
        KruskalMST mst = new KruskalMST(G);

        if (args.length > 1 && args[1].equals("weight")) {
            StdOut.printf("%.5f\n", mst.weight());
        } else {
            for (Edge e : mst.edges()) {
                StdOut.println(e);
            }
            StdOut.printf("%.5f\n", mst.weight());
        }
    }
}
