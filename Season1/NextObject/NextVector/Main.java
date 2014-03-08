import java.io.InputStreamReader;
import java.io.IOException;
import java.util.InputMismatchException;
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
			inputStream = new FileInputStream("nextvector.in");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("nextvector.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scanner in = new Scanner(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		nextvector solver = new nextvector();
		solver.solve(1, in, out);
		out.close();
	}
}

class nextvector {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        char[] v = in.nextToken().toCharArray();
        boolean allZeros = true;
        boolean allOnes = true;
        for (char x: v) {
            allOnes &= x == '1';
            allZeros &= x == '0';
        }

        if (allZeros)
            out.println("-");
        else
            out.println(new String(prev(v)));

        if (allOnes)
            out.println("-");
        else
            out.println(new String(next(v)));
    }

    char[] next(char[] v) {
        int n = v.length;
        char[] res = v.clone();
        int pos = n - 1;
        while (v[pos] != '0') {
            --pos;
        }

        res[pos] = '1';
        for (++pos; pos < n; pos++)
            res[pos] = '0';

        return res;
    }

    char[] prev(char[] v) {
        int n = v.length;
        char[] res = v.clone();
        int pos = n - 1;
        while (v[pos] != '1') {
            --pos;
        }
        res[pos] = '0';
        for (++pos; pos < n; pos++) {
            res[pos] = '1';
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

    }

