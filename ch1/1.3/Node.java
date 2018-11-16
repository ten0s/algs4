import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.Arrays;
import java.util.Iterator;

// $ make run CLASS=Node

@SuppressWarnings("unchecked")
public class Node<T> implements Iterable<T> {
    private T item;
    private Node next;

    private Node() {}

    public static <T> Node<T> empty() {
        return null;
    }

    public static <T> Node<T> cons(T value, Node<T> list) {
        Node head = new Node();
        head.item = value;
        head.next = list;
        return head;
    }

    public static <T> T head(Node<T> list) {
        if (isEmpty(list)) {
            throw new NoSuchElementException();
        }
        return list.item;
    }

    public static <T> Node<T> tail(Node<T> list) {
        if (isEmpty(list)) {
            throw new NoSuchElementException();
        }
        return list.next;
    }

    public static <T> boolean isEmpty(Node<T> list) {
        return list == empty();
    }

    public static <T> int length(Node<T> list) {
        if (isEmpty(list)) {
            return 0;
        }
        return 1 + length(tail(list));
    }

    //
    // Java 8 Lambdas
    //

    public static <T> Node<T> filter(Predicate<T> pred, Node<T> list) {
        if (isEmpty(list)) {
            return list;
        } else {
            T head = head(list);
            Node<T> tail = tail(list);
            if (pred.test(head)) {
                return cons(head, filter(pred, tail));
            } else {
                return filter(pred, tail);
            }
        }
    }

    public static <T, R> Node<R> map(Function<T, R> fun, Node<T> list) {
        if (isEmpty(list)) {
            return empty();
        } else {
            T item = head(list);
            R res = fun.apply(item);
            return cons(res, map(fun, tail(list)));
        }
    }

    public static <T, R> R fold(BiFunction<T, R, R> fun, R init, Node<T> list) {
        if (isEmpty(list)) {
            return init;
        } else {
            T item = head(list);
            R acc = fun.apply(item, init);
            return fold(fun, acc, tail(list));
        }
    }

    // Ex 1.3.28
    public static <T extends Comparable> T max(Node<T> list) {
        if (isEmpty(list)) {
            throw new NoSuchElementException();
        }
        T me = head(list);
        Node<T> tail = tail(list);
        if (isEmpty(tail)) {
            return me;
        }
        T they = max(tail);
        if (me.compareTo(they) >= 0) {
            return me;
        } else {
            return they;
        }
    }

    public Iterator<T> iterator() {
        return new NodeIterator(this);
    }

    private class NodeIterator implements Iterator<T> {
        Node<T> c;

        public NodeIterator(Node<T> parent) {
            c = parent;
        }

        public boolean hasNext() {
            return c != empty();
        }

        public T next() {
            T item = c.item;
            c = c.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        System.out.println("int list\n===");
        Node<Integer> intList = cons(1, cons(2, cons(3, cons(4, empty()))));
        for (Integer i : intList) { System.out.println(i); }
        System.out.println("length = " + length(intList));
        System.out.println("length using fold = " + fold((i, acc) -> 1 + acc, 0, intList));
        System.out.println("max = " + max(intList));
        System.out.println("max using fold = " + fold(Math::max, Integer.MIN_VALUE, intList));
        System.out.println("sum using fold = " + fold(Integer::sum, 0, intList));
        System.out.println("reverse using fold");
        Node<Integer> reversedIntList = fold(Node::cons, empty(), intList);
        for (Integer i : reversedIntList) { System.out.println(i); }
        System.out.println("");

        System.out.println("even only\n===");
        Node<Integer> evenIntList = filter(i -> i % 2 == 0, intList);
        for (Integer i : evenIntList) { System.out.println(i); }
        System.out.println("");

        System.out.println("doubled\n===");
        Node<Integer> doubledIntList = map(i -> 2 * i, intList);
        for (Integer i : doubledIntList) { System.out.println(i); }
        System.out.println("");

        System.out.println("to binary\n===");
        Node<String> binIntList = map(i -> IntToBin.toBinary(i), intList);
        for (String s : binIntList) { System.out.println(s); }
        System.out.println("");
    }
}
