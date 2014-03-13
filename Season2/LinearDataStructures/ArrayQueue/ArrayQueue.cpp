#include <memory.h>
#include <cassert>
#include <fstream>
#include <iostream>

#define TASK "queue1"

std::ifstream cin(TASK ".in");
std::ofstream cout(TASK ".out");

template <typename T>
class Queue {
public:
	Queue() {
		_capacity = _DEFAULT_CAPACITY;
		_size = 0;
		_head = 0;
		_tail = 0;
		_arr = new T[_capacity];
	}

	void push(const T &value) {
		ensureCapacity(_size + 1);
		_arr[_tail] = value;
		_tail = (_tail + 1) % _capacity;
		++_size;
	}

	T pop() {
		assert(!empty());
		T res = _arr[_head];
		_head = (_head + 1) % _capacity;
		--_size;
		return res;
	}
	
	bool empty() const {
		return _size == 0;
	}
	
	size_t size() const {
		return _size;
	}

private:
	static const size_t _DEFAULT_CAPACITY = 2;
	T *_arr;
	size_t _capacity;
	size_t _size;
	size_t _head;
	size_t _tail;
	
	void ensureCapacity(size_t capacity) {
		if (capacity > _capacity) {
			size_t newCapacity = _capacity << 1;
			T *newArr = new T[newCapacity];
			for (int i = 0; i < _size; i++) {
				newArr[i] = _arr[(_head + i) % _capacity];
			}
			delete[] _arr;
			_arr = newArr;
			_capacity = newCapacity;
			_head = 0;
			_tail = _size;
		}
	}
};

int main() {
	int n;
	cin >> n;

	Queue <int> q;
	while (n--) {
		char op;
		cin >> op;
		if (op == '+') {
			int value;
			cin >> value;
			q.push(value);
		} else {
			cout << q.pop() << "\n";
		}
	}
}
