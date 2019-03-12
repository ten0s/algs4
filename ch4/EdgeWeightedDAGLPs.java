import java.util.Iterator;
import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=EdgeWeightedDAGLPs ARGS="../data/tinyEWDAG.txt 5"
#+END_SRC

#+RESULTS:
: 5 to 0 (2.44): 5->1 0.32000 1->3 0.29000 3->6 0.52000 6->4 0.93000 4->0 0.38000
: 5 to 1 (0.32): 5->1 0.32000
: 5 to 2 (2.77): 5->1 0.32000 1->3 0.29000 3->6 0.52000 6->4 0.93000 4->7 0.37000 7->2 0.34000
: 5 to 3 (0.61): 5->1 0.32000 1->3 0.29000
: 5 to 4 (2.06): 5->1 0.32000 1->3 0.29000 3->6 0.52000 6->4 0.93000
: 5 to 5 (-0.00):
: 5 to 6 (1.13): 5->1 0.32000 1->3 0.29000 3->6 0.52000
: 5 to 7 (2.43): 5->1 0.32000 1->3 0.29000 3->6 0.52000 6->4 0.93000 4->7 0.37000

#+BEGIN_SRC sh :results output
make run CLASS=EdgeWeightedDAGLPs ARGS="../data/tinyEWDAG.txt 5 sum"
#+END_SRC

#+RESULTS:
: 11.759999999999998

*/

public class EdgeWeightedDAGLPs {
    private EdgeWeightedDAGSPs sp;

    public EdgeWeightedDAGLPs(EdgeWeightedDigraph G, int s) {
        EdgeWeightedDigraph N = negate(G);
        sp = new EdgeWeightedDAGSPs(N, s);
    }

    private EdgeWeightedDigraph negate(EdgeWeightedDigraph G) {
        EdgeWeightedDigraph N = new EdgeWeightedDigraph(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                N.addEdge(new DirectedEdge(e.from(), e.to(), -1.0 * e.weight()));
            }
        }
        return N;
    }

    public double distTo(int v) {
        return -1.0 * sp.distTo(v);
    }

    public boolean hasPathTo(int v) {
        return sp.hasPathTo(v);
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        Iterable<DirectedEdge> path = sp.pathTo(v);
        if (path == null) return null;
        Iterator<DirectedEdge> it = path.iterator();
        return new Iterable<DirectedEdge>() {
            public Iterator<DirectedEdge> iterator() {
                return new Iterator<DirectedEdge>() {
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    public DirectedEdge next() {
                        DirectedEdge e = it.next();
                        return new DirectedEdge(e.from(), e.to(), -1.0 * e.weight());
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java EdgeWeightedDAGLPs <file> <source> [<path> | sum]");
            return;
        }

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(args[0]));
        int s = Integer.parseInt(args[1]);

        EdgeWeightedDAGLPs lp = new EdgeWeightedDAGLPs(G, s);
        if (args.length > 2 && args[2].equals("sum")) {
            double sum = 0.0;
            for (int v = 0; v < G.V(); v++) {
                sum += lp.distTo(v);
            }
            StdOut.println(sum);
        } else {
            for (int t = 0; t < G.V(); t++) {
                StdOut.print(s + " to " + t);
                StdOut.printf(" (%4.2f): ", lp.distTo(t));
                if (lp.hasPathTo(t)) {
                    for (DirectedEdge e : lp.pathTo(t)) {
                        StdOut.print(e + " ");
                    }
            }
                StdOut.println();
            }
        }
    }
}
