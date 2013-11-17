
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
            inputStream = new FileInputStream("brackets2num2.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("brackets2num2.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        brackets2num2 solver = new brackets2num2();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class brackets2num2 {
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
 
        char[] brackets = {'(', ')', '[', ']'};
        Stack<Character> st = new Stack<>();
 
        long ans = 0;
        int balance = 0;
        for (int i = 0; i < n; i++) {
            int[] newBalance = new int[4];
            for (int j = 0; j < 4; j++) {
                if (j % 2 == 0)
                    newBalance[j] = balance + 1;
                else
                    newBalance[j] = balance - 1;
            }
            int bad = -1;
            if (!st.isEmpty()) {
                if (st.peek() == '(')
                    bad = 3;
                else if (st.peek() == '[')
                    bad = 1;
            }
            for (int j = 0; brackets[j] < s[i]; j++) {
                if (newBalance[j] < 0 || j == bad)
                    continue;
                int size = n - i - 1;
                ans += dp[size][newBalance[j]] * (1l << ((size - newBalance[j]) / 2));
            }
            if (s[i] == '(' || s[i] == '[') {
                ++balance;
                st.push(s[i]);
            } else {
                --balance;
                st.pop();
            }
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
