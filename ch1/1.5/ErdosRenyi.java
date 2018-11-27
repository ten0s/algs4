import edu.princeton.cs.algs4.*;
import java.util.function.Supplier;
import java.util.HashMap;

// Ex 1.5.17

// $ make run CLASS=ErdosRenyi ARGS="10000 QuickFindUF"
// 44907

// Ex 1.5.21

// $ N=1000000; for i in `seq 1 10`; do make run CLASS=ErdosRenyi ARGS=$N ; done |
// > awk -v n=$N '{ sum += $1 } END { printf "%.2f ~ %.2f\n", sum/NR, n*log(n)/2 }'
// 6961982.20 ~ 6907755.28

public class ErdosRenyi {
    private static UnionFind createUF(String algo, int n) throws Exception {
        HashMap<String, Supplier<UnionFind>> hmap = new HashMap<>();
        hmap.put("QuickFindUF", () ->
                 new QuickFindUF(n));
        hmap.put("QuickUnionUF", () ->
                 new QuickUnionUF(n));
        hmap.put("QuickUnionPathCompressionUF", () ->
                 new QuickUnionPathCompressionUF(n));
        hmap.put("WeightedQuickUnionUF", () ->
                 new WeightedQuickUnionUF(n));
        hmap.put("WeightedQuickUnionPathCompressionUF", () ->
                 new WeightedQuickUnionPathCompressionUF(n));
        Supplier<UnionFind> cons = hmap.get(algo);
        if (cons != null) {
            return cons.get();
        }
        throw new Exception("Unknown: " + algo);
    }

    public static int count(String algo, int n) throws Exception {
        int connections = 0;
        UnionFind uf = createUF(algo, n);
        while (uf.count() != 1) {
            int p = StdRandom.uniform(0, n);
            int q = StdRandom.uniform(0, n);
            if (!uf.connected(p, q)) {
                uf.union(p, q);
            }
            connections++;
        }
        return connections;
    }

    public static void main(String[] args) throws Exception {
        int n = Integer.parseInt(args[0]);
        String algo = args.length > 1 ? args[1] : "WeightedQuickUnionPathCompressionUF";
        int c = count(algo, n);
        StdOut.println(c);
    }
}
