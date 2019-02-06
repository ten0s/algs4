import edu.princeton.cs.algs4.*;

// $ make run CLASS=LazyDijkstraSP ARGS="../data/tinyEWD.txt 0"
// 0 to 0 (0.00):
// 0 to 1 (1.05): 0->4 0.38000 4->5 0.35000 5->1 0.32000
// 0 to 2 (0.26): 0->2 0.26000
// 0 to 3 (0.99): 0->2 0.26000 2->7 0.34000 7->3 0.39000
// 0 to 4 (0.38): 0->4 0.38000
// 0 to 5 (0.73): 0->4 0.38000 4->5 0.35000
// 0 to 6 (1.51): 0->2 0.26000 2->7 0.34000 7->3 0.39000 3->6 0.52000
// 0 to 7 (0.60): 0->2 0.26000 2->7 0.34000

// $ make run CLASS=LazyDijkstraSP ARGS="../data/tinyEWG.txt 0"
// 0 to 0 (0.00):
// 0 to 1 (Infinity):
// 0 to 2 (0.26): 0->2 0.26000
// 0 to 3 (0.43): 0->2 0.26000 2->3 0.17000
// 0 to 4 (0.38): 0->4 0.38000
// 0 to 5 (0.73): 0->4 0.38000 4->5 0.35000
// 0 to 6 (0.95): 0->2 0.26000 2->3 0.17000 3->6 0.52000
// 0 to 7 (0.16): 0->7 0.16000

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

        relax(G, s);
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
