import java.util.Iterator;
import java.util.Collections;

import edu.princeton.cs.algs4.*;

/*
$ make run CLASS=DepthFirstPaths ARGS="../data/tinyCG.txt 0"
0 to 0: 0
0 to 1: 0-2-1
0 to 2: 0-2
0 to 3: 0-2-3
0 to 4: 0-2-3-4
0 to 5: 0-2-3-5
*/

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public DepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return new Iterable<Integer>() {
                public Iterator<Integer> iterator() {
                    return Collections.emptyIterator();
                }
            };
        }
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("usage: java DepthFirstPaths <file> <source>");
            return;
        }
        Graph G = new Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        DepthFirstPaths ps = new DepthFirstPaths(G, s);
        for (int v = 0; v < G.V(); v++) {
            StdOut.print(s + " to " + v + ": ");
            for (int x : ps.pathTo(v)) {
                if (x == s) StdOut.print(x);
                else StdOut.print("-" + x);
            }
            StdOut.println();
        }
    }
}
