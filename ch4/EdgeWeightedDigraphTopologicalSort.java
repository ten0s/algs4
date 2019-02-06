public class EdgeWeightedDigraphTopologicalSort {
    private Iterable<Integer> order;

    public EdgeWeightedDigraphTopologicalSort(EdgeWeightedDigraph G) {
        EdgeWeightedDigraphCycle cycleFinder = new EdgeWeightedDigraphCycle(G);
        if (!cycleFinder.hasCycle()) {
            EdgeWeightedDigraphOrders orders = new EdgeWeightedDigraphOrders(G);
            order = orders.reversedPostOrder();
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
