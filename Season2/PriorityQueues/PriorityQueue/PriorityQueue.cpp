#include <fstream>
#include <algorithm>
#include <string>
#include <vector>
#include <climits>
#include <cassert>
using namespace std;

#define TASK "priorityqueue"

ifstream cin(TASK ".in");
ofstream cout(TASK ".out");

template <typename T>
class PriorityQueue {
public:
	PriorityQueue() {
		heap.resize(1);
		q.resize(2000000);
		p.resize(1);
	}

	void insert(int idx, T key) {
		heap.push_back(key);
		q[idx] = (int)p.size();
		p.push_back(idx);
		siftUp((int)heap.size() - 1);
	}

	T extractMin() {
		assert((int)heap.size() > 1);
		
		T res = heap[1];
		p[1] = p.back();
		heap[1] = heap.back();
		q[p[1]] = 1;
		heap.pop_back();
		p.pop_back();
		siftDown(1);
		
		return res;
	}

	void decreaseKey(int idx, T newValue) {
		heap[q[idx]] = newValue;
		siftUp(q[idx]);
	}
	
	bool empty() {
		return (int)heap.size() == 1;
	}

private:
	vector <int> q;
	vector <int> p;
	vector <T> heap;
	
	void siftDown(int v) {
		T left = INT_MAX;
		if (2 * v < (int)heap.size()) {
			left = heap[2 * v];
		}

		T right = INT_MAX;
		if (2 * v + 1 < (int)heap.size()) {
			right = heap[2 * v + 1];
		}
		
		if (heap[v] <= left && heap[v] <= right) {
			return;
		}
		
		int mn = 2 * v;
		if (right < left) {
			++mn;
		}
		swap(p[v], p[mn]);
		swap(heap[v], heap[mn]);
		q[p[v]] = v;
		q[p[mn]] = mn;
		siftDown(mn);
	}

	void siftUp(int v) {
		if (v == 1) {
			return;
		}
		
		if (heap[v] < heap[v / 2]) {
			swap(heap[v], heap[v / 2]);
			swap(p[v], p[v / 2]);
			q[p[v]] = v;
			q[p[v / 2]] = v / 2;
			siftUp(v / 2);
		}
	}
};

int main() {
	string op;

	int idx = 0;
	PriorityQueue <int> h;
	while (cin >> op) {
		if (op == "push") {
			int v;
			cin >> v;
			h.insert(idx, v);
		} else if (op == "extract-min") {
			if (h.empty()) {
				cout << "*\n";
			} else {
				cout << h.extractMin() << "\n";
			}
		} else if (op == "decrease-key") {
			int opIdx, newValue;
			cin >> opIdx >> newValue;
			h.decreaseKey(opIdx - 1, newValue);
		}
		++idx;
	}
	
	return 0;
}
