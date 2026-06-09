import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class KnuthMorrisPratt {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));

        }

        String next() throws IOException {
            while (!tok.hasMoreTokens()) {
                tok = new StringTokenizer(in.readLine());
            }
            return tok.nextToken();
        }
    }

    public List<Integer> findPattern(String pattern, String text) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        // Implement this function yourself
        String prefix=pattern+"$"+text;
        int[] prefixArray = build(prefix);

        // find positions
        int patternLen=pattern.length();
        for (int i = patternLen + 1; i < prefixArray.length; i++) {
            if (prefixArray[i] == patternLen) {
                result.add(i - (2 * patternLen));
            }
        }
        return result;
    }

    public int[] build(String text) {
        int[] prefixArray = new int[text.length()];
        prefixArray[0]=0;
        int j = 0;

        for (int i = 1; i < text.length();i++){

            while (j > 0 && text.charAt(i) != text.charAt(j)) {
                j = prefixArray[j - 1];
            }
            
            if (text.charAt(i) == text.charAt(j)) {
                j++;
            }
            prefixArray[i] = j;
        }
        
        return prefixArray;
    }
    
    static public void main(String[] args) throws IOException {
        new KnuthMorrisPratt().run();
    }

    public void print(List<Integer> x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String pattern = scanner.next();
        String text = scanner.next();
        List<Integer> positions = findPattern(pattern, text);
        print(positions);
    }
}