import java.util.List;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import java.util.Collections;
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
			inputStream = new FileInputStream("nextsetpartition.in");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("nextsetpartition.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scanner in = new Scanner(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		nextsetpartition solver = new nextsetpartition();
		try {
			int testNumber = 1;
			while (true)
				solver.solve(testNumber++, in, out);
		} catch (UnknownError e) {
			out.close();
		}
	}
}

class nextsetpartition {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();

        if (n == 0)
            throw new UnknownError();

        List<Integer>[] sets = new List[k];
        for (int i = 0; i < k; i++) {
            String[] tokens = in.next().split(" ");
            sets[i] = new ArrayList<>();
            for (String s: tokens) {
                sets[i].add(Integer.parseInt(s));
            }
        }

        List<Integer> used = new ArrayList<>();
        for (int i = k - 1; i >= 0; i--) {
            int upper = getUpper(used, sets[i].get(sets[i].size() - 1));
            if (upper != -1) {
                sets[i].add(upper);
                remove(used, upper);
                break;
            }

            boolean ok = false;
            for (int j = sets[i].size() - 1; j >= 0; j--) {
                upper = getUpper(used, sets[i].get(j));
                if (j > 0 && upper != -1) {
                    used.add(sets[i].get(j));
                    sets[i].set(j, upper);
                    remove(used, upper);
                    ok = true;
                    break;
                }
                used.add(sets[i].get(j));
                sets[i].remove(j);
            }

            if (ok)
                break;
        }

        int ans = k;
        while (ans > 0 && sets[ans - 1].isEmpty())
            --ans;

        ans += used.size();

        out.println(n + " " + ans);

        for (int i = 0; i < k && !sets[i].isEmpty(); i++) {
            for (int j = 0; j < sets[i].size(); j++) {
                out.print(sets[i].get(j) + " ");
            }
            out.println();
        }

        Collections.sort(used);
        for (int x: used)
            out.println(x);
        out.println();
    }

    void remove(List<Integer> a, int v) {
        a.remove(a.indexOf(v));
    }

    int getUpper(List<Integer> a, int v) {
        int res = -1;
        for (int x: a) {
            if (x > v) {
                if (res == -1 || res > x) {
                    res = x;
                }
            }
        }

        return res;
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

