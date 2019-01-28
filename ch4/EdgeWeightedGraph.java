import edu.princeton.cs.algs4.*;

// $ make run CLASS=EdgeWeightedGraph ARGS="../data/tinyEWG.txt dot" | dot -Tpng > tinyEWG.png ; open tinyEWG.png

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
            s += v + " -- " + w + " [label=\"" + weight + "\"];\n";
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
