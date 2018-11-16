import edu.princeton.cs.algs4.*;

// $ echo "123145123" | make run CLASS=MoveToFront
// 32154

public class MoveToFront {
    public static void main(String[] args) {
        Bag<Character> bag = new Bag<>();
        while (!StdIn.isEmpty()) {
            char ch = StdIn.readChar();
            bag.remove(ch);
            bag.add(ch);
        }
        for (char ch : bag) {
            StdOut.print(ch);
        }
        StdOut.println();
    }
}
