import java.util.Objects;

public class DirectedEdge implements Comparable<DirectedEdge> {
    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public int compareTo(DirectedEdge that) {
        if      (this.weight < that.weight) return -1;
        else if (this.weight > that.weight) return +1;
        else                                return 0;
    }

    public String toString() {
        return String.format("%d->%d %.5f", v, w, weight);
    }

    public boolean equals(Object x) {
        if (x == this) return true;
        if (x == null) return false;
        if (x.getClass() != this.getClass()) return false;
        DirectedEdge that = (DirectedEdge) x;
        return this.v == that.v &&
               this.w == this.w &&
               this.weight == that.weight;
    }

    public int hashCode() {
        return Objects.hash(v, w, weight);
    }
}
