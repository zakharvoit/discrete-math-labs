#include <fstream>
#include <vector>
#include <climits>
using namespace std;
 
#define TASK "salesman"
 
const int FAKE = INT_MAX;
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
int main() {
    int n, m;
    cin >> n >> m;
     
    vector <vector <pair <int, int> > > g(n);
    for (int i = 0; i < m; i++) {
        int u, v, w;
        cin >> u >> v >> w;
            --u;
            --v;
        g[u].push_back(make_pair(v, w));
        g[v].push_back(make_pair(u, w));
    }  
 
    vector <vector <int> > dp((1 << n), vector <int>(n, FAKE));
        for (int i = 0; i < n; i++) {
            dp[1 << i][i] = 0;
        }
 
    for (int mask = 1; mask < (1 << n); mask++) {
        for (int v = 0; v < n; v++) {
            if ((mask & (1 << v)) == 0 || dp[mask][v] == FAKE) {
                continue;
            }
            for (int i = 0; i < (int)g[v].size(); i++) {
                int to = g[v][i].first;
                int w = g[v][i].second;
                if ((mask & (1 << to))) {
                    continue;
                }
                int newMask = mask | (1 << to);
                if (dp[newMask][to] == FAKE ||
                        dp[newMask][to] > dp[mask][v] + w) {
                    dp[newMask][to] = dp[mask][v] + w;
                }
            }
        }
    }
 
    int ans = FAKE;
    int all = (1 << n) - 1;
    for (int i = 0; i < n; i++) {
        ans = min(ans, dp[all][i]);
    }
 
    if (ans == INT_MAX)
        cout << "-1\n";
    else
        cout << ans << endl;
}
