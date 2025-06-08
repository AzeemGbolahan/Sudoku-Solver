import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.util.Random;

public class Board {
    private Cell[][] board;
    private boolean finished = false;

    /**
     * Default constructor for creating an empty 9x9 board with all cells initialized to 0.
     */
    public Board(){
        this.board = new Cell[9][9];

        for (int r = 0; r < 9; r++){
            for  (int c = 0; c < 9; c++){
                Cell cell = new Cell(r,c,0);
                this.board[r][c] = cell;
            }
        }



    }
    /**
     * Constructor for loading a board from a file.
     * @param filename The file to load the board from.
     */
    public Board(String filename){
        this.board = new Cell[9][9];

        for (int r = 0; r < 9; r++){
            for  (int c = 0; c < 9; c++){
                Cell cell = new Cell(r,c,0);
                this.board[r][c] = cell;
            }
        }

    read(filename);

    }

    /**
     * Constructor for loading a board from a file.
     * @param filename The file to load the board from.
     */

    public Board(int numFixed) {
        this.board = new Cell[9][9];
        Random rand = new Random();
    
        // Initialize the board with empty cells
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                this.board[r][c] = new Cell(r, c, 0);
            }
        }
    
        int lockedCount = 0;
        while (lockedCount < numFixed) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
    
            // Skip if already locked
            if (this.board[row][col].isLocked()) {
                continue;
            }
    
            // Try all numbers until a valid one is found
            boolean placed = false;
            for (int attempt = 0; attempt < 20 && !placed; attempt++) {
                int value = rand.nextInt(9) + 1;
                this.board[row][col].setValue(0); // Ensure current cell is clear
                if (validValue(row, col, value)) {
                    this.set(row, col, value, true);  // Set and lock
                    placed = true;
                    lockedCount++;
                }
            }
    
            // Optional: to avoid infinite loops in rare unsolvable scenarios
            if (!placed) {
                this.board[row][col].setValue(0); // reset just in case
            }
        }
    }
    
    /**
     * Get the number of columns in the board.
     * @return The number of columns (always 9).
     */
    public int getCols() {
        if (this.board.length == 0) {
            return 0;
        }
        return this.board[0].length;
    }

    /**
     * Get the number of rows in the board.
     * @return The number of rows (always 9).
     */

    public void setFinished(boolean finished){
        this.finished = finished;
    }

    /**
     * Set the finished state of the board.
     * @param finished The state of the board (whether it's solved or not).
     */

    public boolean getFinished(){
        return this.finished;
    }


    public int getRows(){
        return this.board.length;
    }
    
    /**public Cell get(int row, int col){
        if (row >= this.board.length | col >= this.board[0].length){
            throw new IllegalArgumentException(" Invalid index(ices). Row index or Column index cannot be exceed the specified number of rows and columns on the board ");
        }
        return this.board[row][col];
    }**/

    public Cell get(int r, int c){
        if (r >= this.board.length | c >= this.board[0].length){
            throw new IllegalArgumentException(" Invalid index(ices). Row index or Column index cannot be exceed the specified number of rows and columns on the board ");
        }
        return this.board[r][c];
    }

    public boolean isLocked(int r, int c){
        return this.board[r][c].isLocked();
    }

    public int numLocked() {
        int lockedCells = 0;
    
        for (int r = 0; r < this.getRows(); r++) {      // Loop through each row
            for (int c = 0; c < this.getCols(); c++) {  // Loop through each column
                if (this.board[r][c].isLocked()) {      // Check if the cell is locked
                    lockedCells++;                      // Increment count if true
                }
            }
        }
    
        return lockedCells; // Return total locked cells
    }

    public int value(int r, int c){
        return this.board[r][c].getValue();
    }

    public void set(int r, int c, int value){
        this.board[r][c].setValue(value);
    }

    public void set(int r, int c, int value, boolean locked){
        this.board[r][c].setValue(value);
        this.board[r][c].setLocked(locked);
    }

    public boolean validValue(int row, int col, int value) {
        // Check if value is within valid range
        if (value < 1 || value > 9) {
            return false;
        }
    
        // Check the row
        for (int c = 0; c < 9; c++) {
            if (c != col && this.board[row][c].getValue() == value) {
                return false;
            }
        }
    
        // Check the column
        for (int r = 0; r < 9; r++) {
            if (r != row && this.board[r][col].getValue() == value) {
                return false;
            }
        }
    
        // Check the 3x3 box
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
    
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if ((r != row || c != col) && this.board[r][c].getValue() == value) {
                    return false;
                }
            }
        }
    
        return true;
    }
    
    public boolean validSolution() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = this.board[row][col].getValue();
    
                // Value must be between 1 and 9
                if (value < 1 || value > 9) {
                    return false;
                }
    
                // Temporarily clear the cell to avoid self-comparison
                this.board[row][col].setValue(0);
                if (!validValue(row, col, value)) {
                    this.board[row][col].setValue(value); // restore value
                    return false;
                }
                this.board[row][col].setValue(value); // restore value
            }
        }
        this.finished = true;
        return this.finished;
    }
    


    /**
     * public void set(int row, int col, int value){
       this.board[row][col].setValue(value);
    }

    public void set(int row, int col, boolean locked){
        this.board[row][col].setLocked(locked);
    }
    **/

    public boolean read(String filename) {
    try {
      // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
      FileReader fr = new FileReader(filename);
      // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
      BufferedReader br = new BufferedReader(fr);
      
      // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
      String line = br.readLine();
      // start a while loop that loops while line isn't null
      int rowTracker = 0;
      while(line != null){
          // print line
	  System.out.println( line );
          // assign to an array of Strings the result of splitting the line up by spaces (line.split("[ ]+"))
          String[] arr = line.split( "[ ]+" );
          // let's see what this array holds: 
          System.out.println("the first item in arr: " + arr[0] + ", the second item in arr: " + arr[1]);
          // print the size of the String array (you can use .length)
          System.out.println( arr.length );
          // use the line to set various Cells of this Board accordingly
	  // TODO THIS IS WHAT NEEDS TO BE FILLED IN!
        //arr = board[rowTracker];
        for(int c = 0; c < 9; c++){
            this.set(rowTracker,c,Integer.parseInt(arr[c]));
        }
        
          // assign to line the result of calling the readLine method of your BufferedReader object.
          line = br.readLine();
          rowTracker++;
      }
      
      // call the close method of the BufferedReader
      br.close();
      return true;
    }
    catch(FileNotFoundException ex) {
      System.out.println("Board.read():: unable to open file " + filename );
    }
    catch(IOException ex) {
      System.out.println("Board.read():: error reading file " + filename);
    }
   
    return false;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (int r = 0; r < 9; r++) {
        if (r > 0 && r % 3 == 0) {
            sb.append("------+-------+------\n"); // horizontal block separator
        }
        for (int c = 0; c < 9; c++) {
            if (c > 0 && c % 3 == 0) {
                sb.append("| ");
            }
            int val = board[r][c].getValue();
            sb.append(val == 0 ? "0 " : val + " ");
        }
        sb.append("\n");
    }

    return sb.toString();

    
}

public void draw(Graphics g, int scale){
    for(int i = 0; i<getRows(); i++){
        for(int j = 0; j<getCols(); j++){
            get(i, j).draw(g, j*scale+5, i*scale+10, scale);
        }
    } if(finished){
        if(validSolution()){
            g.setColor(new Color(0, 127, 0));
            g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale*3+5, scale*10+10);
        } else {
            g.setColor(new Color(127, 0, 0));
            g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale*3+5, scale*10+10);
        }
    }
}


 

public static void main(String[] args) {
    if (args.length < 1) {
        System.out.println("Usage: java Board <filename>");
        return;
    }

    // Initialize the board from the file
    Board sudokuBoard = new Board(args[0]);

    // Print the board
    System.out.println("Initial Board:");
    System.out.println(sudokuBoard.toString());

    // Example: Manually lock a few cells
    sudokuBoard.set(0, 0, 5, true);  // Lock cell at (0,0) with value 5
    sudokuBoard.set(1, 3, 8, true);  // Lock another one at (1,3)

    // Show locked cell values and status
    System.out.println("Cell (0,0) value: " + sudokuBoard.value(0, 0) + " | Locked: " + sudokuBoard.isLocked(0, 0));
    System.out.println("Cell (1,3) value: " + sudokuBoard.value(1, 3) + " | Locked: " + sudokuBoard.isLocked(1, 3));

    // Count locked cells
    int numLocked = sudokuBoard.numLocked();
    System.out.println("Total locked cells: " + numLocked);

    if (sudokuBoard.validSolution()) {
        System.out.println("✅ The board is correctly solved!");
    } else {
        System.out.println("❌ The board is NOT solved.");
    }

}


}

