import java.util.*;

public class Puzzle {

    static class Tile {
        String up, left, down, right;
        String raw;

        Tile(String u, String l, String d, String r, String raw) {
            up = u;
            left = l;
            down = d;
            right = r;
            this.raw = raw;
        }
    }

    static Tile[][] grid;
    static boolean[] used;
    static List<Tile> tiles = new ArrayList<>();
    static boolean solved = false;
    static int N;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }

            String raw = line;
            if (line.startsWith("(") && line.endsWith(")")) {
                line = line.substring(1, line.length() - 1);
            }

            String[] p = line.split(",");
            if (p.length >= 4) {
                tiles.add(new Tile(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), raw));
            }
        }

        if (tiles.isEmpty())
            return;

        N = (int) Math.sqrt(tiles.size());
        grid = new Tile[N][N];
        used = new boolean[tiles.size()];

        dfs(0);
    }

    static void dfs(int pos) {
        if (solved)
            return;

        if (pos == tiles.size()) {
            print();
            solved = true;
            return;
        }

        int r = pos / N;
        int c = pos % N;

        for (int i = 0; i < tiles.size(); i++) {
            if (used[i])
                continue;

            Tile t = tiles.get(i);
            if (!valid(t, r, c))
                continue;

            used[i] = true;
            grid[r][c] = t;

            dfs(pos + 1);

            if (solved)
                return;

            used[i] = false;
            grid[r][c] = null;
        }
    }

    static boolean valid(Tile t, int r, int c) {
        if (r == 0) {
            if (!t.up.equals("black"))
                return false;
        } else {
            if (!grid[r - 1][c].down.equals(t.up))
                return false;
        }

        if (c == 0) {
            if (!t.left.equals("black"))
                return false;
        } else {
            if (!grid[r][c - 1].right.equals(t.left))
                return false;
        }

        if (r == N - 1) {
            if (!t.down.equals("black"))
                return false;
        } else {
            if (t.down.equals("black"))
                return false;
        }

        if (c == N - 1) {
            if (!t.right.equals("black"))
                return false;
        } else {
            if (t.right.equals("black"))
                return false;
        }

        return true;
    }

    static void print() {
        for (int r = 0; r < N; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < N; c++) {
                sb.append(grid[r][c].raw);
                if (c < N - 1)
                    sb.append(";");
            }
            System.out.println(sb);
        }
    }
}