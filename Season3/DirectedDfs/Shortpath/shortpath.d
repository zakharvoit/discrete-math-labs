import std.c.stdio;
import std.typecons;

void main() {
    freopen("shortpath.in", "r", stdin);
    freopen("shortpath.out", "w", stdout);

    int n, m, s, t;
    scanf("%d%d%d%d", &n, &m, &s, &t);

    --s; --t;
    auto g = new Tuple!(int, int)[][](n);
    foreach (i; 0 .. m) {
        int u, v, w;
        scanf("%d%d%d", &u, &v, &w);
        --u; --v;
        g[u] ~= tuple(v, w);
    }

    int[] dp = new int[n];
    bool[] used = new bool[n];

    used[t] = true;

    int dfs(int v) {
        if (used[v]) return dp[v];
        dp[v] = -1;

        used[v] = true;

        foreach (p; g[v]) {
            int to = p[0];
            int w = p[1];
            int res = dfs(to);
            if (res == -1) continue;
            res += w;
            if (dp[v] == -1 || dp[v] > res) dp[v] = res;
        }

        return dp[v];
    }

    int res = dfs(s);
    if (res == -1) puts("Unreachable");
    else printf("%d\n", res);
}
