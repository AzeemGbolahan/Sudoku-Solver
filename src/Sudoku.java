//Azeem

import java.util.Random;

public class Sudoku {

    private Board board;
    private LandscapeDisplay ld;
    private int delay;
    private int iterationCount = 0; // Counter to track iterations
    private long timeToSolve; //time to solve a board

    /**
     * Constructor that creates a board with randomly locked values.
     */
    public Sudoku(int numLocked, int delay) {
        this.board = new Board(numLocked);
        this.delay = delay;

        if (delay > 0) {
            this.ld = new LandscapeDisplay(board);
        }
    }
    
    //it gives the time taken to solve a board
    public long trackSolveTime() {
        long startTime = System.nanoTime();  // Start time in nanoseconds
    
        // Call the solving method
        boolean result = solveRevised();
    
        long endTime = System.nanoTime();  // End time in nanoseconds
        this.timeToSolve = endTime - startTime;  // Store the time taken to solve
    
        return this.timeToSolve;  // Return the time in nanoseconds
    }
    

        // Other fields and methods for Sudoku solver

        public int getIterationCount() {
            return iterationCount;  // Method to access the count
        }

    /**
     * Constructor that loads a board from a file.
     */
    public Sudoku(String filename, int delay) {
        this.board = new Board(filename);
        this.delay = delay;

        if (delay > 0) {
            this.ld = new LandscapeDisplay(board);

        }
    }

    /**
     * Finds the next valid value for the given cell that hasn't been tried yet.
     * Tries values from currentValue + 1 to 9.
     * Returns the valid value if found, otherwise 0.
     */
    public int findNextValue(Cell cell) {
        int currentValue = cell.getValue();

        for (int value = currentValue + 1; value <= 9; value++) {
            if (board.validValue(cell.getRow(), cell.getCol(), value)) {
                return value;
            }
        }

        return 0; // no valid value found
    }
    



    // Optional: Add methods to step through solving if required

    public Cell findNextCell() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Cell cell = board.get(row, col);
                
                // Only consider unlocked, empty cells
                if ( cell.getValue() == 0) { // found an empty
                    int nextVal = findNextValue(cell);
                    if (nextVal != 0) {
                        cell.setValue(nextVal);
                        return cell;
                    }

                    else{
                        return null; // No valid move found

                    }
                }
                    
                    
            }
        }
        return null; // No empty cells found
    
        
    }
    //it genrate random board
    public void generateRandomBoard(int numLocked) {
        Random rand = new Random();
        for (int i = 0; i < numLocked; i++) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            int value = rand.nextInt(9) + 1; // Generate a value between 1 and 9

            // Ensure no conflicts in the randomly filled cell
            while (!board.validValue(row, col, value)) {
                value = rand.nextInt(9) + 1; // Keep generating until it's valid
            }

            board.set(row, col, value);  // Set the value in the board
        }
    }
    //it runs the experiment with varied number of boards 
    public void runExperiment(int numTrials) {
        // Results tracking
        int[] solvedCounts = new int[41];  // Track how many are solved for each case (0 to 40 initial values)
        int[] timeoutCounts = new int[41]; // Track how many timeouts for each case

        // Run experiments for different numbers of initial values (0 to 40)
        for (int numLocked = 0; numLocked <= 40; numLocked++) {
            int solved = 0;
            int timedOut = 0;

            // Run the specified number of trials for each number of initial values
            for (int trial = 0; trial < numTrials; trial++) {
                generateRandomBoard(numLocked);  // Generate a random board with 'numLocked' initial values

                boolean result = solveRevised();  // Use your solveRevised method
                if (result) {
                    solved++;
                } else {
                    timedOut++;
                }
            }

            // Store the results
            solvedCounts[numLocked] = solved;
            timeoutCounts[numLocked] = timedOut;
        }

        // Print the results
        System.out.println("Number of Initial Values | Solved Count | Timeout Count");
        for (int i = 0; i <= 40; i++) {
            System.out.println(i + " | " + solvedCounts[i] + " | " + timeoutCounts[i]);
        }
    }

    //it finds the next valid cell to put a value on the board
    public Cell findNextCellRevised() {
        Cell bestCell = null;
        int bestCandidateCount = 10; // Initialize to a value higher than the maximum number of candidates (9)
    
        // Loop through all the cells on the board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.value(i, j) == 0) {  // For each empty cell
                    // Count the number of viable candidates for the current cell
                    int candidateCount = 0;
                    for (int num = 1; num <= 9; num++) {
                        if (this.board.validValue(i, j, num)) {  // Check if the number is a valid candidate for this cell
                            candidateCount++;
                        }
                    }
    
                    // If this cell has fewer candidates, update bestCell
                    if (candidateCount < bestCandidateCount) {
                        bestCandidateCount = candidateCount;  // Update best candidate count
                        bestCell = board.get(i, j);  // Set this cell as the best cell with the least remaining candidates
                    }
                }
            }
        }
    
        // If no empty cell is found, return null (board is solved or no valid moves)
        if (bestCell == null) {
            return null;
        }
    
        // Try to find the next valid candidate for this cell
        int candidate = findNextValue(bestCell);  // Get the next valid candidate for this cell
        if (candidate != 0) {  // If a valid candidate is found
            board.set(bestCell.getRow(), bestCell.getCol(), candidate);  // Set the candidate in the board
            return board.get(bestCell.getRow(), bestCell.getCol());  // Return the updated cell
        }
    
        return null;  // Return null if no valid candidate is found
    }
    
    //it solves the board by changing the values and observing whether they fit in on the board 
    public boolean solve() {

        
        Stack<Cell> stack = new LinkedList<>();
        boolean finished = this.board.getFinished();
        
    
        // Count the number of non-locked (modifiable) cells
        int totalEmptyCells = 0; //unspecified cells 
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                Cell cell = board.get(r, c);
                if ( cell.getValue() == 0) {
                    totalEmptyCells++;
                }
            }
        }
        
    
        while (stack.size() < totalEmptyCells) {

            if (delay > 0){
                try {
                    Thread.sleep(delay);
                    ld.repaint();
                    
                } catch (InterruptedException e) {
                    System.err.println("Sleep interrupted: " + e.getMessage());
                }
                
            }

            Cell next = findNextCell();
    
            // Backtrack if we hit a dead-end
            while (next == null && stack.size() != 0) {
                Cell last = stack.pop();
                int nextVal = findNextValue(last);
                last.setValue(nextVal);
    
                if (last.getValue() != 0) {
                    next = last;
                }
            }
    
            if (next == null) {
                // No valid solution found
                this.board.setFinished(true);
                return false;
            } else {
                stack.push(next);
            }
    
           
        }
        this.board.setFinished(true);
        return this.board.getFinished();
    }
    //it solves the board using the findnextcells revised

    public boolean solveRevised() {
        LinkedList<Cell> stack = new LinkedList<>();
        int unspecifiedCells = 0;
        int iterations = 0;
    
        // Count the number of unspecified cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.value(i, j) == 0) {
                    unspecifiedCells++;
                }
            }
        }
    
        while (stack.size() < unspecifiedCells) {
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                ld.repaint();
            }
    
            Cell next = findNextCellRevised();
    
            while (next == null && !stack.isEmpty()) {
                Cell previous = stack.pop();
                int newValue = findNextValue(previous);
                previous.setValue(newValue);
                if (previous.getValue() != 0) {
                    next = previous;
                }
                iterations++;
                if (iterations > 100000000) {
                    board.setFinished(true);
                    return true;
                }
            }
    
            if (next == null) {
                board.setFinished(true);
                return false;
            } else {
                stack.push(next);
            }
        }
        board.setFinished(true);
        return true;
    }
    
    
    /**public long solveRevisedWithTime() {
        LinkedList<Cell> stack = new LinkedList<>();
        int unspecifiedCells = 0;
        long startTime = System.nanoTime();  // Record start time
        
        // Count the number of unspecified cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.value(i, j) == 0) {
                    unspecifiedCells++;
                }
            }
        }
    
        while (stack.size() < unspecifiedCells) {
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                ld.repaint();
            }
            Cell next = findNextCellRevised();
    
            while (next == null && !stack.isEmpty()) {
                Cell previous = stack.pop();
                int newValue = findNextValue(previous);
                previous.setValue(newValue);
                if (previous.getValue() != 0) {
                    next = previous;
                }
            }
    
            if (next == null) {
                board.setFinished(true);
                return -1;  // Return -1 for failure
            } else {
                stack.push(next);
            }
        }
        
        long endTime = System.nanoTime();  // Record end time
        long duration = endTime - startTime;  // Calculate time taken
        board.setFinished(true);
        return duration;  // Return time taken in nanoseconds
    }**/
    

    /**public static void main(String[] args) {
        Sudoku sudoku;
    
        // Case 1: Use a board file if a filename is given
        if (args.length > 0) {
            String filename = args[0];
            int delay = (args.length > 1) ? Integer.parseInt(args[1]) : 0;
    
            System.out.println("Loading board from file: " + filename);
            sudoku = new Sudoku(filename, delay);
        } 
        // Case 2: Otherwise, generate a random board with numLocked values
        else {
            int numLocked = 20;  // you can adjust how many cells are pre-filled
            int delay = 10;     // visualization speed in milliseconds
            System.out.println("Generating random board...");
            sudoku = new Sudoku(numLocked, delay);
        }
    
        System.out.println("Initial board:");
        System.out.println(sudoku.board);
    
        boolean result = sudoku.solveRevised();
    
        System.out.println("\nSolved board:");
        System.out.println(sudoku.board);

        System.out.println("Numbers of Iterations required to solve:  " + sudoku.getIterationCount());
    
        if (result) {
            System.out.println("✅ Board successfully solved!");
        } else {
            System.out.println("❌ Could not solve the board.");
        }
    }**/


    public static void main(String[] args) {
        Sudoku sudoku;
        int numLocked = 20;  // Default number of initially filled cells
        int delay = 10;      // Default visualization speed in milliseconds
        
        // Check if command-line arguments are provided
        if (args.length > 0) {
            // First argument: number of initially filled cells
            numLocked = Integer.parseInt(args[0]);
            
            // Second argument: delay for visualization (optional)
            if (args.length > 1) {
                delay = Integer.parseInt(args[1]);
            }
        }
    
        // Generate a random board with the specified number of initial values
        System.out.println("Generating random board with " + numLocked + " initially filled cells...");
        sudoku = new Sudoku(numLocked, delay);
    
        // Print initial board
        System.out.println("Initial board:");
        System.out.println(sudoku.board);
    
        // Track and print the time to solve
        long timeTaken = sudoku.trackSolveTime();
    
        // Print the solved board and iteration count
        System.out.println("\nSolved board:");
        System.out.println(sudoku.board);
        System.out.println("Time to solve the board: " + timeTaken + " nanoseconds");
    
        if (sudoku.solveRevised()) {
            System.out.println("✅ Board successfully solved!");
        } else {
            System.out.println("❌ Could not solve the board.");
        }
    }
    
    
    /*public static void main(String[] args) {
        Sudoku sudoku;
        
        // Case 1: Use a board file if a filename is given
        if (args.length > 0) {
            String filename = args[0];
            int delay = (args.length > 1) ? Integer.parseInt(args[1]) : 0;
            
            System.out.println("Loading board from file: " + filename);
            sudoku = new Sudoku(filename, delay);
        } 
        // Case 2: Otherwise, generate a random board with numLocked values
        else {
            int numLocked = 20;  // You can adjust how many cells are pre-filled
            int delay = 10;      // Visualization speed in milliseconds
            System.out.println("Generating random board...");
            sudoku = new Sudoku(numLocked, delay);
        }
    
        // Print initial board
        System.out.println("Initial board:");
        System.out.println(sudoku.board);
    
        // Run the Sudoku solver
        boolean result = sudoku.solveRevised();

        
        // Print the solved board and iteration count
        System.out.println("\nSolved board:");
        System.out.println(sudoku.board);
        System.out.println("Number of Iterations required to solve: " + sudoku.getIterationCount());
    
        if (result) {
            System.out.println("✅ Board successfully solved!");
        } else {
            System.out.println("❌ Could not solve the board.");
        }
    
        // Additional Experiment (if no file argument is passed)
        if (args.length == 0) {
            System.out.println("\nRunning experiment to analyze the relationship between initial values and solution likelihood:");
            // Run the experiment with 5 trials for each case from 0 to 40 initial values
            sudoku.runExperiment(50);  // Run the experiment with 5 trials for each case
        }
    }*/
    


}

