/*
public class Node<T> {
    T item;
    Node<T> next;
}
*/

public class ListCycle {
    public static <T> boolean hasCycle(Node<T> node) {
        if (node == null) return false;
        Node<T> slow = node, fast = node;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    public static void main(String[] args) throws Throwable {
        Assert.assertFalse(hasCycle(null));

        Node<Integer> n1 = new Node<>(1);
        n1.next = n1;
        Assert.assertTrue(hasCycle(n1));

        Node<Integer> n2 = new Node<>(2);
        n1.next = n2;
        Node<Integer> n3 = new Node<>(3);
        n2.next = n3;
        Node<Integer> n4 = new Node<>(4);
        n3.next = n4;
        Node<Integer> n5 = new Node<>(5);
        n4.next = n5;

        Assert.assertFalse(hasCycle(n1));

        n5.next = n2;
        Assert.assertTrue(hasCycle(n1));

        System.out.println("OK");
    }
}
