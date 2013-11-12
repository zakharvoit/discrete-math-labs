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
 * @author Zakhar Voit(zakharvoit@gmail.com)
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream("brackets.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("brackets.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        brackets solver = new brackets();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class brackets {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        n = in.nextInt() * 2;
        a = new char[n];
        gen(0, 0, out);
    }
 
    int n;
    char[] a;
 
    char getBracket(int x) {
        return x == 0 ? '(' : ')';
    }
 
    int getBalance(int x) {
        return x == 0 ? 1 : -1;
    }
 
    void gen(int pos, int balance, PrintWriter out) {
        if (pos == n) {
            if (balance == 0) {
                for (char c : a) {
                    out.print(c);
                }
                out.println();
            }
 
            return;
        }
 
        for (int i = 0; i < 2; i++) {
            int newBalance = balance + getBalance(i);
            if (newBalance >= 0 && newBalance <= n - pos - 1) {
                a[pos] = getBracket(i);
                gen(pos + 1, newBalance, out);
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
 
    public int nextInt() {
        return Integer.parseInt(nextToken());
    }
 
}

