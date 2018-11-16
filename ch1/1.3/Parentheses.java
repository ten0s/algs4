import edu.princeton.cs.algs4.*;

// Exercise 1.3.4

// $ make run CLASS=Parentheses

// $ echo -e "[()]{}{[()()]()}\n[(])\n" | make run CLASS=Parentheses
// true
// false

public class Parentheses {
    public static boolean isBalanced(String str) {
        DynArrayStack<Character> stack = new DynArrayStack<>();
        try {
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                switch (ch) {
                case '[':
                case '(':
                case '{':
                    stack.push(ch);
                    break;
                case ']':
                    if (stack.pop() != '[') throw new Exception();
                    break;
                case ')':
                    if (stack.pop() != '(') throw new Exception();
                    break;
                case '}':
                    if (stack.pop() != '{') throw new Exception();
                    break;
                default:
                    throw new Exception();
                }
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            String line = StdIn.readString();
            boolean res = isBalanced(line);
            StdOut.println(res);
        }
    }
}
