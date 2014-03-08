#include <fstream>
#include <vector>
#include <queue>
using namespace std;
 
#define TASK "countpaths"
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
vector <int> mem;
vector <vector <int> > g;
 
int dp(int v) {
    if (mem[v] != -1)
        return mem[v];
 
    if (v == (int)g.size() - 1)
        return 1;
    int res = 0;
    for (int i = 0; i < (int)g[v].size(); i++) {
        int to = g[v][i];
        res += dp(to);
    }
 
    return mem[v] = res;
}
 
int main() {
    int n, m;
    cin >> n >> m;
 
    g.resize(n);
    for (int i = 0; i < m; i++) {
        int u, v;
        cin >> u >> v;
        g[--u].push_back(--v);
    }
 
    mem.assign(n, -1);
 
    cout << dp(0) << endl;
}
