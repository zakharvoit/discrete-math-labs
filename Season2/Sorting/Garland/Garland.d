import std.stdio;
import std.algorithm;

void main() {
	stdin = File("garland.in", "r");
	stdout = File("garland.out", "w");

	int n;
	double A;
	readf(" %s %s", &n, &A);
	
	double l = 0;
	double r = A + 1;
	double ans = -1;
	foreach (it; 0 .. 64) {
		double m = (l + r) / 2;
		double a = A;
		double b = m;
		double mn = double.max;
		foreach(i; 2 .. n) {
			double c = 2 * b + 2 - a;
			mn = min(mn, c);
			a = b;
			b = c;
		}
		if (mn >= 0) {
			r = m;
			ans = b;
		} else {
			l = m;
		}
	}
	
	writefln("%.2f", ans);
}
