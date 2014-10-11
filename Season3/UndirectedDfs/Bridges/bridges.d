import std.c.stdio;
import std.algorithm;

void main() {
    freopen("bridges.in", "r", stdin);
    freopen("bridges.out", "w", stdout);

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
        id[u] ~= i;
        g[v] ~= u;
        id[v] ~= i;
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

        foreach (i; 0 .. g[v].length) {
            int to = g[v][i];
            if (p == to) continue;
            if (used[to]) dp[v] = min(dp[v], en[to]);
            else {
                dp[v] = min(dp[v], dfs(to, v));
                if (dp[to] > en[v]) {
                    ans ~= id[v][i];
                }
            }
        }

        return dp[v];
    }

    foreach (v; 0 .. n) if (!used[v]) dfs(v);

    sort(ans);
    printf("%d\n", ans.length);
    foreach (x; ans) printf("%d ", x + 1);
    puts("");
}
