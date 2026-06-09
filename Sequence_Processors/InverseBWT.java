import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


class InverseBWT {
    public static void main(String[] args) throws IOException{
        InverseBWT.run();
    }

    public static void run() throws IOException{
        FastScanner fs=new FastScanner();
        String str = fs.next();
        String og = inverse(str);
        System.out.print(og);
    }

    static class FastScanner {
        BufferedReader in;
        StringTokenizer st = new StringTokenizer("");

        FastScanner(){
            in=new BufferedReader(new InputStreamReader(System.in));

        }

        public String next() throws IOException{
            while(!st.hasMoreElements()){
                st = new StringTokenizer(in.readLine());
            }

            return st.nextToken();
        } 

    }

    public static String inverse(String str) {
        int len=str.length();
      char[] l=str.toCharArray();
      char[] f=Arrays.copyOf(l,len);
      Arrays.sort(f);

      Map<Character, Integer> fc=new HashMap<>();

      for(int i=0;i<len;i++){
          if (!fc.containsKey(f[i])) {
              fc.put(f[i], i);
          }
      }

      Map<Character, Integer> lc = new HashMap<>();
      int[] occ=new int[len];
      for(int i=0;i<len;i++){
          char c = l[i];
          lc.put(c, lc.getOrDefault(c, 0) + 1);
          occ[i] = lc.get(c);
      }

      StringBuilder sb = new StringBuilder();
      int index = 0;

      for (int i = 0; i < len; i++) {
          if (l[i] == '$') {
              index = i;
              break;
          }
      }
      
      for (int i = 0; i < len; i++) {
          sb.append(l[index]);
          index = fc.get(l[index]) + occ[index] - 1;

      }
      
      return sb.reverse().toString();

            } 
  
}