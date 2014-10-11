import std.c.stdio;
import std.algorithm;
import std.array;

void main() {
    freopen("points.in", "r", stdin);
    freopen("points.out", "w", stdout);

    int n, m;
    scanf("%d%d", &n, &m);

    auto g = new int[][](n);

    foreach (i; 0 .. m) {
        int u, v;
        scanf("%d %d", &u, &v);
        --u; --v;
        g[u] ~= v;
        g[v] ~= u;
    }

    int[] dp = new int[n];
    int[] en = new int[n];
    int[] ans;
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
                if (dp[to] >= en[v] && p != -1) ans ~= v;
            }
        }

        if (p == -1 && cnt > 1) ans ~= v;
        return dp[v];
    }

    foreach (v; 0 .. n) if (dp[v] == 0) dfs(v);

    sort(ans);
    ans = array(uniq(ans));
    printf("%d\n", ans.length);
    foreach (v; ans) printf("%d\n", v + 1);
}
