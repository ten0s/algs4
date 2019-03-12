import edu.princeton.cs.algs4.*;

public class LazyPrimMST {
    private EdgeWeightedGraph G;
    private boolean[] marked;
    private MinPQ<Edge> pq;
    private Queue<Edge> mst;
    private double weight;

    public LazyPrimMST(EdgeWeightedGraph G) {
        this.G = G;

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

    public String toString() {
        String s = "";
        for (Edge e : mst) {
            s += e + "\n";
        }
        return s;
    }

    public String toDot() {
        HashSet<Edge> set = new HashSet<>();
        for (Edge e : this.edges()) {
            set.add(e);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("graph {\n");
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            double weight = e.weight();
            if (set.contains(e)) {
                sb.append("  " + v + " -- " + w + " [label=\"" + weight + "\", penwidth=3.0]\n");
            } else {
                sb.append("  " + v + " -- " + w + " [label=\"" + weight + "\"]\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java LazyPrimMST <file> [<text> | dot | weight]");
            return;
        }
        EdgeWeightedGraph G = new EdgeWeightedGraph(new In(args[0]));
        KruskalMST mst = new KruskalMST(G);

        if (args.length > 1 && args[1].equals("weight")) {
            StdOut.printf("%.5f\n", mst.weight());
        } else if (args.length > 1 && args[1].equals("dot")) {
            StdOut.println(mst.toDot());
        } else {
            StdOut.print(mst);
            StdOut.printf("%.5f\n", mst.weight());
        }
    }
}
