import java.util.*;
import edu.princeton.cs.algs4.*;

public class FlowNetwork {
    private final int V;
    private int E;
    private List<FlowEdge>[] adj;

    @SuppressWarnings("unchecked")
    public FlowNetwork(int V) {
        this.V = V;
        adj = (List<FlowEdge>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
    }

    public FlowNetwork(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double capacity = in.readDouble();
            addEdge(new FlowEdge(v, w, capacity));
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(FlowEdge e) {
        int v = e.from(), w = e.to();
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<FlowEdge> edges() {
        Deque<FlowEdge> edges = new ArrayDeque<>();
        for (int v = 0; v < V; v++) {
            for (FlowEdge e : adj[v]) {
                if (e.other(v) > v) {
                    edges.add(e);
                }
            }
        }
        return edges;
    }

    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (FlowEdge e : edges()) {
            s += e + "\n";
        }
        return s;
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        for (FlowEdge e : edges()) {
            int v = e.from(), w = e.to();
            double capacity = e.capacity(), flow = e.flow();
            if (flow > 0) {
                sb.append("   " + v + " -> " + w + " [label=\"" + flow + "/" + capacity + "\", penwidth=3.0]\n");
            } else {
                sb.append("   " + v + " -> " + w + " [label=\"" + flow + "/" + capacity + "\"]\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    public static void main(String[] args) throws Throwable {
        if (args.length == 0) {
            StdOut.println("usage: java FlowNetwork <file> [<text> | dot]");
            return;
        }
        FlowNetwork G = new FlowNetwork(new In(args[0]));
        if (args.length > 1 && args[1].equals("dot")) {
            StdOut.println(G.toDot());
        } else {
            StdOut.println(G);
        }
    }
}
