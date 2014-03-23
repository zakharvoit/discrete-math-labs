#include <fstream>
#include <vector>
#include <algorithm>
#include <string>

using namespace std;

#define TASK "dsu"

ifstream cin(TASK ".in");
ofstream cout(TASK ".out");

class Dsu {
public:
	Dsu(int n) {
		id.resize(n);
		mn.resize(n);
		mx.resize(n);
		size.resize(n);
		
		for (int i = 0; i < n; i++) {
			id[i] = i;
			mn[i] = (i + 1);
			mx[i] = (i + 1);
			size[i] = 1;
		}
	}

	int getId(int v) {
		return (v == id[v]) ? v : (id[v] = getId(id[v]));
	}
	
	int getSize(int v) {
		return size[v];
	}
	
	int getMin(int v) {
		return mn[v];
	}

	int getMax(int v) {
		return mx[v];
	}

	void join(int v, int u) {
		v = getId(v);
		u = getId(u);

		if (v == u) {
			return;
		}
		
		if (size[u] > size[v]) {
			swap(u, v);
		}
		
		id[u] = v;
		mn[u] = mn[v] = min(mn[u], mn[v]);
		mx[u] = mx[v] = max(mx[u], mx[v]);
		size[v] = size[u] = size[v] + size[u];
	}
private:
	vector <int> id;
	vector <int> mn;
	vector <int> mx;
	vector <int> size;
};

int main() {
	int n;
	cin >> n;
	Dsu dsu = Dsu(n);
	
	string op;
	while (cin >> op) {
		if (op == "union") {
			int v, u;
			cin >> v >> u;
			--u;
			--v;
			dsu.join(v, u);
		} else if (op == "get") {
			int v;
			cin >> v;
			--v;
			v = dsu.getId(v);
			cout << dsu.getMin(v) << " " << dsu.getMax(v) << " " << dsu.getSize(v) << "\n";
		}
	}

	return 0;
}

