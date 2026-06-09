import java.util.*;
import java.io.*;

public class SuffixTree {

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

    class Node {
        Map<String, Node> children = new HashMap<>();
    }

    public List<String> computeSuffixTreeEdges(String text) {
        List<String> result = new ArrayList<>();
        Node root = new Node();

        for (int i = 0; i < text.length(); i++) {
            String suffix = text.substring(i);
            Node current = root;

            while (true) {
                boolean found = false;

                for (String edge : new ArrayList<>(current.children.keySet())) {
                    int l = commonPrefix(edge, suffix);

                    if (l > 0) {
                        if (l < edge.length()) {
                            // Split edge
                            Node oldChild = current.children.get(edge);
                            Node newNode = new Node();

                            String remainingEdge = edge.substring(l);
                            newNode.children.put(remainingEdge, oldChild);

                            current.children.remove(edge);
                            current.children.put(edge.substring(0, l), newNode);

                            current = newNode;
                        } else {
                            current = current.children.get(edge);
                        }

                        suffix = suffix.substring(l);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    current.children.put(suffix, new Node());
                    break;
                }

                if (suffix.length() == 0)
                    break;
            }
        }

        collectEdges(root, result);
        return result;
    }

    private int commonPrefix(String a, String b) {
        int len = Math.min(a.length(), b.length());
        for (int i = 0; i < len; i++) {
            if (a.charAt(i) != b.charAt(i))
                return i;
        }
        return len;
    }

    private void collectEdges(Node node, List<String> result) {
        for (Map.Entry<String, Node> entry : node.children.entrySet()) {
            result.add(entry.getKey());
            collectEdges(entry.getValue(), result);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }

    public void print(List<String> edges) {
        for (String edge : edges) {
            System.out.println(edge);
        }
    }

    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }
}
