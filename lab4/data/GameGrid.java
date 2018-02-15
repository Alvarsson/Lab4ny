package lab4.data;
import javafx.util.*;
import java.util.Observable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{
	public static final int ME = 2;
	public static final int OTHER = 1; 
	public static final int EMPTY = 0;
	private int size;
	public int [][] multiarray;
	public static final int INROW = 5;
	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	
	public GameGrid(int size){
		this.size = size;
		multiarray = new int[size][size];
		startArray();
	}
	
	private void startArray() {
		for(int x = 0; x < multiarray.length; x++) {
			for(int y = 0; y < multiarray.length; y++) {
				multiarray[x][y] = EMPTY;
			}
		}
	}
		
		
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return multiarray[x][y];
	}
		
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
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
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		startArray();
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		if(checkHorizontal(player) != true 
				|| checkVertical(player) != true 
				|| checkDiagonal(player) != true) {
			return false;
		} 
		return true;
		
	}
	private boolean checkHorizontal(int player) { //DENNA KOLLAR ÖVER GRÄNSERNA??
		int rows = multiarray.length;
		int cols = multiarray[0].length;
		int count = 0;
		while(count != INROW) {
			for(int x = 0; x < rows; x++) {
				for(int y = 0; y < cols; y++) {
					if(multiarray[x][y] == player) {
						count++;
					} 
					
					if(multiarray[x][y] == EMPTY) {
						count = 0;
						
					}
				}
			}
		}
		return true;
	}
	private boolean checkVertical(int player) {
		int cols = multiarray[0].length;
		int rows = multiarray.length;
		int count = 0;
		while(count != INROW) {
			for(int y = 0; y < cols; y++) {
				for(int x= 0; x < rows; x++) {
					if(multiarray[x][y] == player) {
						count++;
					} 
					if(multiarray[x][y] == EMPTY) {
						count = 0;
					}
				}
			}
		}
		return true;
	}
	private boolean checkDiagonal(int player) { //Kolla efter diagonalt??
		
	}
}
	