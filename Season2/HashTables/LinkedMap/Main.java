import java.util.List;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import java.util.LinkedList;
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
			inputStream = new FileInputStream("linkedmap.in");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("linkedmap.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scanner in = new Scanner(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		linkedmap solver = new linkedmap();
		solver.solve(1, in, out);
		out.close();
	}
}

class linkedmap {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        HashMap<String, Node> map = new HashMap<>();

        Node last = null;

        while (true) {
            String s;
            try {
                s = in.nextToken();
            } catch (InputMismatchException e) {
                return;
            }

            String key = in.nextToken();
            switch (s) {
                case "put": {
                    String value = in.nextToken();
                    if (map.contains(key)) {
                        map.get(key).val = value;
                    } else {
                        Node node = new Node(value, last, null);
                        map.put(key, node);
                        if (last != null) {
                            last.next = node;
                        }
                        last = node;
                    }
                    break;
                }
                case "delete": {
                    Node node = map.get(key);
                    if (node != null) {
                        if (node == last) {
                            last = node.prev;
                        }
                        map.remove(key);
                        if (node.next != null) {
                            node.next.prev = node.prev;
                        }

                        if (node.prev != null) {
                            node.prev.next = node.next;
                        }
                    }
                    break;
                }
                case "get": {
                    Node node = map.get(key);
                    out.println(node == null ? "none" : node.val);
                    break;
                }
                case "prev": {
                    Node node = map.get(key);
                    if (node == null) {
                        out.println("none");
                    } else {
                        out.println(node.prev == null
                                ? "none"
                                : node.prev.val);
                    }
                    break;
                }
                case "next": {
                    Node node = map.get(key);
                    if (node == null) {
                        out.println("none");
                    } else {
                        out.println(node.next == null
                                ? "none"
                                : node.next.val);
                    }
                    break;
                }
            }
        }
    }

    class Node {
        String val;
        public Node prev;
        public Node next;

        Node(String val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
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

}

class HashMap<K, V> implements Iterable<K> {
    public static final int DEFAULT_CAPACITY = 16;

    public static class Node<K, V> {
        public final K key;
        public V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public HashMap() {
        size = 0;
        //noinspection unchecked
        table = new List[DEFAULT_CAPACITY];
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private static final double MAX_LOAD_FACTOR = 0.75;

    private List<Node<K, V>>[] table;
    private int size;

    public void put(K key, V value) {
        if (contains(key)) {
            changeValue(key, value);
            return;
        }

        if (1. * (size + 1) / table.length > MAX_LOAD_FACTOR) {
            rehash(2 * table.length);
        }

        size++;
        unsafePut(key, value);
    }

    private void changeValue(K key, V value) {
        Node<K, V> node = find(key);
        node.value = value;
    }

    private Node<K, V> find(K key) {
        for (Node<K, V> node : table[mod(key.hashCode())]) {
            if (node.key.equals(key)) {
                return node;
            }
        }

        return null;
    }

    private void unsafePut(K key, V value) {
        table[mod(key.hashCode())].add(new Node<>(key, value));
    }

    public void remove(K key) {
        for (Iterator<Node<K, V>> it = table[mod(key.hashCode())].iterator();
                it.hasNext();) {
            Node<K, V> node = it.next();
            if (node.key.equals(key)) {
                it.remove();
                size--;
                return;
            }
        }
    }

    public V get(K key) {
        Node<K, V> node = find(key);
        return node == null ? null : node.value;
    }

    public boolean contains(K key) {
        return find(key) != null;
    }

    private int mod(int hash) {
        return (hash % table.length + table.length) % table.length;
    }

    private void rehash(int newCapacity) {
        List<Node<K, V>>[] oldTable = table;
        //noinspection unchecked
        table = new List[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            table[i] = new LinkedList<>();
        }

        for (List<Node<K, V>> bucket : oldTable) {
            for (Node<K, V> node : bucket) {
                unsafePut(node.key, node.value);
            }
        }
    }

    public Iterator<K> iterator() {
        return new Iterator<K>() {
            int pos = 0;
            int cnt = 0;
            Iterator<Node<K, V>> it = table[pos++].iterator();

            @Override
            public boolean hasNext() {
                return cnt < size;
            }

            @Override
            public K next() {
                while (!it.hasNext()) {
                    it = table[pos++].iterator();
                }

                cnt++;
                return it.next().key;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

}

