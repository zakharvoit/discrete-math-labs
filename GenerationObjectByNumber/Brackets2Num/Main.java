
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
            inputStream = new FileInputStream("brackets2num.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("brackets2num.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        brackets2num solver = new brackets2num();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class brackets2num {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        char[] s = in.nextToken().toCharArray();
 
        int n = s.length;
 
        long[][] dp = new long[n + 1][n + 1];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                dp[i + 1][j + 1] += dp[i][j];
                if (j > 0)
                    dp[i + 1][j - 1] += dp[i][j];
            }
        }
 
        long cnt = 0;
        int balance = 0;
        for (int i = 0; i < n; i++) {
            if (s[i] == ')') {
                cnt += dp[n - i - 1][balance + 1];
                --balance;
            } else
                ++balance;
        }
 
        out.println(cnt);
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

