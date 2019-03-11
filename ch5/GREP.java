import edu.princeton.cs.algs4.*;

public class GREP {
    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Usage: java GREP <regexp> [dot] -");
            return;
        }
        String regexp = args[0];
        int M = regexp.length();
        if (!(regexp.charAt(0) == '(' && regexp.charAt(M-1) == ')'))
            regexp = "(.*" + regexp + ".*)";
        RegExp re = new RegExp(regexp);
        if (args.length > 1 && args[1].equals("dot")) {
            StdOut.println(re.toDot());
        }
        while (!StdIn.isEmpty()) {
            String txt = StdIn.readLine();
            if (re.matches(txt)) {
                StdOut.println(txt);
            }
        }
    }
}
