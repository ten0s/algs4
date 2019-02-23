import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=BellmanFordSPs ARGS="../data/tinyEWD.txt 0"
#+END_SRC

#+RESULTS:
: 0 to 0 (0.00):
: 0 to 1 (1.05): 0->4 0.38000 4->5 0.35000 5->1 0.32000
: 0 to 2 (0.26): 0->2 0.26000
: 0 to 3 (0.99): 0->2 0.26000 2->7 0.34000 7->3 0.39000
: 0 to 4 (0.38): 0->4 0.38000
: 0 to 5 (0.73): 0->4 0.38000 4->5 0.35000
: 0 to 6 (1.51): 0->2 0.26000 2->7 0.34000 7->3 0.39000 3->6 0.52000
: 0 to 7 (0.60): 0->2 0.26000 2->7 0.34000

#+BEGIN_SRC sh :results output
make run CLASS=BellmanFordSPs ARGS="../data/tinyEWG.txt 0"
#+END_SRC

#+RESULTS:
: 0 to 0 (0.00):
: 0 to 1 (Infinity):
: 0 to 2 (0.26): 0->2 0.26000
: 0 to 3 (0.43): 0->2 0.26000 2->3 0.17000
: 0 to 4 (0.38): 0->4 0.38000
: 0 to 5 (0.73): 0->4 0.38000 4->5 0.35000
: 0 to 6 (0.95): 0->2 0.26000 2->3 0.17000 3->6 0.52000
: 0 to 7 (0.16): 0->7 0.16000

#+BEGIN_SRC sh :results output
make run CLASS=BellmanFordSPs ARGS="../data/tinyEWDn.txt 0"
#+END_SRC

#+RESULTS:
: 0 to 0 (0.00):
: 0 to 1 (0.93): 0->2 0.26000 2->7 0.34000 7->3 0.39000 3->6 0.52000 6->4 -1.25000 4->5 0.35000 5->1 0.32000
: 0 to 2 (0.26): 0->2 0.26000
: 0 to 3 (0.99): 0->2 0.26000 2->7 0.34000 7->3 0.39000
: 0 to 4 (0.26): 0->2 0.26000 2->7 0.34000 7->3 0.39000 3->6 0.52000 6->4 -1.25000
: 0 to 5 (0.61): 0->2 0.26000 2->7 0.34000 7->3 0.39000 3->6 0.52000 6->4 -1.25000 4->5 0.35000
: 0 to 6 (1.51): 0->2 0.26000 2->7 0.34000 7->3 0.39000 3->6 0.52000
: 0 to 7 (0.60): 0->2 0.26000 2->7 0.34000

#+BEGIN_SRC sh :results output
make run CLASS=BellmanFordSPs ARGS="../data/tinyEWDnc.txt 0"
#+END_SRC

#+RESULTS:
: Found negative cycle: 5->4 -0.66000 4->5 0.35000

NB: Will take 5+ mins
#+BEGIN_SRC sh :results output
make run CLASS=BellmanFordSPs ARGS="../data/largeEWD.txt 0 sum"
#+END_SRC
*/

public class BellmanFordSPs {
    private final double INFINITY = Double.POSITIVE_INFINITY;
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private boolean[] onQueue;
    private Queue<Integer> queue;
    private int counter;
    private Iterable<DirectedEdge> cycle;

    public BellmanFordSPs(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        onQueue = new boolean[G.V()];
        queue = new Queue<>();

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0.0;

        queue.enqueue(s);
        onQueue[s] = true;

        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(G, v);
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if (counter++ % G.V() == 0) {
                findNegativeCycle();
            }
        }
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            if (edgeTo[v] != null) {
                spt.addEdge(edgeTo[v]);
            }
        }
        EdgeWeightedDigraphCycle cf = new EdgeWeightedDigraphCycle(spt);
        cycle = cf.cycle();
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

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java BellmanFordSPs <file> <source> [<path> | sum]");
            return;
        }

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(args[0]));
        int s = Integer.parseInt(args[1]);

        BellmanFordSPs sp = new BellmanFordSPs(G, s);
        if (args.length > 2 && args[2].equals("sum")) {
            double sum = 0.0;
            for (int v = 0; v < G.V(); v++) {
                sum += sp.distTo[v];
            }
            StdOut.println(sum);
        } else {
            if (sp.hasNegativeCycle()) {
                StdOut.print("Found negative cycle: ");
                for (DirectedEdge e : sp.negativeCycle()) {
                    StdOut.print(e + " ");
                }
                StdOut.println();
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
}
