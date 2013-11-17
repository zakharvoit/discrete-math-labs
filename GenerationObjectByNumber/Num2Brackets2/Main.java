import java.io.InputStreamReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Stack;
import java.util.Vector;
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
            inputStream = new FileInputStream("num2brackets2.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("num2brackets2.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        num2brackets2 solver = new num2brackets2();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class num2brackets2 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt() * 2;
        long k = in.nextLong();
 
        long[][] dp = new long[n + 1][n + 1];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                dp[i + 1][j + 1] += dp[i][j];
                if (j > 0)
                    dp[i + 1][j - 1] += dp[i][j];
            }
        }
 
        int balance = 0;
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            int[] newBalance = {balance + 1, balance - 1, balance + 1, balance - 1};
            long[] add = new long[4];
            for (int j = 0; j < 4; j++) {
                if (newBalance[j] >= 0) {
                    int size = n - i - 1;
                    add[j] = dp[size][newBalance[j]] * (1l << ((size - newBalance[j]) / 2l));
                }
            }
            char[] brackets = {'(', ')', '[', ']'};
 
            char c = '*';
            int bad = -1;
            if (!st.isEmpty()) {
                if (st.peek() == '(')
                    bad = 3;
                else if (st.peek() == '[')
                    bad = 1;
            }
            for (int j = 0; j < 4; j++) {
                if (newBalance[j] < 0 || j == bad)
                    continue;
                c = brackets[j];
                balance = newBalance[j];
                if (k - add[j] < 0) {
                    break;
                }
                k -= add[j];
            }
            if (c == ')' || c == ']')
                st.pop();
            else
                st.push(c);
            out.print(c);
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

