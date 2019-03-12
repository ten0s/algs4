import edu.princeton.cs.algs4.*;

public class SymbolGraph {
    private HashMap<String, Integer> st; // name -> index
    private String[] keys;               // index -> name
    private Graph graph;                 // underlying graph

    public SymbolGraph(String filename, String separator) {
        st = new HashMap<>();
        In in = new In(filename);
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(separator);
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i])) {
                    st.put(a[i], st.size());
                }
            }
        }
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }
        graph = new Graph(st.size());
        in = new In(filename);
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(separator);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                graph.addEdge(v, st.get(a[i]));
            }
        }
    }

    public boolean contains(String s) {
        return st.contains(s);
    }

    public int indexOf(String s) {
        return st.get(s);
    }

    public String nameOf(int v) {
        return keys[v];
    }

    public Graph graph() {
        return graph;
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("graph {\n");
        HashSet<String> set = new HashSet<>();
        for (int v = 0; v < graph.V(); v++) {
            sb.append("  " + v + " [label=\"" + nameOf(v) + "\"]\n");
            for (int w : graph.adj(v)) {
                if (!set.contains(w + "-" + v)) {
                    sb.append("  " + v + " -- " + w + "\n");
                    set.add(v + "-" + w);
                }
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            StdOut.println("usage: java SymbolGraph <filename> <separator> [dot]");
            return;
        }
        String filename = args[0];
        String separator = args[1];
        SymbolGraph sg = new SymbolGraph(filename, separator);
        if (args.length == 2) {
            Graph graph = sg.graph();
            while (StdIn.hasNextLine()) {
                String s = StdIn.readLine();
                StdOut.println(s);
                for (int v : graph.adj(sg.indexOf(s))) {
                    StdOut.println("   " + sg.nameOf(v));
                }
            }
        } else if (args.length == 3) {
            StdOut.println(sg.toDot());
        }
    }
}
