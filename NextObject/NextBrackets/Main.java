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
			inputStream = new FileInputStream("nextbrackets.in");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("nextbrackets.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scanner in = new Scanner(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		nextbrackets solver = new nextbrackets();
		solver.solve(1, in, out);
		out.close();
	}
}

class nextbrackets {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        char[] s = in.nextToken().toCharArray();
        int balance = 0;
        int i;
        boolean ok = false;
        for (i = s.length - 1; i >= 0; i--) {
            if (s[i] == ')')
                ++balance;
            else {
                if (--balance > 0) {
                    s[i] = ')';
                    ok = true;
                    break;
                }
            }
        }

        if (!ok) {
            out.println("-");
            return;
        }
        balance = 0;
        for (int j = 0; j < s.length; j++) {
            if (j <= i) {
                if (s[j] == '(')
                    ++balance;
                else
                    --balance;
                out.print(s[j]);
            } else {
                if (s.length - j - 1 > balance) {
                    out.print("(");
                    ++balance;
                } else {
                    out.print(")");
                    --balance;
                }
            }
        }
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

