/*
#+BEGIN_SRC sh :results output
make run CLASS=Prime 2>&1
#+END_SRC

#+RESULTS:
: usage: java Prime <INT>

#+BEGIN_SRC sh :results output
make run CLASS=Prime ARGS="18"
#+END_SRC

#+RESULTS:
: isPrime: false
: nextPrime: 19
*/

public class Prime {
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int f = 2; f*f <= n; f++) {
            if (n % f == 0) {
                return false;
            }
        }
        return true;
    }

    public static int nextPrime(int n) {
        if (n < 2) return 2;
        int p = (n % 2 == 0 ? n + 1 : n + 2);
        while (!isPrime(p)) {
            p += 2;
        }
        return p;
    }

    public static void main(String[] args) throws Throwable {
        if (args.length == 1) {
            int n = Integer.parseInt(args[0]);
            System.out.println("isPrime: " + isPrime(n));
            System.out.println("nextPrime: " + nextPrime(n));
        } else {
            System.err.println("usage: java Prime <INT>");
        }
    }
}
