#include <memory.h>
#include <cassert>
#include <fstream>
#include <iostream>

#define TASK "queuemin2"

const int MAX_K = 1000;

std::ifstream cin(TASK ".in");
std::ofstream cout(TASK ".out");

int main() {
	int n, m, k;
	cin >> n >> m >> k;
	int a, b, c;
	cin >> a >> b >> c;
	
	long long ans = 0;
	Queuemin<int> q;
	int w = 0;
	int e = 0;
	for (int i = 0; i < k; i++) {
		cin >> x[i];
	}
	w = x[k - 1];
	e = x[k - 2];

	for (int i = 0; i < n; i++) {
		int cur;
		if (i < k) {
			cur = x[i];
		} else {
			cur = a * e + b * w + c;
			e = w;
			w = cur;
		}
		q.push(cur);
		if (i >= m - 1) {
			if (i >= m) {
				q.pop();
			}
			ans += q.getMin();
		}
	}

	cout << ans << "\n";
}

