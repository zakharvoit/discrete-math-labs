import std.c.stdio;
import std.c.stdlib;

void main()
{
    freopen("cycle.in", "r", stdin);
    freopen("cycle.out", "w", stdout);

    int n, m;
    scanf("%d%d", &n, &m);

    int[][] g = new int[][](n);
    foreach (i; 0 .. m) {
        int u, v;
        scanf("%d%d", &u, &v);
        --u; --v;
        g[u] ~= v;
    }

    int[] p = new int[n];
    int[] used = new int[n];
    int[] ans;

    void dfs(int v) {
        if (used[v] == 1) {
            puts("YES");
            foreach (x; ans[p[v] .. $]) {
                printf("%d ", x + 1);
            }
            puts("");
            exit(0);
        }
        if (used[v] == 2) return;
        used[v] = 1;
        p[v] = cast(int) ans.length;
        ans ~= v;
        foreach (to; g[v]) {
            dfs(to);
        }
        --ans.length;
        used[v] = 2;
    }

    foreach (i; 0 .. n) {
        dfs(i);
    }

    puts("NO");
}
