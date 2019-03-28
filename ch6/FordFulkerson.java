import java.util.*;
import edu.princeton.cs.algs4.*;

public class FordFulkerson {
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;

    public FordFulkerson(FlowNetwork G, int s, int t) {
        while (hasAugmentingPath(G, s, t)) {
            // compute bottleneck capacity
            double bottleneck = Double.POSITIVE_INFINITY;
            for(int v = t; v != s; v = edgeTo[v].other(v)) {
                bottleneck = Math.min(bottleneck, edgeTo[v].residualCapacityTo(v));
            }
            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottleneck);
            }

            value += bottleneck;
        }
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        marked = new boolean[G.V()];
        edgeTo = new FlowEdge[G.V()];
        Deque<Integer> queue = new ArrayDeque<>();

        marked[s] = true;
        queue.add(s);
        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    queue.add(w);
                }
            }
        }
        return marked[t];
    }

    public double value() {
        return value;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java FordFulkerson <file> [<text> | dot | value]");
            return;
        }
        FlowNetwork G = new FlowNetwork(new In(args[0]));
        int s = 0, t = G.V() - 1;
        FordFulkerson maxflow = new FordFulkerson(G, s, t);

        if (args.length > 1 && args[1].equals("value")) {
            StdOut.printf("%.5f\n", maxflow.value());
        } else if (args.length > 1 && args[1].equals("dot")) {
            StdOut.println(G.toDot());
        } else {
            StdOut.println("Max flow from " + s + " to " + t);
            for (int v = 0; v < G.V(); v++) {
                for (FlowEdge e : G.adj(v)) {
                    if ((v == e.from()) && e.flow() > 0) {
                        StdOut.println("   " + e);
                    }
                }
            }
            StdOut.printf("Max flow value = %.2f\n", maxflow.value());
        }
    }
}
