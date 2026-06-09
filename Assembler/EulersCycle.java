import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class EulersCycle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return;
        int v = sc.nextInt();
        int e = sc.nextInt();
        
        ArrayList<Integer>[] adj = new ArrayList[v];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }
        
        int[] in = new int[v];
        int[] out = new int[v];

        for (int i = 0; i < e; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            adj[x - 1].add(y - 1);
            out[x - 1]++;
            in[y - 1]++;
        }

        solve(adj, in, out);
    }

    private static void solve(ArrayList<Integer>[] adj, int[] in, int[] out) {
        int n = adj.length;
        Deque<Integer> st = new ArrayDeque<>();
        Deque<Integer> ls = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (in[i] != out[i]) {
                System.out.print(0);
                return;
            }
        }

        int[] edgeHead = new int[n];
        int startVertex = 0;
        for (int i = 0; i < n; i++) {
            if (out[i] > 0) {
                startVertex = i;
                break;
            }
        }

        st.push(startVertex);
        int[] edges=new int[n];

        while (!st.isEmpty()) {
            int cur = st.peek();

            if  (edges[cur ]  < adj[cur].size ()) {
                int next = adj[cur].get(edges[cur]);
                st.push(next);
                edges[cur]++;
            }
            else{
                ls.offer(st.pop());
            }
        }

        System.out.println(1);
        StringBuilder sb = new StringBuilder();
        
        while (ls.size() > 1) {
            sb.append(ls.pop() + 1);
            if (ls.size() > 1) {
                sb.append(" ");
            }
        }
        System.out.println(sb.toString());
    }
}