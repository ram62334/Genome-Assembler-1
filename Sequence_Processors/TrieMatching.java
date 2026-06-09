import java.io.*;
import java.util.*;

class Node
{
	public static final int Letters =  4;
	public static final int NA      = -1;
	public int next [];

	Node ()
	{
		next = new int [Letters];
		Arrays.fill (next, NA);
	}
}

public class TrieMatching implements Runnable {
	int letterToIndex (char letter)
	{
		switch (letter)
		{
			case 'A': return 0;
			case 'C': return 1;
			case 'G': return 2;
			case 'T': return 3;
			default: assert (false); return Node.NA;
		}
	}

	List<Integer> solve(String text, int n, List<String> patterns) {
		List<Integer> result = new ArrayList<>();

		List<Node> trie = new ArrayList<>();
		trie.add(new Node()); // root

		for (String pattern : patterns) {
			int currentNode = 0;

			for (int i = 0; i < pattern.length(); i++) {
				char c = pattern.charAt(i);
				int index = letterToIndex(c);

				if (trie.get(currentNode).next[index] != Node.NA) {
					currentNode = trie.get(currentNode).next[index];
				} else {
					trie.add(new Node());
					int newNode = trie.size() - 1;
					trie.get(currentNode).next[index] = newNode;
					currentNode = newNode;
				}
			}
		}
		for (int i = 0; i < text.length(); i++) {
			int currentNode = 0;
			int j = i;

			while (j < text.length()) {
				char c = text.charAt(j);
				int index = letterToIndex(c);

				if (trie.get(currentNode).next[index] != Node.NA) {
					currentNode = trie.get(currentNode).next[index];

					// Check if this node is a leaf (pattern ends here)
					boolean isLeaf = true;
					for (int k = 0; k < Node.Letters; k++) {
						if (trie.get(currentNode).next[k] != Node.NA) {
							isLeaf = false;
							break;
						}
					}

					if (isLeaf) {
						result.add(i);
						break;
					}

					j++;
				} else {
					break;
				}
			}
		}

		return result;
	}

	public void run () {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String text = in.readLine ();
		 	int n = Integer.parseInt (in.readLine ());
		 	List <String> patterns = new ArrayList <String> ();
			for (int i = 0; i < n; i++) {
				patterns.add (in.readLine ());
			}

			List <Integer> ans = solve (text, n, patterns);

			for (int j = 0; j < ans.size (); j++) {
				System.out.print ("" + ans.get (j));
				System.out.print (j + 1 < ans.size () ? " " : "\n");
			}
		}
		catch (Throwable e) {
			e.printStackTrace ();
			System.exit (1);
		}
	}

	public static void main (String [] args) {
		new Thread (new TrieMatching ()).start ();
	}
}
