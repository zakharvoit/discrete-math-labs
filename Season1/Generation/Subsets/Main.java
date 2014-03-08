
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
 * @author Zakhar Voit(zakharvoit@gmail.com)
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream("subsets.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("subsets.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        subsets solver = new subsets();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class subsets {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        n = in.nextInt();
        gen(new ArrayList<Integer>(), 0, true, out);
    }
 
    int n;
 
    void gen(ArrayList<Integer> cur, int last, boolean wasAdded, PrintWriter out) {
        if (wasAdded) {
            for (int i = 0; i < cur.size(); i++) {
                out.print(cur.get(i));
                out.print(" ");
            }
            out.println();
        }
 
        if (last == n)
            return;
 
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                cur.add(last + 1);
            }
 
            gen(cur, last + 1, i == 0, out);
 
            if (i == 0) {
                cur.remove(cur.size() - 1);
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

