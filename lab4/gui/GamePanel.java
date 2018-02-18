package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * This class provides a graphical view of the game board.
 * A dynamic object of this class is used to draw the
 * graphics of the game.
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20; // storlek på rutor.
	private GameGrid grid;
	
	/**
	 * The constructor takes a grid as a parameter so that
	 * it can connect with it. It then creates a starting size
	 * of the game window and sets the background color of the game
	 * to white.
	 */
	public GamePanel(GameGrid grid){//init 
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * This method returns what position of the grid a box is in
	 * given the pixel position of the box.
	 */
	public int[] getGridPosition(int x, int y){ 
		x = x/UNIT_SIZE;
		y = y/UNIT_SIZE;
		int [] coordinates = {x,y};
		return coordinates;
	}
	/**
	 *This method updates to show graphics we add during the game.
	 */
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	/**
	 * This method paints the borders of the boxes when we start the game
	 * and then fills the boxes controlled by "me" with circles
	 * and boxes controlled by "other" with crosses.
	 */
	public void paintComponent(Graphics g){ // målar ut grid och sen other och me representationerna på gridden.
		super.paintComponent(g);
		g.setColor(Color.black);
		for(int x = 0; x < grid.getSize(); x++) {
			for(int y = 0; y < grid.getSize(); y++) {
				g.drawRect(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				if(grid.getLocation(x, y) == grid.ME) {
					g.setColor(Color.blue);
					g.drawOval(UNIT_SIZE*x +1, UNIT_SIZE*y+1, UNIT_SIZE-2, UNIT_SIZE-2);
				}
				if(grid.getLocation(x, y) == grid.OTHER) {
					g.setColor(Color.red);
					g.drawLine(UNIT_SIZE*x, UNIT_SIZE*y,UNIT_SIZE*x + UNIT_SIZE , UNIT_SIZE*y + UNIT_SIZE);
					g.drawLine(UNIT_SIZE*x + UNIT_SIZE, UNIT_SIZE*y, UNIT_SIZE*x, UNIT_SIZE*y + UNIT_SIZE);
				}
				g.setColor(Color.BLACK);
			}
		}
		
	}
	/**
	 * This method returns the scale we use when we draw the game.
	 */
	public int getUnitSize() {
		return UNIT_SIZE;
	}
	
}
