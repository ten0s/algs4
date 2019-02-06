public class DigraphTopologicalSort {
    private Iterable<Integer> order;

    public DigraphTopologicalSort(Digraph G) {
        DigraphCycle cycleFinder = new DigraphCycle(G);
        if (!cycleFinder.hasCycle()) {
            DigraphOrders orders = new DigraphOrders(G);
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
