
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
            inputStream = new FileInputStream("num2choose.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("num2choose.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        num2choose solver = new num2choose();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class num2choose {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        long m = in.nextLong();
 
        long[][] c = new long[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            c[i][0] = 1;
        }
 
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j <= i; j++) {
                c[i][j] = c[i - 1][j - 1] + c[i - 1][j];
            }
        }
 
        int[] ans = new int[k];
        long cnt = 0;
        int x = -1;
        for (int i = 0; i < k; i++) {
            ++x;
            while (cnt < m) {
                cnt += c[n - x - 1][k - i - 1];
                ++x;
            }
 
            if (cnt > m) {
                --x;
                cnt -= c[n - x - 1][k - i - 1];
            }
 
            ans[i] = x + 1;
        }
 
        for (int i = 0; i < k; i++) {
            out.print(ans[i]);
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
