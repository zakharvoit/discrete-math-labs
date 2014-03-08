
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
            inputStream = new FileInputStream("part2num.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("part2num.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        part2num solver = new part2num();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class part2num {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        String[] tokens = in.nextToken().split("\\+");
        int m = tokens.length;
        int[] a = new int[m];
        int n = 0;
        for (int i = 0; i < m; i++) {
            a[i] = Integer.parseInt(tokens[i]);
            n += a[i];
        }
 
        long[][] dp = new long[n + 1][n + 2];
        for (int i = 0; i <= n; i++)
            dp[0][i] = 1;
 
        for (int i = 1; i <= n; i++) {
            for (int j = n; j > 0; j--) {
                dp[i][j] += dp[i][j + 1];
                if (i >= j)
                    dp[i][j] += dp[i - j][j];
            }
        }
 
        long ans = 0;
        int mn = 1;
        for (int i = 0; i < m; i++) {
            for (int j = mn; j < a[i]; j++) {
                if (n >= j)
                    ans += dp[n - j][j];
            }
            mn = a[i];
            n -= a[i];
        }
 
        out.println(ans);
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
