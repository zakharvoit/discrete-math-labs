#include <fstream>
#include <vector>
using namespace std;
 
#define TASK "markchain"
 
ifstream cin(TASK ".in");
ofstream cout(TASK ".out");
 
void power(int n, vector <vector <double> > &p, int it) {
    while (it--) {
        vector <vector <double> > newP(n, vector <double>(n));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    newP[i][j] += p[i][k] * p[k][j];
                }
            }
        }
        p = newP;
 
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += p[i][j];
            }
            for (int j = 0; j < n; j++) {
                p[i][j] /= sum;
            }
        }
    }
}
 
int main() {
    int n;
    cin >> n;
    vector <vector <double> > p(n, vector <double>(n));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> p[i][j];
        }
    }
    power(n, p, 64);
    cout.precision(10);
    cout << fixed;
    for (int i = 0; i < n; i++) {
        cout << p[0][i] << endl;
    }
}
