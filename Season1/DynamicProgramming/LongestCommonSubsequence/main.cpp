#include <fstream>
#include <vector>
#include <algorithm>
using namespace std;
 
#define TASK "lcs"
 
const int MAXN = 2000;
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
int dp[MAXN][MAXN];
pair <int, int> p[MAXN][MAXN];
 
int main() {
    int n;
    cin >> n;
    vector <int> a(n);
    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }
 
    int m;
    cin >> m;
    vector <int> b(m);
    for (int i = 0; i < m; i++) {
        cin >> b[i];
    }
 
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (a[i] == b[j]) {
                if (i > 0 && j > 0) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    p[i][j] = make_pair(i - 1, j - 1);
                } else {
                    dp[i][j] = 1;
                    p[i][j] = make_pair(-1, -1);
                }
            } else {
                bool updated = false;
                if (i > 0 && dp[i - 1][j] > dp[i][j]) {
                    dp[i][j] = dp[i - 1][j];
                    p[i][j] = make_pair(i - 1, j);
                    updated = true;
                }
                if (j > 0 && dp[i][j - 1] > dp[i][j]) {
                    dp[i][j] = dp[i][j - 1];
                    p[i][j] = make_pair(i, j - 1);
                    updated = true;
                }
                if (!updated) {
                    p[i][j] = make_pair(-1, -1);
                }
            }
        }
    }
 
    int ans = dp[n - 1][m - 1];
    pair <int, int> cur = make_pair(n - 1, m - 1);
    vector <int> res;
    res.reserve(ans);
    for (; cur.first != -1; cur = p[cur.first][cur.second]) {
        if (a[cur.first] == b[cur.second])
            res.push_back(b[cur.second]);
    }
 
    reverse(res.begin(), res.end());
 
    cout << ans << endl;
    for (int i = 0; i < (int)res.size(); i++) {
        cout << res[i] << " ";
    }
    cout << endl;
}
