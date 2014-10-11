import std.algorithm;
import std.stdio;
import std.typecons;
import std.bitmanip;

void main() {
	stdin = File("netcheck.in", "r");
	stdout = File("netcheck.out", "w");

	int n, m, k;
	readf(" %s %s %s", &n, &m, &k);
	
	Tuple!(int, int)[] comps;
	foreach (i; 0 .. k) {
		int r;
		readf(" %s", &r);
		foreach (j; 0 .. r) {
			int a, b;
			readf(" %s %s", &a, &b);
			comps ~= tuple(min(a, b) - 1, max(a, b) - 1);
		}
	}
	
	foreach (mask; 0 .. 1 << n) {
		BitArray a;
		a.init([mask], n);
		auto b = a.dup;
		auto c = a.dup;
		b.sort;
		stderr.writeln("ololo");
		foreach (x; comps) {
			if (a[x[0]] > a[x[1]]) {
				stderr.writeln("swap: ", x);
				auto buf = a[x[0]];
				a[x[0]] = a[x[1]];
				a[x[1]] = buf;
				stderr.writeln(a);
			}
		}
		if (a != b) {
			stderr.writeln(c);
			stderr.writeln(a);
			writeln("No");
			return;
		}
	}
	
	writeln("Yes");
}
