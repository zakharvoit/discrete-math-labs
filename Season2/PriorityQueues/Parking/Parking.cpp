#include <fstream>
#include <set>
using namespace std;

#define TASK "parking"

ifstream cin(TASK ".in");
ofstream cout(TASK ".out");

int main() {
	int n, m;
	cin >> n >> m;

	set <int> free;
	for (int i = 0; i < n; i++) {
		free.insert(i);
	}

	while (m--) {
		string op;
		cin >> op;
		int v;
		cin >> v;
		--v;
		if (op == "enter") {
			set<int>::iterator it = free.lower_bound(v);
			if (it == free.end()) {
				it = free.lower_bound(0);
			}
			int pos = *it + 1;
			cout << pos << "\n";
			free.erase(it);
		} else if (op == "exit") {
			free.insert(v);
		}
	}
	
	return 0;
}
