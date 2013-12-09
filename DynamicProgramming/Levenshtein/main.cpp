#include <fstream>
#include <vector>
#include <climits>
#include <string>
#include <algorithm>
using namespace std;
 
#define TASK "levenshtein"
 
const int INF = (int)1e9;
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
int main() {
    string a, b;
    cin >> a >> b;
 
    int n = (int)a.size();
    int m = (int)b.size();
 
    vector <vector <int> > dp(n + 1, vector <int>(m + 1, INF));
 
    for (int i = 0; i <= n; i++) {
        dp[i][0] = i;
    }
 
    for (int j = 0; j <= m; j++) {
        dp[0][j] = j;
    }
 
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            if (a[i - 1] == b[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                dp[i][j] = min(dp[i][j - 1],
                           min(dp[i - 1][j],
                               dp[i - 1][j - 1])) + 1;
            }
        }
    }
 
    cout << dp[n][m] << endl;
}
