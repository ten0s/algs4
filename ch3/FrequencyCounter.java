import edu.princeton.cs.algs4.*;

// $ time make run CLASS=FrequencyCounter ARGS="BST 10" < ../data/leipzig1M.txt
// government 24763
//
// real	0m18.866s
// user	0m20.920s
// sys	0m0.756s

// $ time make run CLASS=FrequencyCounter ARGS="RedBlackBST 10" < ../data/leipzig1M.txt
// government 24763

// real	0m19.178s
// user	0m21.188s
// sys	0m0.704s

public class FrequencyCounter {
    private static ST<String, Integer> makeST(String name) {
        if (name.equals("BST")) return new BST<String, Integer>();
        if (name.equals("RedBlackBST")) return new RedBlackBST<String, Integer>();
        throw new IllegalArgumentException("Unknown ST: " + name);
    }

    public static void main(String[] args) {
        String stName = args[0];
        int minLen = Integer.parseInt(args[1]);
        ST<String, Integer> st = makeST(stName);
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (word.length() >= minLen) {
                Integer value = st.get(word);
                if (value == null) st.put(word, 1);
                else               st.put(word, value + 1);
            }
        }

        Integer maxVal = 0;
        String maxWord = "";
        Integer val;
        for (String word : st.keys()) {
            val = st.get(word);
            if (val > maxVal) {
                maxVal = val;
                maxWord = word;
            }
        }
        StdOut.println(maxWord + " " + maxVal);
    }
}
