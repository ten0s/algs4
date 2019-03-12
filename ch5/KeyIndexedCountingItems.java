import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output drawer
make run CLASS=KeyIndexedCountingItems <<EOF
4
3
3 Anderson
2 Jones
1 Smith
EOF
#+END_SRC

#+RESULTS:
:RESULTS:
1 Smith
2 Jones
3 Anderson
:END:
*/

class Item {
    private int key;
    private String val;

    public Item(int key, String val) {
        this.key = key;
        this.val = val;
    }

    public int key() {
        return key;
    }

    public String val() {
        return val;
    }
}

public class KeyIndexedCountingItems {
    public static Item[] sort(Item[] a, int R) {
        int N = a.length;
        Item[] aux = new Item[N];

        int[] count = new int[R+1];

        for (int i = 0; i < N; i++)
            count[a[i].key()+1]++;

        for (int r = 0; r < R; r++)
            count[r+1] += count[r];

        for (int i = 0; i < N; i++)
            aux[count[a[i].key()]++] = a[i];

        for (int i = 0; i < N; i++)
            a[i] = aux[i];

        return a;
    }

    public static void main(String[] args) {
        int R = StdIn.readInt();
        int N = StdIn.readInt();
        Item[] items = new Item[N];

        for (int i = 0; i < N; i++) {
            int key = StdIn.readInt();
            String val = StdIn.readString();
            items[i] = new Item(key, val);
        }

        sort(items, R);

        for (int i = 0; i < N; i++) {
            StdOut.println(items[i].key() + " " + items[i].val());
        }
    }
}
