import java.io.*;
import java.util.*;

public class Circulation {

    static class Edge {
        int to, rev, cap, originalCap;

        Edge(int to, int rev, int cap) {
            this.to = to;
            this.rev = rev;
            this.cap = cap;
            this.originalCap = cap;
        }
    }

    static class Dinic {
        List<Edge>[] graph;
        int[] level;
        int[] ptr;

        @SuppressWarnings("unchecked")
        Dinic(int n) {
            graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }
            level = new int[n];
            ptr = new int[n];
        }

        int addEdge(int u, int v, int cap) {
            Edge fwd = new Edge(v, graph[v].size(), cap);
            Edge rev = new Edge(u, graph[u].size(), 0);

            graph[u].add(fwd);
            graph[v].add(rev);

            return graph[u].size() - 1;
        }

        boolean bfs(int s, int t) {
            Arrays.fill(level, -1);

            Queue<Integer> q = new ArrayDeque<>();
            q.add(s);
            level[s] = 0;

            while (!q.isEmpty()) {
                int v = q.poll();

                for (Edge e : graph[v]) {
                    if (e.cap > 0 && level[e.to] == -1) {
                        level[e.to] = level[v] + 1;
                        q.add(e.to);
                    }
                }
            }

            return level[t] != -1;
        }

        int dfs(int v, int t, int pushed) {
            if (pushed == 0)
                return 0;
            if (v == t)
                return pushed;

            for (; ptr[v] < graph[v].size(); ptr[v]++) {
                Edge e = graph[v].get(ptr[v]);

                if (level[e.to] != level[v] + 1 || e.cap == 0)
                    continue;

                int tr = dfs(e.to, t, Math.min(pushed, e.cap));

                if (tr == 0)
                    continue;

                e.cap -= tr;
                graph[e.to].get(e.rev).cap += tr;

                return tr;
            }

            return 0;
        }

        int maxFlow(int s, int t) {
            int flow = 0;

            while (bfs(s, t)) {
                Arrays.fill(ptr, 0);

                int pushed;
                while ((pushed = dfs(s, t, Integer.MAX_VALUE)) > 0) {
                    flow += pushed;
                }
            }

            return flow;
        }
    }

    static class OriginalEdge {
        int from, to;
        int lower;
        int edgeIndex;

        OriginalEdge(int from, int to, int lower, int edgeIndex) {
            this.from = from;
            this.to = to;
            this.lower = lower;
            this.edgeIndex = edgeIndex;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int SS = 0;
        int TT = n + 1;

        Dinic dinic = new Dinic(n + 2);

        int[] balance = new int[n + 1];

        OriginalEdge[] original = new OriginalEdge[m];

        for (int i = 0; i < m; i++) {

            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int lower = Integer.parseInt(st.nextToken());
            int cap = Integer.parseInt(st.nextToken());

            balance[u] -= lower;
            balance[v] += lower;

            int edgeIdx = dinic.addEdge(u, v, cap - lower);

            original[i] = new OriginalEdge(u, v, lower, edgeIdx);
        }

        int required = 0;

        for (int v = 1; v <= n; v++) {

            if (balance[v] > 0) {
                dinic.addEdge(SS, v, balance[v]);
                required += balance[v];
            } else if (balance[v] < 0) {
                dinic.addEdge(v, TT, -balance[v]);
            }
        }

        int flow = dinic.maxFlow(SS, TT);

        if (flow != required) {
            System.out.println("NO");
            return;
        }

        StringBuilder out = new StringBuilder();
        out.append("YES\n");

        for (int i = 0; i < m; i++) {

            OriginalEdge oe = original[i];

            Edge e = dinic.graph[oe.from].get(oe.edgeIndex);

            int used = e.originalCap - e.cap;

            out.append(oe.lower + used).append('\n');
        }

        System.out.print(out);
    }
}