import edu.princeton.cs.algs4.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// $ make run CLASS=QuickFindPlot < ../../data/mediumUF.txt

public class QuickFindPlot {
    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickFindUF uf = new QuickFindUF(n);

        int i = 1, total = 0;
        List<Integer> costStat = new ArrayList<>();
        List<Integer> totalStat = new ArrayList<>();

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
            }
            costStat.add(uf.cost());
            total += uf.cost();
            totalStat.add(total/i);
            uf.clearCost();
            i++;
        }

        StdDraw.setXscale(0, i);
        int costMin = Collections.min(costStat);
        int costMax = Collections.max(costStat);
        int totalMin = Collections.min(totalStat);
        int totalMax = Collections.max(totalStat);
        StdDraw.setYscale(Math.min(costMin, totalMin), Math.max(costMax, totalMax));
        for (int j = 0; j < i-1; j++) {
            StdDraw.setPenColor(StdDraw.GRAY);
            StdDraw.point(j, costStat.get(j));
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.point(j, totalStat.get(j));
            StdOut.println(j + " " + costStat.get(j) + " " + totalStat.get(j));
        }
    }
}
