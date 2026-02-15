**DSA Algorithm Visualizer**
An interactive web-based application that visually demonstrates how common Data Structures & Algorithms work step by step — powered by a Java algorithm engine and a JavaScript frontend for real-time visualization.

**Problem This Project Solves**
Understanding algorithms through textbooks or static code makes it hard to visualize:
How elements move during sorting
How comparisons happen internally
Why time complexity differs between algorithms

**This project solves that by:**
Showing every step of each algorithm in motion
Tracking comparisons & swaps in real time
Turning abstract logic into visual learning

**How Step Tracking Works**
Each algorithm is implemented in Java to return:
Array state after every operation
Number of comparisons
Number of swaps

**Flow:**
User selects algorithm (UI - JS)
        ↓
Java processes algorithm step-by-step
        ↓
Each step returned as snapshot
        ↓
JavaScript animates the transitions


**This allows:**
• Smooth animation
• Accurate internal logic
• Performance metric display

**Algorithms Implemented**

# Sorting Algorithms
Bubble Sort
Selection Sort
Insertion Sort
Merge Sort
Quick Sort

# Searching Algorithms
Linear Search
Binary Search

**Each algorithm includes:**
✔ Step-by-step array updates
✔ Comparison counter
✔ Swap counter

**Java + JavaScript Interaction**
Layer	Responsibility
Java	Algorithm logic + step generation
Java	Tracks operations (swaps/comparisons)
JavaScript	UI animation + controls
JavaScript	Visual bars & transitions

Data is passed as structured step arrays which JS renders visually.

**Tech Stack**
# Backend Logic:
Java (algorithm engine)

# Frontend:
HTML
CSS
Javascript

# **Tools:**
Git & GitHub
Browser-based UI

**Key Learning Outcomes**
Deep understanding of DSA internals
Algorithm optimization awareness
Frontend animation logic
Cross-language data handling
Clean project architecture

**Future Enhancements**
Add stack & queue visualizers
Graph traversal animations
Time complexity graphs
Speed control slider