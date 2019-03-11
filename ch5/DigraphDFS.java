import java.util.*;

public class DigraphDFS {
    private boolean[] marked;

    public DigraphDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    public DigraphDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int s : sources) {
            if (!marked[s]) {
                dfs(G, s);
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }
}
