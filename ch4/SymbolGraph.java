import edu.princeton.cs.algs4.*;

// $ make compile CLASS=SymbolGraph

// $ java SymbolGraph ../data/routes.txt " "
// JFK
//   ORD
//   ATL
//   MCO
// LAX
//   LAS
//   PHX
// ^-D

// $ java SymbolGraph ../data/routes.txt " " dot
// graph {
//   0 [label="JFK"];
//   0 -- 2;
//   0 -- 7;
//   0 -- 1;
//   1 [label="MCO"];
//   1 -- 4;
//   1 -- 7;
//   2 [label="ORD"];
//   2 -- 7;
//   2 -- 6;
//   2 -- 5;
//   2 -- 4;
//   2 -- 3;
//   3 [label="DEN"];
//   3 -- 9;
//   3 -- 6;
//   4 [label="HOU"];
//   4 -- 5;
//   4 -- 7;
//   5 [label="DFW"];
//   5 -- 6;
//   6 [label="PHX"];
//   6 -- 9;
//   6 -- 8;
//   7 [label="ATL"];
//   8 [label="LAX"];
//   8 -- 9;
//   9 [label="LAS"];
// }

// $ java SymbolGraph ../data/routes.txt " " dot | circo -Tpng > routes.png ; open routes.png

// $ java SymbolGraph ../data/movies.txt "/" dot > movies.dot
// $ dot -Tpng movies.dot > movies.png ; open movies.png

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

    public void printDot() {
        StdOut.println("graph {");
        HashSet<String> set = new HashSet<>();
        for (int v = 0; v < graph.V(); v++) {
            StdOut.println("  " + v + " [label=\"" + nameOf(v) + "\"];");
            for (int w : graph.adj(v)) {
                if (!set.contains(w + "-" + v)) {
                    StdOut.println("  " + v + " -- " + w + ";");
                    set.add(v + "-" + w);
                }
            }
        }
        StdOut.println("}");
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
                for (int v : graph.adj(sg.indexOf(s))) {
                    StdOut.println("   " + sg.nameOf(v));
                }
            }
        } else if (args.length == 3) {
            sg.printDot();
        }
    }
}
