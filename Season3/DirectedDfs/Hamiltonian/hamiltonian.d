import std.c.stdio;
import std.c.stdlib;
import std.algorithm;

void main() {
    freopen("hamiltonian.in", "r", stdin);
    freopen("hamiltonian.out", "w", stdout);

    int n, m;
    scanf("%d%d", &n, &m);

    auto g = new int[][](n);

    int[] parent = new int[n];
    foreach (i; 0 .. m) {
        int u, v;
        scanf("%d%d", &u, &v);
        --u; --v;
        g[u] ~= v;
        ++parent[v];
    }

    auto used = new bool[n];
    auto dp = new int[n];
    int dfs(int v) {
        if (used[v]) return dp[v];
        used[v] = true;
        int ans = 0;
        foreach (to; g[v]) {
            ans = max(ans, dfs(to));
        }

        return dp[v] = ans + 1;
    }

    int ans = 0;
    foreach (i; 0 .. n) {
        ans = max(ans, dfs(i));
    }

    if (ans == n) {
        puts("YES");
    } else {
        puts("NO");
    }
}
