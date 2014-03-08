#include <fstream>
#include <vector>
#include <algorithm>
using namespace std;
  
#define TASK "optimalcode"
typedef long long ll;
  
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
const ll INF = (ll)1e18;
  
 
vector <vector <int> > p;
char cur[1000000];
int end = 0;
 
void restore(int l, int r) {
    if (l == r) {
        cout << cur << endl;
        return;
    }
    cur[end] = '0';
    cur[++end] = 0;
    restore(l, p[l][r]);
    cur[end - 1] = '1';
    restore(p[l][r] + 1, r);
    cur[--end] = 0;
}
 
int main() {
    int n;
    cin >> n;
    vector <int> f(n);
 
    for (int i = 0; i < n; i++) {
        cin >> f[i];
    }
 
    vector < vector <ll> > dp(n, vector <ll> (n, INF));
    p.assign(n, vector <int>(n));
 
    for (int i = 0; i < n; i++) {
        dp[i][i] = 0;
        p[i][i] = i;
    }
 
    vector <int> sum(n);
    for (int i = 0; i < n; i++) {
        sum[i] = i > 0 ? sum[i - 1] + f[i] : f[i];
    }
    for (int len = 2; len <= n; len++) {
        for (int l = 0; l + len <= n; l++) {
            int r = l + len - 1;
            for (int k = p[l][r - 1]; k <= min(r - 1, p[l + 1][r]); k++) {
                if (dp[l][k] + dp[k + 1][r] <= dp[l][r]) {
                    dp[l][r] = dp[l][k] + dp[k + 1][r];
                    p[l][r] = k;
                }
            }
            dp[l][r] += sum[r] - (l > 0 ? sum[l - 1] : 0);
        }
    }
 
    cout << dp[0][n - 1] << endl;
    restore(0, n - 1);
     
    return 0;
}
