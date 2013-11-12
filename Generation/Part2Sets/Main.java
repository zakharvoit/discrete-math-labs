import java.util.List;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class Main {
	public static void main(String[] args) {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("part2sets.in");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("part2sets.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scanner in = new Scanner(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		part2sets solver = new part2sets();
		solver.solve(1, in, out);
		out.close();
	}
}

class part2sets {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        n = in.nextInt();
        k = in.nextInt();

        sets = new ArrayList<>();
        used = new boolean[n];

        gen(1, out);
    }

    int n;
    int k;
    boolean[] used;
    List<Integer> sets;

    void gen(int cur, PrintWriter out) {
        if (cur == k) {
            int mask = 0;
            int cnt = 0;
            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    mask |= (1 << i);
                    ++cnt;
                }
            }

            if (cnt != 0) {
                sets.add(mask);
                output(out);
                sets.remove(sets.size() - 1);
            }
            return;
        }
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!used[i])
                a.add(i);
        }

        int m = a.size();
        for (int mask = 1; mask < (1 << m); mask++) {
            if ((mask & 1) == 0)
                continue;
            int allMask = 0;
            for (int i = 0; i < m; i++) {
                if (((1 << i) & mask) != 0) {
                    used[a.get(i)] = true;
                    allMask |= (1 << a.get(i));
                }
            }

            sets.add(allMask);
            gen(cur + 1, out);
            sets.remove(sets.size() - 1);

            for (int i = 0; i < m; i++) {
                if (((1 << i) & mask) != 0) {
                    used[a.get(i)] = false;
                }
            }
        }
    }

    void output(PrintWriter out) {
        for (int mask: sets) {
            for (int i = 0; i < n; i++) {
                if (((1 << i) & mask) != 0) {
                    out.print(i + 1);
                    out.print(" ");
                }
            }
            out.println();
        }
        out.println();
    }
}

class Scanner {
    BufferedReader in;
    StringTokenizer tok;

    public Scanner(InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
        tok = new StringTokenizer("");
    }

    public String nextToken() {
        while (!tok.hasMoreTokens()) {
            tok = new StringTokenizer(next());
        }

        return tok.nextToken();
    }

    private String tryReadNextLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new InputMismatchException();
        }
    }

    public String next() {
        String newLine = tryReadNextLine();
        if (newLine == null)
            throw new InputMismatchException();
        return newLine;
    }

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    }

