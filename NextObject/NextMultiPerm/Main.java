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
			inputStream = new FileInputStream("nextmultiperm.in");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("nextmultiperm.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scanner in = new Scanner(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		nextmultiperm solver = new nextmultiperm();
		solver.solve(1, in, out);
		out.close();
	}
}

class nextmultiperm {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int[] v = in.nextIntArray(n);

        int[] bad = new int[n];

        if (isLast(v))
            ArrayUtils.printArray(bad, out);
        else
            ArrayUtils.printArray(next(v), out);
    }

    boolean isLast(int[] v) {
        boolean last = true;
        for (int i = 1; i < v.length; i++) {
            last &= v[i] <= v[i - 1];
        }
        return last;
    }

    int[] next(int[] v) {
        int n = v.length;
        int[] res = v.clone();
        int pos = n - 2;
        while (v[pos] >= v[pos + 1]) {
            pos--;
        }
        int k = n - 1;
        while (v[k] <= v[pos])
            --k;
        ArrayUtils.swap(res, pos, k);
        ArrayUtils.reverse(res, pos + 1, n);
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

    static public void swap(int[] arr, int a, int b) {
        int buf = arr[a];
        arr[a] = arr[b];
        arr[b] = buf;
    }

    /**
     * Reverse subarray [begin, end)
     * @param arr array to reverse
     * @param begin begin of subarray
     * @param end end of subarray
     */
    static public void reverse(int[] arr, int begin, int end) {
        for (int l = begin, r = end - 1; l <= r; l++, r--) {
            swap(arr, l, r);
        }
    }
}

