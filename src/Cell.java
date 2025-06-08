/*
file name:      Cell.java
Author:        Azeem Gbolahan
Creates a cell object(location) to be used in the grid ( for the game )

How to run:     java -ea Cell
*/
import java.awt.Color;
import java.awt.Graphics;


public class Cell {

    /**
     * The fileds - row, column, value and locked
     */

    private int row;
    private int col;
    private int value;
    private boolean locked;

    /**
     * Constructs an empty cell- initializes the row, column, and value fields to the given parameter values
     */
    public Cell(int row, int col, int value ) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = false;
    }

    /**
     * Constructs cell- initializes the row, column, value, and locked fields to the given parameter values
     */
    public Cell(int row, int col, int value, boolean locked ) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = locked;
    }
    /**
     * return the Cell's row index
     */
    public int getRow(){
        return this.row;
    }
    /**
     * return the Cell's column index
     */
    public int getCol(){
        return this.col;
    }
    /**
     * return the Cell's value 
     */
    public int getValue(){
        return this.value;
    }
    /**
     * sets the Cell's value 
     */
    public void setValue(int newval){
        this.value = newval;
    }
    // return whether the cell has a value in it or not  
    public boolean isLocked(){
        return this.locked;
    }
    // sets the value of locked 
    public void setLocked(boolean locked){
        this.locked = locked;
    }

    /**
     * Returns a String representation of this Cell.
     * 
     * @return 1 if this Cell is alive, otherwise 0.
     */
    public String toString() {
        if (this.isLocked()){
            return " " +  this.value + " ";
        }
        return " ";
    }

    public void draw(Graphics g, int x, int y, int scale){
        char toDraw = (char) ((int) '0' + getValue());
        g.setColor(isLocked()? Color.BLUE : Color.RED);
        g.drawChars(new char[] {toDraw}, 0, 1, x, y);
    }

    public static void main(String[] args) {
        Cell cell = new Cell(1,2,4,true);
        
        System.out.println("The value in the cell:  "+  cell.toString());

        cell.setValue(9);
        System.out.println("The value in the cell:   "+  cell.toString());

        int r = cell.getRow()+1;
        System.out.println("The cell is in row   "+  r);

        int c = cell.getCol()+1;
        System.out.println("The cell is in column  "+  c);

    }



}