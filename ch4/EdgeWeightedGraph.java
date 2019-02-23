import edu.princeton.cs.algs4.*;

/*
#+NAME: tiny_ewg
#+BEGIN_SRC sh :results output drawer
make run CLASS=EdgeWeightedGraph ARGS="../data/tinyEWG.txt dot"
#+END_SRC

#+RESULTS: tiny_ewg
:RESULTS:
graph {
  5 -- 7 [label="0.28"];
  4 -- 5 [label="0.35"];
  4 -- 7 [label="0.37"];
  6 -- 4 [label="0.93"];
  3 -- 6 [label="0.52"];
  2 -- 3 [label="0.17"];
  2 -- 7 [label="0.34"];
  6 -- 2 [label="0.4"];
  1 -- 5 [label="0.32"];
  1 -- 7 [label="0.19"];
  1 -- 2 [label="0.36"];
  1 -- 3 [label="0.29"];
  0 -- 7 [label="0.16"];
  0 -- 4 [label="0.38"];
  0 -- 2 [label="0.26"];
  6 -- 0 [label="0.58"];
}

:END:

#+BEGIN_SRC dot :file tinyEWG.png :var dotdesc=tiny_ewg
$dotdesc
#+END_SRC

#+RESULTS:
[[file:tinyEWG.png]]
*/

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        this.V = V;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new Edge(v, w, weight));
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> edges = new Bag<>();
        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                if (e.other(v) > v) {
                    edges.add(e);
                }
            }
        }
        return edges;
    }

    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (Edge e : edges()) {
            s += e + "\n";
        }
        return s;
    }

    public String toDot() {
        String s = "graph {\n";
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            double weight = e.weight();
            s += "  " + v + " -- " + w + " [label=\"" + weight + "\"];\n";
        }
        s += "}\n";
        return s;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java EdgeWeightedGraph <file> [<text> | dot]");
            return;
        }
        EdgeWeightedGraph G = new EdgeWeightedGraph(new In(args[0]));
        if (args.length > 1 && args[1].equals("dot")) {
            StdOut.println(G.toDot());
        } else {
            StdOut.println(G);
        }
    }
}
