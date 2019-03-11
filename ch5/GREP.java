import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=GREP ARGS='(A(B*|C)D)' <<EOF
ABD
ACB
ABBD
ABBBD
ABCD
EOF
#+END_SRC

#+RESULTS:
: ABD
: ABBD
: ABBBD

#+NAME: re1
#+BEGIN_SRC sh :results output
make run CLASS=GREP ARGS='(A(B*|C)*) dot' <<EOF
EOF
#+END_SRC

#+BEGIN_SRC dot :file re1.png :var src=re1
$src
#+END_SRC

#+RESULTS:
[[file:re1.png]]

#+NAME: re2
#+BEGIN_SRC sh :results output
make run CLASS=GREP ARGS='((A*B|AC)D) dot' <<EOF
EOF
#+END_SRC

#+BEGIN_SRC dot :file re2.png :var src=re2
$src
#+END_SRC

#+RESULTS:
[[file:re2.png]]

#+NAME: re3
#+BEGIN_SRC sh :results output
make run CLASS=GREP ARGS='((A|B|C)DE) dot' <<EOF
EOF
#+END_SRC

#+BEGIN_SRC dot :file re3.png :var src=re3
$src
#+END_SRC

#+RESULTS:
[[file:re3.png]]

*/

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
