import std.algorithm;
import std.range;
import std.stdio;
import std.string;

void main(string[] args) {
	stdin = File("radixsort.in", "r");
	stdout = File("radixsort.out", "w");

	int n, m, k;
	readf(" %s %s %s\n", &n, &m, &k);
	string[] a = new string[n];
	foreach (i; 0 .. n) {
		a[i] = readln.strip;
	}

	int[] cnt = new int[256];
	string[] b = new string[n];
	for (auto i = m - 1; i >= m - k; i--) {
		fill(cnt, 0);
		foreach (s; a) {
			++cnt[s[i]];
		}
		foreach (j; 1 .. cnt.length) {
			cnt[j] += cnt[j - 1];
		}
		foreach_reverse (s; a) {
			b[--cnt[s[i]]] = s;
		}
		swap(a, b);
	}
	
	foreach (x; a) {
		writeln(x);
	}
}
