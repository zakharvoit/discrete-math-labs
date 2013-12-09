#include <fstream>
#include <vector>
#include <climits>
#include <algorithm>
#include <string>
#include <map>
using namespace std;
  
#define TASK "palindrome"
  
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
  
typedef long long ll;
string s;
vector <vector <pair <int, int> > > prev;
vector <vector <char> > have;
 
void restore(int l, int r) {
    if (r < l)
        return;
    if (prev[l][r].first == -1) {
        cout << s[l];
        return;
    } else if (have[l][r]) {
        cout << s[l];
        restore(prev[l][r].first, prev[l][r].second);
        cout << s[r];
    } else {
        restore(prev[l][r].first, prev[l][r].second);
    }
}
 
int main() {
    cin >> s;
  
    int n = (int)s.length();
    vector <vector <int> > dp(n, vector <int>(n));
    prev.assign(n, vector <pair <int, int> > (n, make_pair(-1, -1)));
    have.assign(n, vector <char>(n));
  
    for (int i = 0; i < n; i++) {
        dp[i][i] = 1;
    }
  
    for (int size = 2; size <= n; size++) {
        for (int i = 0; i < n; i++) {
            int j = i + size - 1;
            if (j >= n)
                continue;
            if (dp[i + 1][j] > dp[i][j - 1]) {
                dp[i][j] = dp[i + 1][j];
                prev[i][j] = make_pair(i + 1, j);
            } else {
                dp[i][j] = dp[i][j - 1];
                prev[i][j] = make_pair(i, j - 1);
            }
            if (s[i] == s[j] && dp[i][j] < dp[i + 1][j - 1] + 2) {
                dp[i][j] = dp[i + 1][j - 1] + 2;
                prev[i][j] = make_pair(i + 1, j - 1);
                have[i][j] = true;
            }
        }
    }
  
    cout << dp[0][n - 1] << endl;
    restore(0, n - 1);
}
