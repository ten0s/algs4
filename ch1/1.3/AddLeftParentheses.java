import edu.princeton.cs.algs4.*;

// Ex 1.3.9

// $ echo "1 + 2 )" | make run CLASS=AddLeftParentheses
// ( 1 + 2 )

// $ echo "1 + 2 ) * 3 - 4 ) )" | make run CLASS=AddLeftParentheses
// ( ( 1 + 2 ) * ( 3 - 4 ) )

// $ echo "1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )" | make run CLASS=AddLeftParentheses
// ( ( 1 + 2 ) * ( ( 3 - 4 ) * ( 5 - 6 ) ) )

public class AddLeftParentheses {
    // insert left parentheses to the expression
    public static void main(String[] args) {

        DynArrayStack<String> stack = new DynArrayStack<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals(")")) {
                String val2 = stack.pop();
                String op = stack.pop();
                String val1 = stack.pop();
                String expr = "( " + val1 + " " + op + " " + val2 + " )";
                stack.push(expr);
            } else {
                stack.push(s);
            }
        }
        StdOut.println(stack.pop());
    }
}
