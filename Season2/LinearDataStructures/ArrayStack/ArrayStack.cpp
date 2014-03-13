#include <memory.h>
#include <cassert>
#include <fstream>

using namespace std;

#define TASK "stack1"

ifstream cin(TASK ".in");
ofstream cout(TASK ".out");

template <typename T>
class Vector {
public:
	Vector() {
		_capacity = MINIMAL_CAPACITY;
		_size = 0;
		_arr = new T[_capacity];
	}
	
	T& operator [](const size_t &index) const {
		assert(index < _size);
		return _arr[index];
	}
	
	void add(const T &value) {
		checkForIncreasing(_size + 1);
		_arr[_size] = value;
		++_size;
	}
	
	void remove() {
		assert(!empty());

		checkForDecreasing(_size - 1);
		--_size;
	}
	
	size_t size() const {
		return _size;
	}
	
	bool empty() const {
		return _size == 0;
	}

private:
	static const size_t MINIMAL_CAPACITY = 1 << 4;
	static const size_t A_RATIO = 1;
	static const size_t B_RATIO = 2;
	static const size_t C_RATIO = 1;

	T *_arr;
	size_t _size, _capacity;
	
	void checkForDecreasing(const size_t &newSize) {
		if ((newSize << B_RATIO) <= _capacity && _capacity > MINIMAL_CAPACITY) {
			_capacity >>= C_RATIO;
			T *newArr = new T[_capacity];
			memcpy(newArr, _arr, _size * sizeof(T));
			delete[] _arr;
			_arr = newArr;
		}
	}
	
	void checkForIncreasing(const size_t &newSize) {
		if (newSize > _capacity) {
			_capacity <<= A_RATIO;
			T *newArr = new T[_capacity];
			memcpy(newArr, _arr, _size * sizeof(T));
			delete[] _arr;
			_arr = newArr;
		}
	}
};

template <typename T>
class Stack {
public:
	void push(const T &value) {
		_v.add(value);
	}

	T pop() {
		assert(!empty());
		T value = _v[_v.size() - 1];
		_v.remove();
		return value;
	}
	
	bool empty() const {
		return _v.empty();
	}
   
private:
	Vector <T> _v;
};

int main() {
	int n;
	cin >> n;

	Stack <int> s;
	while (n--) {
		char op;
		cin >> op;
		if (op == '+') {
			int value;
			cin >> value;
			s.push(value);
		} else {
			cout << s.pop() << "\n";
		}
	}
}
