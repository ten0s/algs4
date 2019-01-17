import edu.princeton.cs.algs4.*;

// $ make compile CLASS=SymbolDigraph

// $ java SymbolDigraph ../data/routes.txt " "
// JFK
//   ORD
//   ATL
//   MCO
// LAX
// LAS
//   PHX
//   LAX
// ^-D

// $ java SymbolDigraph ../data/routes.txt " " dot
// digraph {
//   0 [label="JFK"];
//   0 -> 2;
//   0 -> 7;
//   0 -> 1;
//   1 [label="MCO"];
//   2 [label="ORD"];
//   2 -> 7;
//   2 -> 6;
//   2 -> 5;
//   2 -> 4;
//   2 -> 3;
//   3 [label="DEN"];
//   3 -> 9;
//   3 -> 6;
//   4 [label="HOU"];
//   4 -> 1;
//   5 [label="DFW"];
//   5 -> 4;
//   5 -> 6;
//   6 [label="PHX"];
//   6 -> 8;
//   7 [label="ATL"];
//   7 -> 1;
//   7 -> 4;
//   8 [label="LAX"];
//   9 [label="LAS"];
//   9 -> 6;
//   9 -> 8;
// }

// $ java SymbolDigraph ../data/routes.txt " " dot | circo -Tpng > routes.png ; open routes.png

public class SymbolDigraph {
    private HashMap<String, Integer> st; // name -> index
    private String[] keys;               // index -> name
    private Digraph digraph;             // underlying digraph

    public SymbolDigraph(String filename, String separator) {
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
        digraph = new Digraph(st.size());
        in = new In(filename);
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(separator);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                digraph.addEdge(v, st.get(a[i]));
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

    public Digraph digraph() {
        return digraph;
    }

    public void printDot() {
        StdOut.println("digraph {");
        for (int v = 0; v < digraph.V(); v++) {
            StdOut.println("  " + v + " [label=\"" + nameOf(v) + "\"];");
            for (int w : digraph.adj(v)) {
                StdOut.println("  " + v + " -> " + w + ";");
            }
        }
        StdOut.println("}");
    }

    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            StdOut.println("usage: java SymbolDigraph <filename> <separator> [dot]");
            return;
        }
        String filename = args[0];
        String separator = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, separator);
        if (args.length == 2) {
            Digraph digraph = sg.digraph();
            while (StdIn.hasNextLine()) {
                String s = StdIn.readLine();
                for (int v : digraph.adj(sg.indexOf(s))) {
                    StdOut.println("   " + sg.nameOf(v));
                }
            }
        } else if (args.length == 3) {
            sg.printDot();
        }
    }
}
