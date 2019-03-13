import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=RLE << EOF
- AAABBC
+ A3B2C1
- WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWBWWWWWWWWWWWWWW
+ W12B1W12B3W24B1W14
EOF
#+END_SRC

#+RESULTS:
: A3B2C1
: AAABBC
: W12B1W12B3W24B1W14
: WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWBWWWWWWWWWWWWWW

*/

public class RLE {
    public static String compress(String s) {
        final int n = s.length();
        if (n == 0) return s;
        StringBuilder sb = new StringBuilder();
        char symbol = s.charAt(0);
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) != symbol) {
                sb.append(symbol);
                sb.append(count);
                symbol = s.charAt(i);
                count = 1;
            } else {
                count++;
            }
        }
        sb.append(symbol);
        sb.append(count);
        return sb.toString();
    }

    public static String expand(String s) {
        final int n = s.length();
        if (n == 0) return s;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < n) {
            char symbol = s.charAt(i++);
            StringBuilder digits = new StringBuilder();
            while (i < n) {
                char ch = s.charAt(i);
                if (Character.isDigit(ch)) {
                    digits.append(ch);
                    i++;
                } else {
                    break;
                }
            }
            int count = Integer.parseInt(digits.toString());
            for (int k = 0; k < count; k++) {
                sb.append(symbol);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length != 0) {
            StdOut.println("Usage: java RLE << EOF");
            StdOut.println("- STRING");
            StdOut.println("+ STRING");
            StdOut.println("EOF");
            return;
        }
        while (!StdIn.isEmpty()) {
            String cmd = StdIn.readString();
            String str = StdIn.readString();
            if (cmd.equals("-")) {
                StdOut.println(compress(str));
            } else if (cmd.equals("+")) {
                StdOut.println(expand(str));
            }
        }
    }
}
