import edu.princeton.cs.algs4.*;

public class Digraph {
    private final int V;        // number of vertices
    private int E;              // number of edges
    private Bag<Integer>[] adj; // adjacency lists

    @SuppressWarnings("unchecked")
    public Digraph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public Digraph(In in) {
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
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj[v]) {
                R.addEdge(w, v);
            }
        }
        return R;
    }

    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (int v = 0; v < V; v++) {
            s += v + ": ";
            for (int w : adj[v]) {
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        HashSet<Integer> nodes = new HashSet<>();
        for (int v = 0; v < V; v++) {
            for (int w : adj[v]) {
                nodes.add(v);
                nodes.add(w);
                sb.append("  " + v + " -> " + w + "\n");
            }
        }
        for (int v = 0; v < V; v++) {
            if (!nodes.contains(v)) {
                sb.append("  " + v + "\n");
            }
        }
        DigraphTopologicalSort ts = new DigraphTopologicalSort(this);
        if (ts.hasOrder()) {
            sb.append("  // {rank=same");
            for (int v : ts.order()) {
                sb.append(" " + v);
            }
            sb.append("}\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java Digraph <file> [<text> | dot] [<dir> | rev]");
            return;
        }
        Digraph G = new Digraph(new In(args[0]));
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
