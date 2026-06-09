import java.io.*;
import java.util.*;

public class BubbleDetection {

    static class Path {
        List<String> nodes;
        Path(List<String> nodes) {
            this.nodes = new ArrayList<>(nodes);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = br.readLine();
        if (firstLine == null || firstLine.isEmpty()) return;

        String[] tokens = firstLine.trim().split("\\s+");
        int k = Integer.parseInt(tokens[0]);
        int t = Integer.parseInt(tokens[1]);

        Map<String, Set<String>> adj = new HashMap<>();
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            for (int i = 0; i + k <= line.length(); i++) {
                String kmer = line.substring(i, i + k);
                String u = kmer.substring(0, k - 1);
                String v = kmer.substring(1);
                adj.putIfAbsent(u, new HashSet<>());
                adj.get(u).add(v);
            }
        }

        int bubbleCount = 0;

        for (String v : adj.keySet()) {
            if (adj.get(v).size() < 2) {
                continue;
            }

            Map<String, List<Path>> pathsToTarget = new HashMap<>();
            Queue<Path> queue = new LinkedList<>();
            
            List<String> startPath = new ArrayList<>();
            startPath.add(v);
            queue.add(new Path(startPath));

            while (!queue.isEmpty()) {
                Path currentPath = queue.poll();
                List<String> pathNodes = currentPath.nodes;
                int currentLength = pathNodes.size() - 1; 

                String currNode = pathNodes.get(pathNodes.size() - 1);

                if (currentLength > 0) {
                    pathsToTarget.putIfAbsent(currNode, new ArrayList<>());
                    pathsToTarget.get(currNode).add(currentPath);
                }

                if (currentLength < t) {
                    Set<String> neighbors = adj.get(currNode);
                    if (neighbors != null) {
                        for (String neighbor : neighbors) {
                            if (!pathNodes.contains(neighbor)) { 
                                List<String> nextNodes = new ArrayList<>(pathNodes);
                                nextNodes.add(neighbor);
                                queue.add(new Path(nextNodes));
                            }
                        }
                    }
                }
            }

            for (String w : pathsToTarget.keySet()) {
                List<Path> paths = pathsToTarget.get(w);
                if (paths.size() < 2) continue;

                for (int i = 0; i < paths.size(); i++) {
                    for (int j = i + 1; j < paths.size(); j++) {
                        if (isDisjoint(paths.get(i), paths.get(j))) {
                            bubbleCount++;
                        }
                    }
                }
            }
        }

        System.out.println(bubbleCount);
    }

    private static boolean isDisjoint(Path p1, Path p2) {
        Set<String> internalNodes = new HashSet<>(p1.nodes);
        
        internalNodes.remove(p1.nodes.get(0));
        internalNodes.remove(p1.nodes.get(p1.nodes.size() - 1));

        for (int i = 1; i < p2.nodes.size() - 1; i++) {
            if (internalNodes.contains(p2.nodes.get(i))) {
                return false; 
            }
        }
        return true;
    }
}