
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
            inputStream = new FileInputStream("num2part.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("num2part.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        num2part solver = new num2part();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class num2part {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        long k = in.nextLong();
 
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
 
        List<Integer> ans = new ArrayList<>();
        int mn = 1;
        while (n > 0) {
            for (int i = mn; i <= n; i++) {
                if (k < dp[n - i][i]) {
                    mn = i;
                    n -= i;
                    ans.add(i);
                    break;
                }
                k -= dp[n - i][i];
            }
        }
 
        for (int i = 0; i < ans.size(); i++) {
            out.print(ans.get(i));
            if (i < ans.size() - 1)
                out.print("+");
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
