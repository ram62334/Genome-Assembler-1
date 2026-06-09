# Genome Assembly Engine

A Java-based implementation for processing biological sequences and reconstructing complete genome sequences from fragmented inputs.

The project applies a combination of sequence processing techniques and graph-based assembly methods to transform raw sequence fragments into a reconstructed genome.

It includes implementations of classical string and sequence algorithms such as pattern matching (KMP), prefix-based preprocessing, trie and suffix tree structures, and Burrows–Wheeler Transform (BWT) along with its inverse for sequence transformation and reconstruction.

The assembly phase builds a De Bruijn graph from k-mers and reconstructs the original sequence using Eulerian path traversal over the graph.

## Key Components

- Pattern matching using KMP algorithm  
- Prefix array computation for efficient matching  
- Trie-based sequence storage and lookup  
- Suffix tree representation for substring operations  
- Burrows–Wheeler Transform (BWT) for sequence transformation  
- Inverse BWT for reconstruction  
- De Bruijn graph construction from k-mers  
- Eulerian path traversal for genome reconstruction  

## Tech Stack

- Java (JDK 23)  
- Graph algorithms  
- String processing algorithms  
- Object-oriented implementation  

---

## Output

The system reconstructs complete genome sequences from fragmented inputs using a combination of sequence transformation, indexing, and graph traversal techniques.

---

## Author

Ram Nivas Y
