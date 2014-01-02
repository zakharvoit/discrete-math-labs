#include <fstream>
#include <vector>
using namespace std;
 
#define TASK "absmarkchain"
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
int main() {
    int n, m;
    cin >> n >> m;
    vector <vector <double> > g(n, vector <double>(n));
    for (int i = 0; i < m; i++) {
        int v, u;
        double p;
        cin >> v >> u >> p;
        --u;
        --v;
        g[v][u] = p;
    }
 
    vector <int> pos(n);
    int cntq = 0;
    int cntr = 0;
    for (int i = 0; i < n; i++) {
        pos[i] = (g[i][i] == 1 ? cntr : cntq)++;
    }
 
    vector <vector <double> > r(cntq, vector <double>(cntr));
    vector <vector <double> > q(cntq, vector <double>(cntq));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (g[j][j] == 1) {
                if (g[i][i] != 1) {
                    r[pos[i]][pos[j]] = g[i][j];
                }
            } else if (g[i][i] != 1) {
                q[pos[i]][pos[j]] = g[i][j];
            }
        }
    }
 
    vector <vector <double> > N(cntq, vector <double>(cntq));
    vector <vector <double> > E(cntq, vector <double>(cntq));
 
    for (int i = 0; i < cntq; i++) {
        N[i][i] = 1;
        E[i][i] = 1;
        for (int j = 0; j < cntq; j++) {
            E[i][j] -= q[i][j];
        }
    }
 
    for (int i = 0; i < cntq; i++) {
        double mul = E[i][i];
        for (int j = 0; j < cntq; j++) {
            E[i][j] /= mul;
            N[i][j] /= mul;
        }
        for (int row = 0; row < cntq; row++) {
            if (i != row) {
                mul = E[row][i];
                for (int j = 0; j < cntq; j++) {
                    E[row][j] -= mul * E[i][j];
                    N[row][j] -= mul * N[i][j];
                }
            }
        }
    }
 
    vector <vector <double> > G(cntq, vector <double>(cntr));
    for (int i = 0; i < cntq; i++) {
        for (int j = 0; j < cntr; j++) {
            for (int k = 0; k < cntq; k++) {
                G[i][j] += N[i][k] * r[k][j];
            }
        }
    }
 
    cout.precision(10);
    cout << fixed;
    for (int i = 0; i < n; i++) {
        double p = 0;
        if (g[i][i] == 1) {
            for (int j = 0; j < cntq; j++) {
                p += G[j][pos[i]];
            }
            p++;
            p /= n;
        }
        cout << p << endl;
    }
    return 0;
}
