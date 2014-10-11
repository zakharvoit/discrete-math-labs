import std.c.stdio;
import std.algorithm;

void main() {
    freopen("bicone.in", "r", stdin);
    freopen("bicone.out", "w", stdout);

    int n, m;
    scanf("%d%d", &n, &m);

    auto g = new int[][](n);
    auto id = new int[][](n);

    foreach (i; 0 .. m) {
        int u, v;
        scanf("%d%d", &u, &v);
        --u;
        --v;
        g[u] ~= v;
        g[v] ~= u;
    }

    auto used = new bool[n];
    int timer = 0;
    int[] en = new int[n];
    int[] dp = new int[n];
    int[] ans;

    int dfs(int v, int p = -1) {
        used[v] = true;
        en[v] = timer++;
        dp[v] = en[v];

        foreach (to; g[v]) {
            if (p == to) continue;
            if (used[to]) dp[v] = min(dp[v], en[to]);
            else dp[v] = min(dp[v], dfs(to, v));
        }

        return dp[v];
    }

    foreach (v; 0 .. n) if (!used[v]) dfs(v);

    int[] color = new int[n];
    fill(color, -1);

    int cur = 0;
    void dfs2(int v, int c) {
        color[v] = c;
        foreach (to; g[v]) {
            if (color[to] == -1) {
                int c2 = c;
                if (dp[to] > en[v]) c2 = cur++;
                dfs2(to, c2);
            }
        }
    }

    foreach (v; 0 .. n) if (color[v] == -1) dfs2(v, cur++);
    printf("%d\n", cur);
    foreach (x; color) printf("%d\n", x + 1);
}
