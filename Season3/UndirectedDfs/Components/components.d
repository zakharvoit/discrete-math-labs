import std.c.stdio;
import std.c.stdlib;
import std.algorithm;

void main() {
    freopen("components.in", "r", stdin);
    freopen("components.out", "w", stdout);

    int n, m;
    scanf("%d%d", &n, &m);

    auto g = new int[][](n);

    foreach (i; 0 .. m) {
        int u, v;
        scanf("%d%d", &u, &v);
        --u;
        --v;
        g[u] ~= v;
        g[v] ~= u;
    }

    int[] color = new int[n];
    int cur = 0;
    void dfs(int v, int p = -1) {
        color[v] = cur;
        foreach (to; g[v]) {
            if (to == p) continue;
            if (color[to] == 0) dfs(to, v);
        }
    }

    foreach (v; 0 .. n) {
        if (color[v] == 0) {
            cur++;
            dfs(v);
        }
    }

    printf("%d\n", cur);
    foreach (x; color) printf("%d\n", x);
}
