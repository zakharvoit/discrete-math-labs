import std.stdio;

int binSearch(alias pred)(int n) {
	int l = -1;
	int r = n;
	
	while (r - l > 1) {
		int m = (l + r) / 2;
		if (pred(m)) {
			l = m;
		} else {
			r = m;
		}
	}
	
	return l;
}

void main() {
	stdin = File("binsearch.in", "r");
	stdout = File("binsearch.out", "w");

	int n;
	readf(" %s", &n);
	int[] a = new int[n];
	foreach (i; 0 .. n) {
		readf(" %s", &a[i]);
	}
	int m;
	readf(" %s", &m);
	foreach (i; 0 .. m) {
		int x;
		readf(" %s", &x);
		
		int first = binSearch!(delegate (int i) { return a[i] < x; })(n) + 2;
		int last = binSearch!(delegate (int i) { return a[i] <= x; })(n) + 1;
		if (first > last) {
			first = -1;
			last = -1;
		}
		writeln(first, " ", last);
	}
}
