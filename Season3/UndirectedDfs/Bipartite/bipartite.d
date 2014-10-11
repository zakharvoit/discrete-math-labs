import std.c.stdio;
import std.c.stdlib;
import std.algorithm;

void main() {
    freopen("bipartite.in", "r", stdin);
    freopen("bipartite.out", "w", stdout);

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

    int[] en = new int[n];
    fill(en, -1);
    void dfs(int v, int c = 0, int p = -1) {
        en[v] = c;
        foreach (to; g[v]) {
            if (to == p) continue;
            if (en[to] == -1) dfs(to, c ^ 1, v);
            else if (en[to] == en[v]) {
                puts("NO");
                exit(0);
            }
        }
    }

    foreach (v; 0 .. n) if (en[v] == -1) dfs(v);

    puts("YES");
}
