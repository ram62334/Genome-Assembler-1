# Genome-Assembler-1
Building efficient systems to store, compress and process large DNA strands
# Genome Assembler Foundations

A Java-based bioinformatics engine for DNA sequence indexing, compression, and pattern matching using advanced string algorithms.

---

## Overview

Genome sequencing generates massive amounts of DNA fragment data that must be efficiently stored, searched, and compressed before assembly.

This project implements core string-processing algorithms used in bioinformatics systems to handle large-scale genomic data efficiently.

It serves as the **foundation layer** for a full genome assembly pipeline.

---

## Features

### 1. DNA Sequence Indexing

* Suffix Trie construction
* Compressed Suffix Trie (optimized storage)
* Fast substring lookup in DNA sequences

### 2. Data Compression

* Burrows-Wheeler Transform (BWT)
* Inverse Burrows-Wheeler Transform
* Compression and reconstruction of DNA strings

### 3. Pattern Matching

* Prefix Function (for preprocessing)
* Knuth-Morris-Pratt (KMP) algorithm
* Efficient DNA motif search in large sequences

---

## Example

### DNA Sequence

```text
ATCGATCGATCG
```

### Pattern Search

```text
Pattern: CGAT
```

### Output

```text
Pattern found at positions: 2, 6
```

---

## Project Structure

```text
src/
├── suffixtrie/        # Suffix Trie implementation
├── compression/       # Burrows-Wheeler Transform
├── search/            # KMP and pattern matching
├── utils/             # Helper utilities
└── Main.java          # Demo runner
```

---

## Tech Stack

* Java 23
* Maven (build tool)
* JUnit (testing)

---

## Algorithms Implemented

| Category    | Algorithms                             |
| ----------- | -------------------------------------- |
| Indexing    | Suffix Trie, Compressed Trie           |
| Compression | Burrows-Wheeler Transform, Inverse BWT |
| Searching   | KMP Algorithm, Prefix Function         |





MIT License
