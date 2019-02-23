import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=LazyDijkstraSP ARGS="../data/tinyEWD.txt 0 6"
#+END_SRC

#+RESULTS:
: 0 to 6 (1.51): 0->2 0.26000 2->7 0.34000 7->3 0.39000 3->6 0.52000

#+BEGIN_SRC sh :results output
make run CLASS=LazyDijkstraSP ARGS="../data/tinyEWD.txt 0 6 dist"
#+END_SRC

#+RESULTS:
: 1.51

#+BEGIN_SRC sh :results output
make run CLASS=LazyDijkstraSP ARGS="../data/tinyEWG.txt 0 6"
#+END_SRC

#+RESULTS:
: 0 to 6 (0.95): 0->2 0.26000 2->3 0.17000 3->6 0.52000

NB: Will take 5+ mins
#+BEGIN_SRC sh :results output
make run CLASS=LazyDijkstraSP ARGS="../data/largeEWD.txt 0 999812 dist"
#+END_SRC
*/

public class LazyDijkstraSP {
    private final double INFINITY = Double.POSITIVE_INFINITY;
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private MinPQ<DirectedEdge> pq;
    private final int t;

    public LazyDijkstraSP(EdgeWeightedDigraph G, int s, int t) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new MinPQ<>();
        this.t = t;

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0.0;

        relax(G, s);
        while (!pq.isEmpty()) {
            DirectedEdge e = pq.delMin();
            int v = e.to();
            if (v == t) return;
            relax(G, v);
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

    public double dist() {
        return distTo[t];
    }

    public boolean hasPath() {
        return distTo[t] < INFINITY;
    }

    public Iterable<DirectedEdge> path() {
        if (!hasPath()) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[t]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java LazyDijkstraSP <file> <source> <sink> [<path> | dist]");
            return;
        }

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        int t = Integer.parseInt(args[2]);

        LazyDijkstraSP sp = new LazyDijkstraSP(G, s, t);
        if (args.length > 3 && args[3].equals("dist")) {
            StdOut.printf("%4.2f\n", sp.dist());
        } else {
            StdOut.print(s + " to " + t);
            StdOut.printf(" (%4.2f): ", sp.dist());
            if (sp.hasPath()) {
                for (DirectedEdge e : sp.path()) {
                    StdOut.print(e + " ");
                }
                StdOut.println();
            }
        }
    }
}
