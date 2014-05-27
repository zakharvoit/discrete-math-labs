import std.algorithm;
import std.random;
import std.stdio;

int nthElement(int[] a, int k) {
	for (int l = 0, r = cast(int)a.length - 1; l < r;) {
		int m = a[uniform(l, r + 1)];
		int i = l;
		int j = r;
		
		do {
			while (a[i] < m) ++i;
			while (a[j] > m) {
				--j;
			}
			
			if (i <= j) {
				swap(a[i], a[j]);
				++i;
				--j;
			}
		} while (i <= j);
		
		if (k <= j && l < j) {
			r = j;
		} else if (r > i) {
			l = i;
		} else {
			break;
		}
	}
	
	return a[k];
}

void main() {
	stdin = File("kth.in", "r");
	stdout = File("kth.out", "w");

	int n, k;
	readf(" %s %s", &n, &k);
	
	int[] a = new int[n];
	int A, B, C;
	readf(" %s %s %s %s %s", &A, &B, &C, &a[0], &a[1]);

	foreach (i; 2 .. n) {
		a[i] = A * a[i - 2] + B * a[i - 1] + C;
	}
	
	writeln(nthElement(a, k - 1));
}
