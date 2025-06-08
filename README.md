
# 🧩 Sudoku Solver

A Java-based Sudoku Solver and Visualizer that demonstrates algorithmic problem-solving using custom data structures like `LinkedList`, `Stack`, and `Queue`. This project allows solving, testing, and displaying Sudoku boards through a terminal interface and GUI rendering via Java Swing.

---

## 📂 Project Structure

```
Project5/
├── src/
│   ├── Board.java              # Handles board representation and logic
│   ├── Cell.java               # Represents individual Sudoku cells
│   ├── Sudoku.java             # Contains main solving algorithm
│   ├── SudokuTests.java        # Unit tests for Sudoku functionalities
│   ├── Stack.java              # Custom stack implementation
│   ├── Queue.java              # Custom queue implementation
│   ├── LinkedList.java         # Custom linked list used by Stack/Queue
│   ├── StackTests.java         # Unit tests for stack operations
│   ├── LinkedListTests.java    # Unit tests for linked list
│   ├── LandscapeDisplay.java   # Visual rendering of the board (Swing)
│   ├── board1.txt              # Sample Sudoku input file
│   ├── board2.txt              # Another sample input
│   ├── Revisedboard.txt        # Alternate test board
├── Project5 Report.pdf         # Detailed explanation of logic and implementation
```

---

## 🚀 Features

- ✅ Custom Solver Algorithm using backtracking
- 📚 Modular Design with separation of concerns (Board, Cell, Solver)
- 🛠️ Custom Data Structures: Linked List, Stack, and Queue
- 🔍 Unit Tests for all core structures and components
- 🎨 Graphical Display using Java Swing for real-time visualization
- 📥 Multiple Board Input Support from text files

---

## 🧠 How It Works

1. The Sudoku board is read from a `.txt` file.
2. A `Board` object initializes cells using the `Cell` class.
3. The `Sudoku` class performs solving using a backtracking algorithm.
4. Optionally, `LandscapeDisplay` animates solving using Swing.
5. Utility data structures (like Stack, Queue, and LinkedList) are used instead of built-in Java collections.

---

## 📦 Installation & Running

### Prerequisites
- Java JDK 8 or above
- IDE like IntelliJ, Eclipse, or command line terminal

### To Compile and Run
```bash
javac *.java
java Sudoku
```

Or to view the animated solution:
```bash
javac *.java
java LandscapeDisplay
```

---

## 🧪 Running Tests

Run the following to execute test cases:

```bash
javac StackTests.java LinkedListTests.java SudokuTests.java
java StackTests
java LinkedListTests
java SudokuTests
```

---

## 📁 Input Format (board1.txt / board2.txt / Revisedboard.txt)

Text files contain 9 lines of 9 digits (0 for empty):

```
530070000
600195000
098000060
800060003
400803001
700020006
060000280
000419005
000080079
```

---

## 🎯 Objectives & Learning Outcomes

- Understand custom data structure implementation in Java
- Practice backtracking algorithm on Sudoku
- Apply OOP principles to a real-world logic puzzle
- Visualize algorithmic steps using GUI
- Write and maintain modular, testable Java code

---

## 📖 Documentation

See `Project5 Report.pdf` for a full breakdown of:

- Design choices
- Algorithmic logic
- Class responsibilities

---

## 👨‍💻 Author

**Azeem Gbolahan**  
Student of Computer Science & Economics  
Colby College




---

## 📝 License

This project is licensed under the MIT License.

---

## 🌐 Let’s Connect

Interested in algorithm visualizations, Java simulations, or educational tools?
Feel free to fork, star ⭐, or reach out via GitHub!
