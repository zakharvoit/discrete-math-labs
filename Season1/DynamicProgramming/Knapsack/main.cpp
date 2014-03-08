#include <fstream>
#include <vector>
#include <climits>
#include <algorithm>
using namespace std;
 
#define TASK "knapsack"
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
int main() {
    int n, m;
    cin >> n >> m;
 
    vector <int> w(n), c(n);
 
    for (int i = 0; i < n; i++)
        cin >> w[i];
 
    for (int i = 0; i < n; i++)
        cin >> c[i];
 
    vector <vector <int> > dp(n + 1, vector <int>(m + 1, -INT_MAX));
    vector <vector <int> > p(n + 1, vector <int>(m + 1, -1));
     
    dp[0][0] = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j <= m; j++) {
            if (dp[i][j] == -INT_MAX)
                continue;
            if (dp[i + 1][j] < dp[i][j]) {
                dp[i + 1][j] = dp[i][j];
                p[i + 1][j] = p[i][j];
            }
            if (j + w[i] <= m && dp[i + 1][j + w[i]] < dp[i][j] + c[i]) {
                dp[i + 1][j + w[i]] = dp[i][j] + c[i];
                p[i + 1][j + w[i]] = i;
            }
        }
    }
 
    int ans = 0;
    for (int i = 0; i <= m; i++)
        ans = max(ans, dp[n][i]);
     
    vector <int> res;
    for (int i = 0; i <= m; i++) {
        if (ans == dp[n][i]) {
            for (int a = n, b = i; a != -1;) {
                if (a < n)
                    res.push_back(a + 1);
                int na = p[a][b];
                int nb = b - w[na];
                a = na;
                b = nb;
            }
            break;
        }
    }
 
    cout << res.size() << endl;
    reverse(res.begin(), res.end());
    for (int i = 0; i < (int)res.size(); i++) {
        cout << res[i] << " ";
    }
    cout << endl;
}
