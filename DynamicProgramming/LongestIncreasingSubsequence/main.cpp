#include <fstream>
#include <vector>
#include <algorithm>
using namespace std;
 
#define TASK "lis"
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
int main() {
    int n;
    cin >> n;
     
    vector <int> v(n);
 
    for (int i = 0; i < n; i++) {
        cin >> v[i];
    }
 
    vector <int> dp(n, 1), p(n, -1);
     
    int ans = -1;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (v[j] < v[i] && dp[j] + 1 > dp[i]) {
                dp[i] = dp[j] + 1;
                p[i] = j;
            }
            ans = max(ans, dp[i]);
        }
    }
 
    cout << ans << endl;
    vector <int> res;
    res.reserve(ans);
    for (int i = 0; i < n; i++) {
        if (dp[i] == ans) {
            for (int cur = i; cur != -1; cur = p[cur]) {
                res.push_back(v[cur]);
            }
            break;
        }
    }
 
    reverse(res.begin(), res.end());
 
    for (int i = 0; i < (int)res.size(); i++) {
        cout << res[i] << " ";
    }
 
    cout << endl;
}
