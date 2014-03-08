
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
            inputStream = new FileInputStream("vectors.in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("vectors.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        vectors solver = new vectors();
        solver.solve(1, in, out);
        out.close();
    }
}
 
class vectors {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        n = in.nextInt();
        ans = new ArrayList<>();
        gen(new ArrayList<Integer>());
        out.println(ans.size());
 
        for (ArrayList<Integer> x : ans) {
            for (int y : x) {
                out.print(y);
            }
            out.println();
        }
    }
 
    int n;
    ArrayList<ArrayList<Integer>> ans;
 
    void gen(ArrayList<Integer> cur) {
        if (cur.size() == n) {
            ans.add((ArrayList<Integer>)cur.clone());
            return;
        }
 
        for (int i = 0; i < 2; i++) {
            if (cur.isEmpty() || i == 0 || i != cur.get(cur.size() - 1)) {
                cur.add(i);
                gen(cur);
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

