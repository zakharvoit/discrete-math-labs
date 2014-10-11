import std.algorithm;
import std.stdio;

void main() {
	stdin = File("antiqs.in", "r");
	stdout = File("antiqs.out", "w");

	int n;
	readf(" %s", &n);

	int[] p = new int[n];
	int[] res = new int[n];
	
	foreach (i; 0 .. n) {
		p[i] = i;
	}
	
	foreach (i; 0 .. n) {
		int pos = (i + n - 1) / 2;
		res[i] = p[pos];
		swap(p[i], p[pos]);
	}
	
	int[] ans = new int[n];
	foreach (i; 0 .. n) {
		ans[res[i]] = i + 1;
	}
	
	foreach (x; ans) {
		write(x, " ");
	}

	writeln;
}
