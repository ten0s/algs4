import java.util.*;

public class RegExp {
    private char[] re;
    private Digraph G;
    private int M;

    public RegExp(String regexp) {
        re = regexp.toCharArray();
        M = re.length;
        G = epsilonTransitionDigraph();
    }

    private Digraph epsilonTransitionDigraph() {
        Digraph G = new Digraph(M+1);
        Deque<Integer> ops = new ArrayDeque<>();
        for (int i = 0; i < M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            } else if (re[i] == ')') {
                Deque<Integer> ors = new ArrayDeque<>();
                while (true) {
                    int j = ops.pop();
                    if (re[j] == '|') {
                        ors.add(j);
                    } else {
                        lp = j;
                        break;
                    }
                }
                for (int or : ors) {
                    G.addEdge(lp, or+1);
                    G.addEdge(or, i);
                }
            }
            if (i < M-1) {
                if (re[i+1] == '*') {
                    G.addEdge(lp, i+1);
                    G.addEdge(i+1, lp);
                }
                if (re[i+1] == '+') {
                    G.addEdge(i+1, lp);
                }
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == '+' || re[i] == ')') {
                G.addEdge(i, i+1);
            }
        }
        return G;
    }

    public boolean matches(String txt) {
        Deque<Integer> states = new ArrayDeque<>();
        DigraphDFS dfs = new DigraphDFS(G, 0);
        for (int v = 0; v < G.V(); v++)
            if (dfs.hasPathTo(v)) states.add(v);

        int N = txt.length();
        for (int i = 0; i < N; i++) {
            Deque<Integer> matches = new ArrayDeque<>();
            for (int s : states) {
                if (s == M) return true; //continue;

                if (re[s] == txt.charAt(i) || re[s] == '.')
                    matches.add(s+1);
            }

            states = new ArrayDeque<>();
            dfs = new DigraphDFS(G, matches);
            for (int v = 0; v < G.V(); v++)
                if (dfs.hasPathTo(v)) states.add(v);
        }

        for (int s : states)
            if (s == M) return true;
        return false;
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        sb.append("  rankdir=LR;\n");

        for (int v = 0; v < G.V(); v++) {
            char ch = v < re.length ? re[v] : ' ';
            sb.append("  " + v + " " + attrs(label(ch), xlabel(v), shape("circle")) + ";\n");
        }

        for (int v = 0; v < G.V()-1; v++) {
            if (!isMetaChar(re[v])) {
                sb.append("  " + v + " -> " + (v+1) + " " + attrs(color("black")) + ";\n");
            }
        }

        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                sb.append("  " + v + " -> " + w + " " + attrs(color("red")) + ";\n");
            }
        }

        sb.append("}\n");
        return sb.toString();
    }

    private boolean isMetaChar(char ch) {
        switch (ch) {
        case '(':
        case ')':
        case '*':
        case '+':
        case '|':
            return true;
        default:
            return false;
        }
    }

    private String attrs(String... attrs) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < attrs.length; i++) {
            sb.append(attrs[i]);
            if (i != attrs.length-1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private String label(Object o) {
        return "label=\"" + o + "\"";
    }

    private String xlabel(Object o) {
        return "xlabel=\"" + o + "\"";
    }

    private String color(String c) {
        return "color=\"" + c + "\"";
    }

    private String shape(String s) {
        return "shape=\"" + s + "\"";
    }
}
