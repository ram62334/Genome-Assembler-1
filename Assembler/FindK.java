import java.io.*;
import java.util.*;

public class FindK {

    static List<String> reads = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            reads.add(line.trim());
        }
        System.out.println(findK());
    }

    static int findK() {
        for (int k = 100; k >= 1; k--) {
            if (validK(k))
                return k;
        }
        return 1;
    }

    static boolean validK(int k) {
        Map<String, Set<String>> adj = new HashMap<>();
        Map<String, Integer> indeg = new HashMap<>();
        Map<String, Integer> outdeg = new HashMap<>();
        Set<String> nodes = new HashSet<>();

        for (String read : reads) {
            if (read.length() < k)
                continue;

            for (int i = 0; i + k <= read.length(); i++) {
                String kmer = read.substring(i, i + k);
                String u = kmer.substring(0, k - 1);
                String v = kmer.substring(1);

                adj.putIfAbsent(u, new HashSet<>());
                if (adj.get(u).add(v)) {
                    nodes.add(u);
                    nodes.add(v);
                    outdeg.put(u, outdeg.getOrDefault(u, 0) + 1);
                    indeg.put(v, indeg.getOrDefault(v, 0) + 1);
                }
            }
        }

        if (nodes.isEmpty())
            return false;

        for (String node : nodes) {
            int out = outdeg.getOrDefault(node, 0);
            int in = indeg.getOrDefault(node, 0);
            if (out != 1 || in != 1) {
                return false;
            }
        }

        String startNode = nodes.iterator().next();
        String currentNode = startNode;
        int visitedCount = 0;

        while (currentNode != null) {
            visitedCount++;
            Set<String> neighbors = adj.get(currentNode);
            if (neighbors == null || neighbors.isEmpty()) {
                break;
            }
            String nextNode = neighbors.iterator().next();
            if (nextNode.equals(startNode)) {
                break;
            }
            if (visitedCount > nodes.size()) {
                return false;
            }
            currentNode = nextNode;
        }

        return visitedCount == nodes.size();
    }
}