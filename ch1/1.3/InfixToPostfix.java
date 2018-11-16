import edu.princeton.cs.algs4.*;

// Ex 1.3.10

// $ echo "( 1 + 2 )" | make run CLASS=InfixToPostfix
// 1 2 -

// $ echo "( ( 1 + 2 ) * ( 3 - 4 ) )" | make run CLASS=InfixToPostfix
// 1 2 + 3 4 - *

// $ echo "( ( 1 + 2 ) * ( ( 3 - 4 ) * ( 5 - 6 ) ) )" | make run CLASS=InfixToPostfix
// 1 2 + 3 4 - 5 6 - * *

// $ echo "( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )" | make run CLASS=InfixToPostfix
// 1 2 3 + 4 5 * * +

public class InfixToPostfix {
    // convert expression from infix to postfix form
    public static void main(String[] args) {
        DynArrayStack<String> stack = new DynArrayStack<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if      (s.equals("(")) {}
            else if (s.equals(")")) {
                String val2 = stack.pop();
                String op = stack.pop();
                String val1 = stack.pop();
                String expr = val1 + " " + val2 + " " + op;
                stack.push(expr);
            } else {
                stack.push(s);
            }
        }
        StdOut.println(stack.pop());
    }
}
