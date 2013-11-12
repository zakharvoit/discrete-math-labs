
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
            inputStream = new FileInputStream("partition.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("partition.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        partition solver = new partition();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class partition {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        n = in.nextInt();
        gen(new ArrayList<Integer>(), 0, 1, out);
    }
 
    int n;
 
    void gen(ArrayList<Integer> a, int sum, int last, PrintWriter out) {
        if (sum == n) {
            for (int i = 0; i < a.size(); i++) {
                out.print(a.get(i));
                if (i < a.size() - 1)
                    out.print("+");
            }
            out.println();
            return;
        }
 
        for (int i = last; sum + i <= n; i++) {
            a.add(i);
            gen(a, sum + i, i, out);
            a.remove(a.size() - 1);
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

