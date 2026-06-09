import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GenomeAssemblyErrorFree {
    static List<String> reads;

    public static void main(String[] args) throws IOException {
        new FastScanner();
        FastScanner.readInput();
        System.out.println(buildGenome(reads));
    }

    static String buildGenome(List<String> reads) {
        if (reads == null || reads.isEmpty())
            return "";

        List<String> uniqueReads = new ArrayList<>();
        for (String r : reads) {
            if (!uniqueReads.contains(r)) {
                uniqueReads.add(r);
            }
        }
        int n = uniqueReads.size();

        int minLength = Integer.MAX_VALUE;
        for (String read : uniqueReads) {
            minLength = Math.min(minLength, read.length());
        }
        int k = Math.min(12, minLength - 1);
        if (k < 1)
            k = 1;

        Map<String, List<Integer>> prefixIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String prefixstr = uniqueReads.get(i).substring(0, k);
            prefixIndex.computeIfAbsent(prefixstr, x -> new ArrayList<>()).add(i);
        }

        int startIdx = 0;
        for (int i = 0; i < n; i++) {
            if (uniqueReads.get(i).startsWith("ACG")) {
                startIdx = i;
                break;
            }
        }

        List<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[n];

        path.add(startIdx);
        visited[startIdx] = true;

        if (dfs(startIdx, uniqueReads, prefixIndex, visited, path, n, k)) {
            StringBuilder genome = new StringBuilder(uniqueReads.get(path.get(0)));
            for (int i = 1; i < n; i++) {
                int prev = path.get(i - 1);
                int next = path.get(i);
                int overlap = calculateOverlap(uniqueReads.get(prev), uniqueReads.get(next));
                genome.append(uniqueReads.get(next).substring(overlap));
            }
            int finalOverlap = calculateOverlap(uniqueReads.get(path.get(n - 1)), uniqueReads.get(path.get(0)));
            if (finalOverlap > 0) {
                return genome.substring(0, genome.length() - finalOverlap);
            }
            return genome.toString();
        }

        return "";
    }

    static boolean dfs(int curr, List<String> reads, Map<String, List<Integer>> prefixIndex, boolean[] visited,
            List<Integer> path, int n, int k) {
        if (path.size() == n) {
            return calculateOverlap(reads.get(curr), reads.get(path.get(0))) > 0;
        }

        String curString = reads.get(curr);
        List<OverlapCandidate> candidates = new ArrayList<>();

        for (int p = 0; p <= curString.length() - k; p++) {
            String prToFind = curString.substring(p, p + k);
            if (prefixIndex.containsKey(prToFind)) {
                for (int idx : prefixIndex.get(prToFind)) {
                    if (visited[idx])
                        continue;
                    String nxtread = reads.get(idx);
                    int currOverLap = curString.length() - p;
                    if (nxtread.length() >= currOverLap && nxtread.startsWith(curString.substring(p))) {
                        candidates.add(new OverlapCandidate(idx, currOverLap));
                    }
                }
            }
            if (!candidates.isEmpty()) {
                break;
            }
        }

        if (candidates.isEmpty()) {
            for (int c = 0; c < n; c++) {
                if (!visited[c]) {
                    int overlap = calculateOverlap(curString, reads.get(c));
                    if (overlap > 0) {
                        candidates.add(new OverlapCandidate(c, overlap));
                    }
                }
            }
            candidates.sort((c1, c2) -> Integer.compare(c2.overlap, c1.overlap));
        }

        for (OverlapCandidate cand : candidates) {
            if (!visited[cand.index]) {
                visited[cand.index] = true;
                path.add(cand.index);

                if (dfs(cand.index, reads, prefixIndex, visited, path, n, k)) {
                    return true;
                }

                path.remove(path.size() - 1);
                visited[cand.index] = false;
            }
        }

        if (candidates.isEmpty()) {
            for (int c = 0; c < n; c++) {
                if (!visited[c]) {
                    visited[c] = true;
                    path.add(c);
                    if (dfs(c, reads, prefixIndex, visited, path, n, k)) {
                        return true;
                    }
                    path.remove(path.size() - 1);
                    visited[c] = false;
                }
            }
        }

        return false;
    }

    static int calculateOverlap(String a, String b) {
        int maxLen = Math.min(a.length(), b.length());
        for (int len = maxLen - 1; len > 0; len--) {
            if (a.endsWith(b.substring(0, len))) {
                return len;
            }
        }
        return 0;
    }

    static class OverlapCandidate {
        int index;
        int overlap;

        OverlapCandidate(int index, int overlap) {
            this.index = index;
            this.overlap = overlap;
        }
    }

    static class FastScanner {
        static BufferedReader br;
        static String line;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
            reads = new ArrayList<>();
        }

        private static void readInput() throws IOException {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    reads.add(line);
                } else {
                    break;
                }
            }
        }
    }
}