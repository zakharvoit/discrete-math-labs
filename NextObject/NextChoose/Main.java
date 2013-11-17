import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
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
			inputStream = new FileInputStream("nextchoose.in");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("nextchoose.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scanner in = new Scanner(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		nextchoose solver = new nextchoose();
		solver.solve(1, in, out);
		out.close();
	}
}

class nextchoose {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();

        int[] choose = in.nextIntArray(k);

        boolean last = true;
        for (int i = n, j = k - 1; j >= 0; i--, j--) {
            last &= choose[j] == i;
        }

        if (last) {
            out.println("-1");
            return;
        }

        int pos = k - 1;
        int cur = n;
        while (choose[pos] == cur) {
            --cur;
            --pos;
        }

        ++choose[pos];
        for (++pos; pos < k; pos++) {
            choose[pos] = choose[pos - 1] + 1;
        }

        ArrayUtils.printArray(choose, out);
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

    public int[] nextIntArray(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = nextInt();
        }

        return res;
    }

    }

class ArrayUtils {

    static public void printArray(int[] arr, PrintWriter out) {
        for (int x: arr) {
            out.print(x);
            out.print(" ");
        }
        out.println();
    }

    }

