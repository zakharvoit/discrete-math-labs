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
            inputStream = new FileInputStream("num2perm.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("num2perm.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        num2perm solver = new num2perm();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class num2perm {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        long k = in.nextLong();
 
        long[] fact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = fact[i - 1] * i;
        }
 
        int[] perm = new int[n];
        boolean[] used = new boolean[n + 1];
        long cur = 0;
        for (int i = 0; i < n; i++) {
            int x = 0;
            while (used[x])
                ++x;
            while (cur < k) {
                cur += fact[n - i - 1];
                ++x;
                while (used[x])
                    ++x;
            }
            if (cur > k) {
                cur -= fact[n - i - 1];
                --x;
                while (used[x])
                    --x;
            }
            used[x] = true;
            perm[i] = x + 1;
        }
 
        for (int i = 0; i < n; i++) {
            out.print(perm[i]);
            out.print(" ");
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
 
    public long nextLong() {
        return Long.parseLong(nextToken());
    }
 
}

