import std.c.stdio;
import std.c.stdlib;
import std.algorithm;

void main() {
    freopen("topsort.in", "r", stdin);
    freopen("topsort.out", "w", stdout);

    int n, m;
    scanf("%d%d", &n, &m);

    auto g = new int[][](n);
    foreach (i; 0 .. m) {
        int u, v;
        scanf("%d%d", &u, &v);
        --u; --v;
        g[u] ~= v;
    }

    int[] order;
    int[] color = new int[n];
    void dfs(int v) {
        if (color[v] == 1) {
            puts("-1");
            exit(0);
        }

        color[v] = 1;
        foreach (to; g[v]) {
            if (color[to] != 2) dfs(to);
        }

        color[v] = 2;

        order ~= v;
    }

    foreach (i; 0 .. n) {
        if (color[i] == 0) {
            dfs(i);
        }
    }

    reverse(order);

    foreach (x; order) {
        printf("%d ", x + 1);
    }
    puts("");
}
