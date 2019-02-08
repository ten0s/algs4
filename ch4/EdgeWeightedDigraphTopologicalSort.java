public class EdgeWeightedDigraphTopologicalSort {
    private Iterable<Integer> order;

    public EdgeWeightedDigraphTopologicalSort(EdgeWeightedDigraph G) {
        EdgeWeightedDigraphCycle cycleFinder = new EdgeWeightedDigraphCycle(G);
        if (!cycleFinder.hasCycle()) {
            EdgeWeightedDigraphOrders orders = new EdgeWeightedDigraphOrders(G);
            order = orders.reversedPostOrder();
        } else {
            throw new IllegalArgumentException(
                "\nCycle detected: " + GraphUtil.pathToString(cycleFinder.cycle())
            );
        }
    }

    public boolean hasOrder() {
        return order != null;
    }

    public Iterable<Integer> order() {
        return order;
    }

    public static void main(String[] args) throws Throwable {

    }
}
