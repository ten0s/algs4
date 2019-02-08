public class GraphUtil {
    public static String pathToString(Iterable<Integer> path) {
        String s = "";
        for (int x : path) {
            if (s.equals("")) {
                s = "" + x;
            } else {
                s += "-" + x;
            }
        }
        return s;
    }

    public static String directedWeightedPathToString(Iterable<DirectedEdge> path) {
        String s = "";
        DirectedEdge l = null;
        for (DirectedEdge x : path) {
            if (s.equals("")) {
                s = "" + x.from();
            } else {
                s += "->" + x.from();
            }
            l = x;
        }
        if (l != null) {
            s += "->" + l.to();
        }
        return s;
    }
}
