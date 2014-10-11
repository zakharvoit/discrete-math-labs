import std.c.stdio;
import std.algorithm;

void main() {
    freopen("cond.in", "r", stdin);
    freopen("cond.out", "w", stdout);

    int n, m;
    scanf("%d%d", &n, &m);

    auto g = new int[][](n);
    auto gr = new int[][](n);
    foreach (i; 0 .. m) {
        int u, v;
        scanf("%d%d", &u, &v);
        --u; --v;
        g[u] ~= v;
        gr[v] ~= u;
    }

    int[] order;
    bool[] used = new bool[n];
    void dfs(int v) {
        used[v] = true;
        foreach (to; g[v]) {
            if (!used[to]) dfs(to);
        }
        order ~= v;
    }

    int cur;
    int[] comp = new int[n];
    void dfs2(int v) {
        used[v] = true;
        comp[v] = cur;
        foreach (to; gr[v]) {
            if (!used[to]) dfs2(to);
        }
    }

    foreach (v; 0 .. n) {
        if (!used[v]) dfs(v);
    }

    reverse(order);
    used = new bool[n];
    foreach (v; order) {
        if (!used[v]) {
            ++cur;
            dfs2(v);
        }
    }

    printf("%d\n", cur);
    foreach (i; 0 .. n) {
        printf("%d ", comp[i]);
    }
    puts("");
}
