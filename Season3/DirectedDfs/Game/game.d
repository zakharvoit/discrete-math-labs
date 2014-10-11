import std.c.stdio;

void main() {
    freopen("game.in", "r", stdin);
    freopen("game.out", "w", stdout);

    int n, m, s;
    scanf("%d%d%d", &n, &m, &s);

    --s;
    int[][] g = new int[][](n);

    foreach (i; 0 .. m) {
        int u, v;
        scanf("%d%d", &u, &v);
        --u;
        --v;
        g[u] ~= v;
    }

    bool[] win = new bool[n];
    bool[] used = new bool[n];

    bool dfs(int v) {
        if (used[v]) return win[v];

        used[v] = true;
        foreach (to; g[v]) {
            if (!dfs(to)) return win[v] = true;
        }

        return false;
    }

    string ans = "Second";
    if (dfs(s)) ans = "First";

    puts((ans ~ " player wins").ptr);
}
