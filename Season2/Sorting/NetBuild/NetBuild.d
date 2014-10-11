import std.stdio;
import std.typecons;

void main() {
	stdin = File("netbuild.in", "r");
	stdout = File("netbuild.out", "w");
	
	int n;
	readf(" %s", &n);
	
	int oldn = n;
	n = 1;
	while (n < oldn) {
		n *= 2;
	}
	auto ans = new Tuple!(int, int)[][](1);
	int m = 0;
	for (int len = 2; len <= n; len *= 2) {
		for (int p = len; p >= 2; p >>= 1) {
			for (int i = 0; i < n; i += p) {
				for (int j = i; j < i + p / 2; j++) {
					auto other = p == len ? i + p - (j - i) : j + 1 + p / 2;
					if (j + 1 <= oldn && other <= oldn) {
						ans[$ - 1] ~= tuple(j + 1, other);
						++m;
					}
				}
			}
			++ans.length;
		}
	}
	
	--ans.length;
	writeln(oldn, " ", m, " ", ans.length);
	foreach (l; ans) {
		write(l.length);
		foreach (x; l) {
			write(" ", x[0], " ", x[1]);
		}
		writeln;
	}
}
