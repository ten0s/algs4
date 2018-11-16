import edu.princeton.cs.algs4.*;

// $ echo "( 1 + 2 )" | make run CLASS=Evaluate
// 3.0

// $ echo "( ( 1 + 2 ) * ( 3 - 4 ) )" | make run CLASS=Evaluate
// -3.0

// $ echo "( ( 1 + 2 ) * ( ( 3 - 4 ) * ( 5 - 6 ) ) )" | make run CLASS=Evaluate
// 3.0

// $ echo "( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )" | make run CLASS=Evaluate
// 101.0

public class Evaluate {
    // evaluate infix expression
    public static void main(String[] args) {
        DynArrayStack<String> ops = new DynArrayStack<>();
        DynArrayStack<Double> vals = new DynArrayStack<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if      (s.equals("("))            ;
            else if (s.equals("+")) ops.push(s);
            else if (s.equals("-")) ops.push(s);
            else if (s.equals("*")) ops.push(s);
            else if (s.equals("/")) ops.push(s);
            else if (s.equals(")")) {
                String op = ops.pop();
                double v = vals.pop();
                if      (op.equals("+")) v = vals.pop() + v;
                else if (op.equals("-")) v = vals.pop() - v;
                else if (op.equals("*")) v = vals.pop() * v;
                else if (op.equals("/")) v = vals.pop() / v;
                vals.push(v);
            } else {
                vals.push(Double.parseDouble(s));
            }
        }
        StdOut.println(vals.pop());
    }
}
