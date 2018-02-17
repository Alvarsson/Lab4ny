package lab4.data;
import javafx.util.*;
import java.util.Observable;


/**
 * The class GameGrid represents the board,
 * a dynamic object of this class is the board
 * you play on. 
 *
 */

public class GameGrid extends Observable{
	public static final int ME = 1;
	public static final int OTHER = 2; 
	public static final int EMPTY = 0;
	private int size;
	public int [][] multiarray;
	public static final int INROW = 5;
	
	/**
	 * Constructor that creates a board with the size
	 * that is taken as a parameter for the dynamic object.
	 */
	
	public GameGrid(int size){
		this.size = size;
		multiarray = new int[size][size];
		startArray();
	}
	
	/**
	 * This method creates all the small boxes of the game,
	 * the initial state of each box is EMPTY, which means
	 * that no player controls that box.
	 */
	
	private void startArray() {
		for(int x = 0; x < multiarray.length; x++) {
			for(int y = 0; y < multiarray.length; y++) {
				multiarray[x][y] = EMPTY;
			}
		}
	}
	
	/**
	 * This method returns the value of a specific box,
	 * in other words it checks whether a box at position (x,y) 
	 * is controlled by "me", "other" or if it's empty.
	 */
	public int getLocation(int x, int y){
		return multiarray[x][y];
	}
		
	/**
	 * This method checks the size of the grid.
	 */
	public int getSize(){
		return this.size;
	}
	
	/**
	 * This method attempts making a move to a certain
	 * box on the board. If no player has control over
	 * that specific box the move will happen. However if
	 * the box is not empty the move will not be made, instead
	 * the method just returns false to say that the move was not
	 * possible.
	 */
	public boolean move(int x, int y, int player){
		if(multiarray[x][y] == EMPTY) {
			multiarray[x][y] = player;
			setChanged();
			notifyObservers();
			return true;
			
		} else {
			return false;
		}
	}
		
	/**
	 * This method calls the function startArray() which creates
	 * the board with only empty boxes, which means that it clears the board.
	 */
	public void clearGrid(){
		startArray();
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * This method returns true if any of the winning conditions are met,
	 * the winning conditions are checked in help functions.
	 */
	public boolean isWinner(int player){
		return checkHorizontal(player) || checkVertical(player) || checkDiagonal(player);
		
	}
	/**
	 * This method is a private help function that checks if
	 * the player has enough boxes vertically in a row to win the game.
	 */
	private boolean checkVertical(int player) { 
		int rows = multiarray.length;
		int cols = multiarray[0].length;
		int count = 0;
		for(int x = 0; x < rows; x++) {
			for(int y = 0; y < cols; y++) {
				if(multiarray[x][y] == player) {
					count++;
				}
				if(multiarray[x][y] != player) {
					count = 0;
				}
				if(INROW == count) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * This method is a private help function that checks if
	 * the player has enough boxes horizontally in a row to win the game.
	 */
	private boolean checkHorizontal(int player) {
		int cols = multiarray[0].length;
		int rows = multiarray.length;
		int count = 0;
		for(int y = 0; y < cols; y++) {
			for(int x= 0; x < rows; x++) {
				if(multiarray[x][y] == player) {
					count++;
				} 
				if(multiarray[x][y] != player) {
					count = 0;
				}
				if(INROW == count) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * This method is a private help function that checks if
	 * the player has enough boxes diagonally in a row to win the game.
	 */
	private boolean checkDiagonal(int player) { 
		int cols = multiarray[0].length;
		int rows = multiarray.length;
		int count = 0;
		int dCountX, dCountY;
		for(int x = 0; x < rows; x++) {
			for(int y = 0; y < cols; y++) {
				if(multiarray[x][y] == player) {
					dCountX = x;
					dCountY = y;
					while(multiarray[dCountX][dCountY] == player && x+INROW <= size && y+INROW <= size) {
						dCountX++;
						dCountY++;
						count++;
						if(INROW <= count) {
							System.out.println("printout: " +count );
							return true;
						}
					}
					dCountX = x;
					dCountY = y;
					count = 0;
					while(multiarray[dCountX][dCountY] == player && x-INROW >= -1 && y+INROW <= size) {
						dCountX--;
						dCountY++;
						count++;
						if(INROW <= count) {
							return true;
						}
					}
					count = 0;
				}
				
			}
		}
		return false;
	}
}
	
