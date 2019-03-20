import edu.princeton.cs.algs4.*;

public class KWIC {
    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: javac KWIC <FILE> <CONTEXT> << EOF");
            StdOut.println("QUERY");
            StdOut.println("EOF");
            return;
        }
        In in = new In(args[0]);
        int context = Integer.parseInt(args[1]);

        String text = in.readAll().replaceAll("\\s+", " ");
        int n = text.length();
        SuffixArray sa = new SuffixArray(text);

        while (!StdIn.isEmpty()) {
            String query = StdIn.readLine();
            for (int i = sa.rank(query); i < n; i++) {
                // check if sorted suffix i is a match
                int from1 = sa.index(i);
                int to1   = Math.min(n, sa.index(i) + query.length());
                if (!query.equals(text.substring(from1, to1))) break;

                // print centex surrounding sorted suffix i
                int from2 = Math.max(0, sa.index(i) - context);
                int to2   = Math.min(n, sa.index(i) + context + query.length());
                StdOut.println(text.substring(from2, sa.index(i)) +
                               green(text.substring(sa.index(i), sa.index(i) + query.length())) +
                               text.substring(sa.index(i) + query.length(), to2));
            }
            StdOut.println();
        }
    }

    private static String green(String s) {
        return (char)27 + "[32m" + s + (char)27 + "[0m";
    }
}
