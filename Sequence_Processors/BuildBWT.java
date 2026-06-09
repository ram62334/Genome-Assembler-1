import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class BuildBWT{
    public static void main(String[] args) throws IOException{
        BuildBWT.run();
    }
    
    static class FastScanner {
        BufferedReader bf;
        StringTokenizer st=new StringTokenizer("");

        public FastScanner() {
            bf = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() throws IOException{
            while (!st.hasMoreElements()) {
                st = new StringTokenizer(bf.readLine());
            }
            return st.nextToken();
        }

        public int nextInt() throws IOException{
            return Integer.parseInt(next());
        }
    }

    public static void run() throws IOException{
        FastScanner fs = new FastScanner();
        String s = fs.next();

        String bwt=build(s);
        display(bwt);
    }

    public static String build(String s) {
        int len = s.length();
        char[][] bwt = new char[len][len];
        char[] b = new char[len];
        char[] arr = s.toCharArray();
        
        for (int i = 0; i < len; i++) {
            //arr = s.toCharArray();
            arr = rotate(arr);
           // Arrays.sort(arr);
            
            for (int j = 0; j < len; j++) {
                bwt[i][j] = arr[j];
            }
        }

        String[] rotations = new String[len];

        for (int i = 0; i < len; i++) {
            rotations[i] = new String(bwt[i]);
        }

        Arrays.sort(rotations);

        int k=0;

        for (int i = 0; i < len; i++) {
            b[i] = rotations[i].charAt(len - 1);
        }

        // for (int i = 0; i < len; i++) {
        //     System.out.println();
        //     for (int j = 0; j < len; j++) {
        //         System.out.print(bwt[i][j] + " ");
        //     }
        // }
        //System.out.print(new String(b));

        return new String(b);
    }

    public static char[] rotate(char[] c) {
        char last = c[c.length - 1]; 

        for (int i = c.length - 1; i > 0; i--) {
            c[i] = c[i - 1];
        }

        c[0] = last; 
        return c;

    }

    public static void display(String s) {
        for (int i = 0; i < s.length(); i++) {
            System.out.print(s.charAt(i));
        }
    }

}