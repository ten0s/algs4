import edu.princeton.cs.algs4.*;

// $ make run CLASS=LazyDijkstraSP ARGS="../data/tinyEWD.txt 0"


public class LazyDijkstraSP {
    private final double INFINITY = Double.POSITIVE_INFINITY;
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private MinPQ<DirectedEdge> pq;

    public LazyDijkstraSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new MinPQ<>();

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0.0;

        relax(G, 0);
        while (!pq.isEmpty()) {
            DirectedEdge e = pq.delMin();
            relax(G, e.to());
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                pq.insert(e);
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("usage: java LazyDijkstraSP <file> <source>");
            return;
        }

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(args[0]));
        int s = Integer.parseInt(args[1]);

        DijkstraSP sp = new DijkstraSP(G, s);
        for (int t = 0; t < G.V(); t++) {
            StdOut.print(s + " to " + t);
            StdOut.printf(" (%4.2f): ", sp.distTo(t));
            if (sp.hasPathTo(t)) {
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + " ");
                }
            }
            StdOut.println();
        }
    }
}
