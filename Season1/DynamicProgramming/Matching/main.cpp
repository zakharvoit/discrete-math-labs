#include <fstream>
#include <vector>
#include <climits>
#include <algorithm>
using namespace std;
  
#define TASK "matching"
  
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
  
typedef long long ll;
 
vector < vector <pair<int, int> > > g;
 
ll d[200000][2];
 
ll dp(int v, int p) {
    ll sum = 0;
    for (int i = 0; i < (int)g[v].size(); i++) {
        int to = g[v][i].first;
        if (to != p)
            sum += dp(to, v);
    }
 
    d[v][1] = sum;
    for (int i = 0; i < (int)g[v].size(); i++) {
        int to = g[v][i].first;
        if (to != p) {
            int w = g[v][i].second;
            d[v][0] = max(d[v][0], sum - max(d[to][0], d[to][1]) + w + d[to][1]);
        }
    }
 
    return max(d[v][0], d[v][1]);
}
 
int main() {
    int n;
    cin >> n;
 
    g.resize(n);
    for (int i = 0; i < n - 1; i++) {
        int v, u, w;
        cin >> v >> u >> w;
        --u;
        --v;
        g[v].push_back(make_pair(u, w));
        g[u].push_back(make_pair(v, w));
    }
 
    cout << dp(0, -1) << endl;
 
    return 0;
}
