import edu.princeton.cs.algs4.*;

// Ex 1.3.11

// $ echo "1 2 +" | make run CLASS=EvaluatePostfix
// 3.0

// $ echo "1 2 + 3 4 - *" | make run CLASS=EvaluatePostfix
// -3.0

// $ echo "1 2 + 3 4 - 5 6 - * *" | make run CLASS=EvaluatePostfix
// 3.0

// $ echo "1 2 3 + 4 5 * * +" | make run CLASS=EvaluatePostfix
// 101.0

// $ echo "( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )" | make run CLASS=InfixToPostfix | make run CLASS=EvaluatePostfix
// 101.0

public class EvaluatePostfix {
    // evaluate postfix expression
    public static void main(String[] args) {
        DynArrayStack<Double> vals = new DynArrayStack<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("+")) {
                vals.push(vals.pop() + vals.pop());
            } else if (s.equals("-")) {
                Double v2 = vals.pop();
                Double v1 = vals.pop();
                vals.push(v1 - v2);
            } else if (s.equals("*")) {
                vals.push(vals.pop() * vals.pop());
            } else if (s.equals("/")) {
                Double v2 = vals.pop();
                Double v1 = vals.pop();
                vals.push(v1 / v2);
            } else {
                vals.push(Double.parseDouble(s));
            }
        }
        StdOut.println(vals.pop());
    }
}
