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
            inputStream = new FileInputStream("choose.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("choose.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        choose solver = new choose();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class choose {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        n = in.nextInt();
        k = in.nextInt();
        a = new int[k];
        used = new boolean[n];
 
        gen(0, -1, out);
    }
 
    int n, k;
    int[] a;
    boolean[] used;
 
    void gen(int pos, int last, PrintWriter out) {
        if (pos == k) {
            for (int x : a) {
                out.print(x + 1);
                out.print(" ");
            }
            out.println();
            return;
        }
 
        for (int i = last + 1; i < n - k + pos + 1; i++) {
            if (!used[i]) {
                a[pos] = i;
                used[i] = true;
                gen(pos + 1, i, out);
                used[i] = false;
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

