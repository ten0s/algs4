import edu.princeton.cs.algs4.*;

// $ make run CLASS=Graph ARGS="../data/tinyCG.txt dot" | dot -Tpng > tinyCG.png ; open tinyCG.png

public class Graph {
    private final int V;        // number of vertices
    private int E;              // number of edges
    private Bag<Integer>[] adj; // adjacency lists

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public Graph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (int v = 0; v < V; v++) {
            s += v + ": ";
            for (int w : adj(v)) {
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }

    public String toDot() {
        String s = "graph {\n";
        HashSet<Integer> nodes = new HashSet<>();
        HashSet<String> pairs = new HashSet<>();
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                nodes.add(w);
                if (!pairs.contains(w + "-" + v)) {
                    s += "  " + v + " -- " + w + ";\n";
                    pairs.add(v + "-" + w);
                }
            }
        }
        for (int v = 0; v < V; v++) {
            if (!nodes.contains(v)) {
                s += "  " + v + ";\n";
            }
        }
        s += "}\n";
        return s;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java Graph <file> [<text> | dot]");
            return;
        }
        Graph G = new Graph(new In(args[0]));
        if (args.length > 1 && args[1].equals("dot")) {
            StdOut.println(G.toDot());
        } else {
            StdOut.println(G);
        }
    }
}
