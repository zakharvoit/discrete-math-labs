#include <fstream>
#include <vector>
using namespace std;
 
#define TASK "lottery"
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
int main() {
    int n, m;
    cin >> n >> m;
    double p = 1;
    int last = 0;
    double ans = 0;
    for (int i = 0; i < m; i++) {
        int a, b;
        cin >> a >> b;
        ans += p * (1.0 - (1.0 / a)) * last;
        p *= (1.0 / a);
        last = b;
    }
    ans += p * last;
    cout << (n - ans) << endl;
}
