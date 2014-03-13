#include <stdio.h>

#define TASK "queuemin2"
#define MAX_K 1000
#define MAX_N 30000123

int queue[MAX_N];
int deque[MAX_N];

int head, tail;

int n, m, k;
int a, b, c;

int min() {
	return deque[head];
}

void pop(int x) {
	if (tail > head && deque[head] == x) {
		++head;
	}
}

void push(int x) {
	while (tail > head && deque[tail - 1] > x) {
		--tail;
	}
	deque[tail++] = x;
}

int main() {
	freopen(TASK ".in", "r", stdin);
	freopen(TASK ".out", "w", stdout);

	scanf("%d%d%d%d%d%d", &n, &m, &k, &a, &b, &c);

	long long ans = 0;
	int f = 0, s = 0;
	int i;
	for (i = 0; i < n; i++) {
		int cur;
		if (i < k) {
			scanf("%d", &cur);
		} else {
			cur = a * f + b * s + c;
			f = s;
			s = cur;
		}

		queue[i] = cur;

		if (i == k - 2) {
			f = cur;
		}

		if (i == k - 1) {
			s = cur;
		}

		push(cur);
		if (i >= m - 1) {
			if (i >= m) {
				pop(queue[i - m]);
			}
			ans += min();
		}
	}

	printf("%I64d", ans);

	return 0;
}
