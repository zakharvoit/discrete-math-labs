#include <fstream>
#include <vector>
using namespace std;
 
#define TASK "exam"
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
int main() {
    int k, n;
    cin >> k >> n;
    double ans = 0;
    for (int i = 0; i < k; i++) {
        int p, m;
        cin >> p >> m;
        ans += (1.0 * p / n) * (m / 100.0);
    }
    cout.precision(7);
    cout << ans << endl;
}
