import edu.princeton.cs.algs4.*;

/*
$ make run CLASS=DigraphTopologicalSort ARGS="../data/jobs.txt /"
Calculus
Linear Algebra
Introduction to CS
Advanced Programming
Algorithms
Theoretical CS
Artificial Intelligence
Robotics
Machine Learning
Neural Networks
Databases
Scientific Computing
Computational Biology
*/

public class DigraphTopologicalSort {
    private Iterable<Integer> order;

    public DigraphTopologicalSort(Digraph G) {
        DigraphCycle cycleFinder = new DigraphCycle(G);
        if (!cycleFinder.hasCycle()) {
            DigraphOrders orders = new DigraphOrders(G);
            order = orders.reversedPostOrder();
        } else {
            throw new IllegalArgumentException(
                "\nCycle detected: " + GraphUtil.pathToString(cycleFinder.cycle())
            );
        }
    }

    public boolean hasOrder() {
        return order != null;
    }

    public Iterable<Integer> order() {
        return order;
    }

    public static void main(String[] args) throws Throwable {
        if (args.length == 0) {
            StdOut.println("usage: java DigraphTopologicalSort <filename> <delimiter>");
            return;
        }
        String filename = args[0];
        String delimiter = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);

        DigraphTopologicalSort top = new DigraphTopologicalSort(sg.digraph());
        for (int v : top.order()) {
            StdOut.println(sg.nameOf(v));
        }
    }
}
