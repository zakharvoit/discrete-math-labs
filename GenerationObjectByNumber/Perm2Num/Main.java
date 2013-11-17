
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
            inputStream = new FileInputStream("perm2num.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("perm2num.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        perm2num solver = new perm2num();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class perm2num {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int[] perm = ArrayUtils.readIntArray(in, n);
 
        long[] fact = new long[n + 1];
 
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = fact[i - 1] * i;
        }
        boolean[] used = new boolean[n];
 
        long ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < perm[i] - 1; j++) {
                if (!used[j])
                    ans += fact[n - i - 1];
            }
            used[perm[i] - 1] = true;
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
 
    public int nextInt() {
        return Integer.parseInt(nextToken());
    }
 
    }
 
class ArrayUtils {
 
    static public int[] readIntArray(Scanner in, int size) {
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = in.nextInt();
        }
        return res;
    }
 
}
