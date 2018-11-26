import edu.princeton.cs.algs4.*;

// $ make run CLASS=TestStdDraw

public class TestStdDraw {
    public static void main(String[] args) {
        int n = 100;
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, n*n);
        StdDraw.setPenRadius(0.01);
        for (int i = 1; i <= n; i++) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.point(i, i);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.point(i, i*i);
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.point(i, i*Math.log(i));
        }
    }
}
