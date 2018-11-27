import edu.princeton.cs.algs4.*;

// Ex 1.5.22,23,24

/*
$ make run CLASS=DoublingTestUF ARGS="QuickFindUF 1"
         n      count    time   ratio
      1000       3009     0.0     0.1
      2000       7018     0.0     1.0
      4000      15292     0.0     3.5
      8000      43452     0.1     3.8
     16000      70016     0.4     3.8
     32000     207147     1.7     4.1
     64000     313743     6.6     3.9
    128000     771705    26.2     4.0
*/

/*
$ make run CLASS=DoublingTestUF ARGS="QuickUnionUF 1"
         n      count    time   ratio
      1000       3807     0.0     0.0
      2000       9388     0.0     3.3
      4000      18541     0.0     2.8
      8000      42173     0.2     4.6
     16000      67867     0.8     4.8
     32000     155578     4.9     6.0
     64000     345044    23.6     4.9
*/

/*
$ make run CLASS=DoublingTestUF ARGS="QuickUnionPathCompressionUF 1"
         n      count    time   ratio
      1000       3772     0.0     0.0
      2000       7180     0.0     1.0
      4000      17629     0.0     2.5
      8000      52948     0.0     1.8
     16000      72296     0.0     0.9
     32000     207728     0.0     4.5
     64000     412654     0.0     0.7
    128000     871134     0.1     2.4
    256000    1666970     0.1     2.2
    512000    3317425     0.3     2.2
   1024000    6972873     1.1     3.8
   2048000   13559256     3.1     2.9
   4096000   31869401     8.5     2.7
   8192000   67029789    19.7     2.3
  16384000  135362655    44.5     2.3
*/

/*
$ make run CLASS=DoublingTestUF ARGS="WeightedQuickUnionUF 1"
         n      count    time   ratio
      1000       3005     0.0     0.0
      2000      10492     0.0     2.0
      4000      21213     0.0     1.8
      8000      40155     0.0     1.3
     16000      75830     0.0     1.0
     32000     166239     0.0     2.1
     64000     389343     0.0     2.2
    128000     843256     0.1     1.8
    256000    1449034     0.1     1.8
    512000    3495823     0.4     2.9
   1024000    7412409     1.5     3.9
   2048000   15876440     4.5     2.9
   4096000   34584971    11.1     2.5
   8192000   65580140    22.6     2.0
  16384000  151717922    55.5     2.5
*/

/*
$ make run CLASS=DoublingTestUF ARGS="WeightedQuickUnionPathCompressionUF 1"
         n      count    time   ratio
      1000       3314     0.0     0.0
      2000       6256     0.0     1.0
      4000      20586     0.0     3.0
      8000      34073     0.0     1.3
     16000      85106     0.0     1.5
     32000     181674     0.0     2.9
     64000     347687     0.0     1.2
    128000     821776     0.0     1.2
    256000    1678471     0.1     2.1
    512000    3100780     0.2     2.3
   1024000    6860288     0.9     3.9
   2048000   15209681     2.6     2.9
   4096000   31548900     5.9     2.2
   8192000   64132235    12.2     2.1
  16384000  148030813    28.4     2.3
*/

class Result {
    int count;
    double time;
}

public class DoublingTestUF {
    private static Result timeTrial(String algo, int n, int t) throws Exception {
        int countSum = 0;
        double timeSum = 0;
        for (int i = 0; i < t; i++) {
            Stopwatch timer = new Stopwatch();
            countSum += ErdosRenyi.count(algo, n);
            timeSum += timer.elapsedTime();
        }
        Result res = new Result();
        res.count = countSum / t;
        res.time = timeSum / t;
        return res;
    }

    public static void main(String[] args) throws Exception {
        String algo = args.length > 0 ? args[0] : "WeightedQuickUnionPathCompressionUF";
        int t = args.length > 1 ? Integer.parseInt(args[1]) : 5;
        double prev = timeTrial(algo, 500, t).time;
        StdOut.printf("         n      count    time   ratio\n");
        for (int n = 1000; true; n *= 2) {
            Result res = timeTrial(algo, n, t);
            int count = res.count;
            double time = res.time;
            StdOut.printf("%10d %10d %7.1f %7.1f\n", n, count, time, time/prev);
            prev = time;
        }
    }
}
