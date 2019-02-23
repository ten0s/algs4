import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=EdgeWeightedDAGSPs ARGS="../data/tinyEWDAG.txt 5"
#+END_SRC

#+RESULTS:
: 5 to 0 (0.73): 5->4 0.35000 4->0 0.38000
: 5 to 1 (0.32): 5->1 0.32000
: 5 to 2 (0.62): 5->7 0.28000 7->2 0.34000
: 5 to 3 (0.61): 5->1 0.32000 1->3 0.29000
: 5 to 4 (0.35): 5->4 0.35000
: 5 to 5 (0.00):
: 5 to 6 (1.13): 5->1 0.32000 1->3 0.29000 3->6 0.52000
: 5 to 7 (0.28): 5->7 0.28000

#+BEGIN_SRC sh :results output
make run CLASS=EdgeWeightedDAGSPs ARGS="../data/tinyEWDAG.txt 5 sum"
#+END_SRC
4.04

#+BEGIN_SRC sh :results output
make run CLASS=DijkstraSPs ARGS="../data/tinyEWDAG.txt 5 sum"
#+END_SRC

#+RESULTS:
: 4.04
*/

public class EdgeWeightedDAGSPs {
    private final double INFINITY = Double.POSITIVE_INFINITY;
    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public EdgeWeightedDAGSPs(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0.0;

        EdgeWeightedDigraphTopologicalSort top = new EdgeWeightedDigraphTopologicalSort(G);
        for (int v : top.order()) {
            relax(G, v);
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
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
        if (args.length == 0) {
            StdOut.println("usage: java EdgeWeightedDAGSPs <file> <source> [<path> | sum]");
            return;
        }

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(args[0]));
        int s = Integer.parseInt(args[1]);

        EdgeWeightedDAGSPs sp = new EdgeWeightedDAGSPs(G, s);
        if (args.length > 2 && args[2].equals("sum")) {
            double sum = 0.0;
            for (int v = 0; v < G.V(); v++) {
                sum += sp.distTo(v);
            }
            StdOut.println(sum);
        } else {
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
}
