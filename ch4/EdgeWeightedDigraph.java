import edu.princeton.cs.algs4.*;

// $ make run CLASS=EdgeWeightedDigraph ARGS="../data/tinyEWG.txt dot" | dot -Tpng > tinyEWG.png ; open tinyEWG.png

public class EdgeWeightedDigraph {
    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public EdgeWeightedDigraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> edges = new Bag<>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj[v]) {
                edges.add(e);
            }
        }
        return edges;
    }

    public EdgeWeightedDigraph reverse() {
        EdgeWeightedDigraph R = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj[v]) {
                R.addEdge(new DirectedEdge(e.to(), e.from(), e.weight()));
            }
        }
        return R;
    }

    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (DirectedEdge e : edges()) {
            s += e + "\n";
        }
        return s;
    }

    public String toDot() {
        String s = "digraph {\n";
        for (DirectedEdge e : edges()) {
            int v = e.from(), w = e.to();
            double weight = e.weight();
            s += v + " -> " + w + " [label=\"" + weight + "\"];\n";
        }
        s += "}\n";
        return s;
    }

    public static void main(String[] args) throws Throwable {
        if (args.length == 0) {
            StdOut.println("usage: java EdgeWeightedDigraph <file> [<text> | dot] [<dir> | rev]");
            return;
        }
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(args[0]));
        if (args.length > 2 && args[2].equals("rev")) {
            G = G.reverse();
        }
        if (args.length > 1 && args[1].equals("dot")) {
            StdOut.println(G.toDot());
        } else {
            StdOut.println(G);
        }
    }
}
