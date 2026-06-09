import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;
import java.io.IOException;

public class SuffixArrayLong {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }
    }

    public int[] computeSuffixArray(String s) {
        int n = s.length();

        int[] order = sortCharacters(s);
        int[] classes = computeCharClasses(s, order);

        int L = 1;
        while (L < n) {
            order = sortDoubled(s, L, order, classes);
            classes = updateClasses(order, classes, L);
            L *= 2;
        }

        return order;
    }

    private int[] sortCharacters(String s) {
        int n = s.length();
        int[] order = new int[n];

        int alphabet = 256;
        int[] count = new int[alphabet];

        for (int i = 0; i < n; i++) {
            count[s.charAt(i)]++;
        }

        for (int i = 1; i < alphabet; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            count[c]--;
            order[count[c]] = i;
        }

        return order;
    }

    private int[] computeCharClasses(String s, int[] order) {
        int n = s.length();
        int[] classes = new int[n];

        classes[order[0]] = 0;

        for (int i = 1; i < n; i++) {
            if (s.charAt(order[i]) != s.charAt(order[i - 1])) {
                classes[order[i]] = classes[order[i - 1]] + 1;
            } else {
                classes[order[i]] = classes[order[i - 1]];
            }
        }

        return classes;
    }

    private int[] sortDoubled(String s, int L, int[] order, int[] classes) {
        int n = s.length();
        int[] newOrder = new int[n];
        int[] count = new int[n];

        for (int i = 0; i < n; i++) {
            count[classes[i]]++;
        }

        for (int i = 1; i < n; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int start = (order[i] - L + n) % n;
            int cl = classes[start];
            count[cl]--;
            newOrder[count[cl]] = start;
        }

        return newOrder;
    }

    private int[] updateClasses(int[] newOrder, int[] classes, int L) {
        int n = newOrder.length;
        int[] newClass = new int[n];

        newClass[newOrder[0]] = 0;

        for (int i = 1; i < n; i++) {
            int cur = newOrder[i];
            int prev = newOrder[i - 1];

            int mid = (cur + L) % n;
            int midPrev = (prev + L) % n;

            if (classes[cur] != classes[prev] ||
                classes[mid] != classes[midPrev]) {
                newClass[cur] = newClass[prev] + 1;
            } else {
                newClass[cur] = newClass[prev];
            }
        }

        return newClass;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }
}