#include <memory.h>
#include <cassert>
#include <sstream>
#include <fstream>
#include <iostream>
#include <map>
#include <string>

#define TASK "quack"

const int MAXN = 100010;

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

std::map <std::string, int> labels;
std::string commands[MAXN];
unsigned short reg[255];
int n = 0;
int ip = 0;

Queue<unsigned short> q;

void add() {
	unsigned short x = q.pop();
	unsigned short y = q.pop();
	q.push(x + y);
}

void sub() {
	unsigned short x = q.pop();
	unsigned short y = q.pop();
	q.push(x - y);
}

void mul() {
	unsigned short x = q.pop();
	unsigned short y = q.pop();
	q.push(x * y);
}

void div() {
	unsigned short x = q.pop();
	unsigned short y = q.pop();
	if (y == 0) 
		q.push(0);
	else
		q.push(x / y);
}

void mod() {
	unsigned short x = q.pop();
	unsigned short y = q.pop();
	if (y == 0)
		q.push(0);
    else
		q.push(x % y);
}
		
void load(int r) {
	reg[r] = q.pop();
}

void store(int r) {
	q.push(reg[r]);
}

void print() {
	cout << q.pop() << "\n";
}

void print(int r) {
	cout << reg[r] << "\n";
}

void printSymbol() {
	cout << (char)(q.pop() % 256);
}

void printSymbol(int r) {
	cout << (char)(reg[r] % 256);
}

void jmp(std::string label) {
	ip = labels[label];
}

void jz(int r, std::string label) {
	if (reg[r] == 0)
		ip = labels[label];
}

void je(unsigned short r1, unsigned short r2, std::string label) {
	if (reg[r1] == reg[r2])
		ip = labels[label];
}

void jg(unsigned short r1, unsigned short r2, std::string label) {
	if (reg[r1] > reg[r2])
		ip = labels[label];
}

void put(std::string s) {
	std::stringstream ss;
	ss << s;
	int x;
	ss >> x;
	q.push(x);
}

void run() {
	while (ip < n) {
		std::string instr = commands[ip];
		if (instr == "Q") {
			return;
		} else if (instr == "+") {
			add();
		} else if (instr == "-") {
			sub();
		} else if (instr == "*") {
			mul();
		} else if (instr == "/") {
			div();
		} else if (instr == "%") {
			mod();
		} else if (instr[0] == '>') {
			load(instr[1]);
		} else if (instr[0] == '<') {
			store(instr[1]);
		} else if (instr == "P") {
			print();
		} else if (instr[0] == 'P') {
			print(instr[1]);
		} else if (instr == "C") {
			printSymbol();
		} else if (instr[0] == 'C') {
			printSymbol(instr[1]);
		} else if (instr[0] == 'J') {
			jmp(instr.substr(1, (int)instr.length() - 1));
		} else if (instr[0] == 'Z') {
			jz(instr[1], instr.substr(2, (int)instr.length() - 2));
		} else if (instr[0] == 'E') {
			je(instr[1], instr[2], instr.substr(3, (int)instr.length() - 3));
		} else if (instr[0] == 'G') {
			jg(instr[1], instr[2], instr.substr(3, (int)instr.length() - 3));
		} else if ('0' <= instr[0] && instr[0] <= '9') {
			put(instr);
		}
		
		++ip;
	}
}

int main() {
	std::string s;
	while (true) {
		if (!getline(cin, s))
			break;
		commands[n] = s;
		if (s[0] == ':') {
			labels[s.substr(1, s.length() - 1)] = n;
		}
		++n;
	}
	run();
}
