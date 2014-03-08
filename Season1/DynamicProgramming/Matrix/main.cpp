#include <fstream>
#include <vector>
#include <climits>
#include <algorithm>
#include <map>
#include <memory.h>
using namespace std;
  
#define TASK "matrix"
  
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
  
typedef long long ll;
  
int n;
vector <int> p;
int mem[500][500];
int pos[500][500];
  
vector <pair <int, char> > par;
  
void restore(int l, int r) {
    if (l == r)
        return void(cout << "A");
 
    cout << "(";
    restore(l, pos[l][r]);
    restore(pos[l][r] + 1, r);
    cout << ")";
}
  
ll dp(int l, int r) {
    if (l == r)
        return 0;
  
    if (mem[l][r] != -1) {
        return mem[l][r];
    }
  
    ll res = INT_MAX;
    int curPos = -1;
    for (int mid = l; mid < r; mid++) {
        ll cur = dp(l, mid) + dp(mid + 1, r) + 1ll * p[l - 1] * p[mid] * p[r];
        if (cur < res) {
            res = cur;
            curPos = mid;
        }
    }
    mem[l][r] = res;
    pos[l][r] = curPos;
    return res;
}
  
int main() {
    memset(mem, -1, sizeof mem);
    cin >> n;
  
    int x;
    cin >> x;
    p.push_back(x);
    for (int i = 0; i < n; i++) {
        cin >> x;
        cin >> x;
        p.push_back(x);
    }
  
    dp(1, n);
    restore(1, n);
    cout << endl;
}
