import edu.princeton.cs.algs4.*;

public class KruskalMST {
    private EdgeWeightedGraph G;
    private Queue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        this.G = G;

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
            StdOut.println("usage: java KruskalMST <file> [<text> | dot | weight]");
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
