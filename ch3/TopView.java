import java.util.*;
import java.io.*;

// $ make run CLASS=TopView <topViewInput2.txt
// 2 1 14 15 12

class Node {
    Node left;
    Node right;
    int data;

    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class TopView {

    //+BEGIN_SOLUTION
    static class Entry<K, V> {
        K key;
        V val;
        Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

	public static void topView(Node root) {
        if (root == null) return;

        TreeMap<Integer, Integer> l2d = new TreeMap<>();
        LinkedList<Entry<Node, Integer>> q = new LinkedList<>();

        q.addLast(new Entry<>(root, 0));
        while (!q.isEmpty()) {
            Entry<Node, Integer> n2l = q.removeFirst();
            Node node = n2l.key;
            int level = n2l.val;
            if (!l2d.containsKey(level)) {
                l2d.put(level, node.data);
            }
            if (node.left != null) {
                q.addLast(new Entry<>(node.left, level-1));
            }
            if (node.right != null) {
                q.addLast(new Entry<>(node.right, level+1));
            }
        }

        for (int data : l2d.values()) {
            System.out.print(data + " ");
        }
        System.out.println();
    }
    //+END_SOLUTION

	public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        topView(root);
    }
}
