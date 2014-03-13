#include <fstream>
#include <cassert>
#include <iostream>

#define TASK "queue2"

std::ifstream cin(TASK ".in");
std::ofstream cout(TASK ".out");

template <typename T>
struct Node {
	Node(T value): value(value), next(NULL) {}
	Node(T value, Node<T> *next): value(value), next(next) {}
	T value;
	Node<T> *next;
};

template <typename T>
class Queue {
public:
	Queue(): head(NULL), tail(NULL) {}

	void push(T value) {
		Node<T> *newTail = new Node<T>(value);
		if (!head) {
			head = tail = newTail;
			return;
		}
		
		tail->next = newTail;
		tail = newTail;
	}	
	
	T pop() {
		assert(head);
		T res = head->value;
		Node<T> *newHead = head->next;
		delete head;
		if (!newHead) {
			tail = NULL;
		}
		head = newHead;
		return res;
	}

private:
	Node<T> *head, *tail;
};

int main() {
	int n;
	cin >> n;
	Queue<int> q;
	while (n--) {
		char op;
		cin >> op;
		if (op == '+') {
			int x;
			cin >> x;
			q.push(x);
		} else {
			cout << q.pop() << "\n";
		}
	}

	return 0;
}
