#include <vector>
#include <fstream>
using namespace std;

#define TASK "parking"

ifstream cin(TASK ".in");
ofstream cout(TASK ".out");

class Dsu {
public:
    Dsu(int n) {
        id.resize(n);

        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    int getId(int v) {
        return (v == id[v]) ? v : (id[v] = getId(id[v]));
    }

    void join(int v, int u) {
        v = getId(v);
        u = getId(u);

        id[u] = v;
    }

private:
    vector <int> id;
};

int main() {
    int n;
    cin >> n;

    Dsu dsu(n);
	for (int i = 0; i < n; i++) {
        int v;
        cin >> v;
        --v;
		v = dsu.getId(v);
		
		dsu.join((v + 1) % n, v);

		cout << (v + 1) << "\n";
    }

    return 0;
}
