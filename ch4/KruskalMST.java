import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=KruskalMST ARGS="../data/tinyEWG.txt"
#+END_SRC

#+RESULTS:
: 0-7 0.16000
: 2-3 0.17000
: 1-7 0.19000
: 0-2 0.26000
: 5-7 0.28000
: 4-5 0.35000
: 6-2 0.40000
: 1.81000

#+BEGIN_SRC sh :results output drawer
make run CLASS=KruskalMST ARGS="../data/tinyEWG.txt dot"
#+END_SRC

#+RESULTS:
:RESULTS:
graph {
  5 -- 7 [label="0.28", penwidth=3.0];
  4 -- 5 [label="0.35", penwidth=3.0];
  4 -- 7 [label="0.37"];
  6 -- 4 [label="0.93"];
  3 -- 6 [label="0.52"];
  2 -- 3 [label="0.17", penwidth=3.0];
  2 -- 7 [label="0.34"];
  6 -- 2 [label="0.4", penwidth=3.0];
  1 -- 5 [label="0.32"];
  1 -- 7 [label="0.19", penwidth=3.0];
  1 -- 2 [label="0.36"];
  1 -- 3 [label="0.29"];
  0 -- 7 [label="0.16", penwidth=3.0];
  0 -- 4 [label="0.38"];
  0 -- 2 [label="0.26", penwidth=3.0];
  6 -- 0 [label="0.58"];
}

:END:

NB: Will take 5+ mins
#+BEGIN_SRC sh :results output
make run CLASS=KruskalMST ARGS="../data/largeEWG.txt weight"
#+END_SRC

Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
*/

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
        String s = "graph {\n";
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            double weight = e.weight();
            if (set.contains(e)) {
                s += "  " + v + " -- " + w + " [label=\"" + weight + "\", penwidth=3.0];\n";
            } else {
                s += "  " + v + " -- " + w + " [label=\"" + weight + "\"];\n";
            }
        }
        s += "}\n";
        return s;
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
