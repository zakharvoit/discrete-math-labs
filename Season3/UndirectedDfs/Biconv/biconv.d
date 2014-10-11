import std.c.stdio;
import std.algorithm;
import std.array;

void main() {
    freopen("biconv.in", "r", stdin);
    freopen("biconv.out", "w", stdout);

    int n, m;
    scanf("%d%d", &n, &m);

    auto g = new int[][](n);
    auto id = new int[][](m);

    foreach (i; 0 .. m) {
        int u, v;
        scanf("%d %d", &u, &v);
        --u; --v;
        id[u] ~= i;
        id[v] ~= i;
        g[u] ~= v;
        g[v] ~= u;
    }

    int[] dp = new int[n];
    int[] en = new int[n];
    int timer = 1;
    int dfs(int v, int p = -1) {
        en[v] = timer++;
        dp[v] = en[v];

        int cnt = 0;
        foreach (to; g[v]) {
            if (to == p) continue;
            if (dp[to] != 0) dp[v] = min(dp[v], en[to]);
            else {
                ++cnt;
                dp[v] = min(dp[v], dfs(to, v));
            }
        }

        return dp[v];
    }

    foreach (v; 0 .. n) if (dp[v] == 0) dfs(v);

    int cur = 0;
    int[] color = new int[m];
    bool[] used = new bool[n];

    void dfs2(int v, int c = -1, int p = -1) {
        used[v] = true;

        foreach (i; 0 .. g[v].length) {
            int to = g[v][i];
            if (to == p) continue;
            if (!used[to]) {
                int c2 = c;
                if (dp[to] >= en[v]) c2 = cur++;
                color[id[v][i]] = c2;
                dfs2(to, c2, v);
            } else if (used[to] && en[to] < en[v]) {
                color[id[v][i]] = c;
            }
        }
    }

    foreach (v; 0 .. n) if (!used[v]) dfs2(v);

    printf("%d\n", cur);
    foreach (x; color) printf("%d\n", x + 1);
}
