import java.io.*;
import java.util.*;

public class PhiX174 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("/home/coder/project/data_set.txt"));

        List<String> reads = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                reads.add(line.trim());
            }
        }

        if (reads.isEmpty())
            return;

        int k = reads.get(0).length();

        Map<String, Deque<String>> graph = new HashMap<>();

        for (String kmer : reads) {
            String prefix = kmer.substring(0, k - 1);
            String suffix = kmer.substring(1);

            graph.putIfAbsent(prefix, new ArrayDeque<>());
            graph.get(prefix).add(suffix);
        }

        // Find a safe starting node belonging to the main, complex genomic component
        String start = null;
        int maxUniqueChars = 0;

        for (String kmer : reads) {
            String prefix = kmer.substring(0, k - 1);

            // Count unique characters in this prefix
            Set<Character> uniqueChars = new HashSet<>();
            for (char c : prefix.toCharArray()) {
                uniqueChars.add(c);
            }

            // We want a node with a high variety of nucleotides (ideally 3 or 4 different
            // letters)
            if (uniqueChars.size() > maxUniqueChars) {
                maxUniqueChars = uniqueChars.size();
                start = prefix;
                if (maxUniqueChars == 4) { // Perfect mix found (A, C, G, and T)
                    break;
                }
            }
        }

        if (start == null)
            return;

        List<String> path = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            String node = stack.peek();

            if (graph.containsKey(node) && !graph.get(node).isEmpty()) {
                stack.push(graph.get(node).poll());
            } else {
                path.add(stack.pop());
            }
        }

        Collections.reverse(path);

        System.out.println(reconstruct(path, k));
    }

    static String reconstruct(List<String> path, int k) {
        StringBuilder sb = new StringBuilder();

        sb.append(path.get(0));
        for (int i = 1; i < path.size(); i++) {
            sb.append(path.get(i).charAt(k - 2));
        }

        String fullCycle = sb.toString();
        return fullCycle.substring(0, fullCycle.length() - (k - 1));
    }
}