import edu.princeton.cs.algs4.*;

// $ make run CLASS=LazyPrimMST ARGS="../data/tinyEWG.txt"
// 0-7 0.16000
// 2-3 0.17000
// 1-7 0.19000
// 0-2 0.26000
// 5-7 0.28000
// 4-5 0.35000
// 6-2 0.40000
// 1.81000

// $ make run CLASS=LazyPrimMST ARGS="../data/largeEWG.txt weight"
// 647.66307

public class LazyPrimMST {
    private boolean[] marked;
    private MinPQ<Edge> pq;
    private Queue<Edge> mst;
    private double weight;

    public LazyPrimMST(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        pq = new MinPQ<>();
        mst = new Queue<>();

        visit(G, 0);
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.enqueue(e);
            weight += e.weight();
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (!marked[e.other(v)]) {
                pq.insert(e);
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java LazyPrimMST <file> [<edges> | weight]");
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
