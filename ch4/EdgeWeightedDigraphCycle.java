public class EdgeWeightedDigraphCycle {
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private boolean[] onStack;
    private Stack<DirectedEdge> cycle;

    public EdgeWeightedDigraphCycle(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            // short circuit if cycle already found
            if (hasCycle()) return;
            int w = e.to();
            if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            } else if (onStack[w]) {
                cycle = new Stack<>();
                DirectedEdge f = e;
                while (f.from() != w) {
                    f = edgeTo[f.from()];
                    cycle.push(f);
                }
                cycle.push(e);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

    public static void main(String[] args) throws Throwable {

    }
}
