/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;


/**
 * This class represents the game state,
 * a dynamic object of this class keeps track
 * of what state the board currently is in and 
 * updates it whenever anything happens.
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;
	private GomokuClient client;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	
	private int currentState;	
	private String message;
	
	/**
	 * The constructor takes the client as a paremeter
	 * so that it can connect and communicate with it.
	 * It then states that initially there is no game going on
	 * and then it creates the board.
	 */
	public GomokuGameState(GomokuClient gc){ //init
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * This method returns the string message visible at
	 * the bottom of the game.
	 */
	public String getMessageString(){ //meddelandet för student returneras
		return message;
	}
	
	/**
	 * This method returns the game grid.
	 */
	public GameGrid getGameGrid(){//return object av gamegrid
		return gameGrid;
	}

	/**
	 * This method attempts to make a move to a certain box.
	 * If it is "my" turn and the box the player wants to move to is empty
	 * the player will get control over that box.
	 */
	public void move(int x, int y){// movefunktion för vad som sker ifall spelarens tur
		if(currentState == MY_TURN) {
			if(gameGrid.move(x, y, GameGrid.ME)) {
				if(gameGrid.isWinner(GameGrid.ME)) {
					currentState = FINISHED;
					message = "You won!";
					client.sendMoveMessage(x, y);
				} else {
					client.sendMoveMessage(x,y);
					currentState = OTHER_TURN;
					message = "Others player's turn.";
				}
				setChanged();
				notifyObservers();
			} else {
			message = "Move could not be made.";
			}
		} else if(currentState == NOT_STARTED) {
			message = "Not started.";
		} else if(currentState == FINISHED) {
			message = "Game is finished";
		} else {
			message = "Not your turn.";
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * This method creates a new game by clearing the board and
	 * telling the other player it's their turn.
	 */
	public void newGame(){// Vad som ska ske i currentstate vid newgame
		gameGrid.clearGrid();
		currentState = OTHER_TURN;
		message = "You started a new game, other player's turn.";
		client.sendNewGameMessage();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * This method is called when the other player starts a game.
	 * The method will clear the board and it will be "my" turn.
	 */
	public void receivedNewGame(){ // Vad som sker vid recieved när new game kallas.
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "Other player started new game, your turn.";
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * This method is called when the other player
	 * disconnects. It will stop the game, clear the board
	 * and inform "me" that the other player left.
	 */
	public void otherGuyLeft(){ // Om en lämnar
		
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "Other player left.";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * This method is called when "me" disconnects. It will
	 * stop the game, clear the board and inform "me" that 
	 * "me" was disconnected.
	 */
	public void disconnect(){ // vid disconnect
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "You disconnected.";
		setChanged();
		notifyObservers();
		client.disconnect();
	}
	
	/**
	 * This method is called when the other player makes a move.
	 * The method will check if the move was legit and then check
	 * if the other player won the game with that move. If he did the
	 * game ends, otherwise it's "my" turn.
	 */
	public void receivedMove(int x, int y){// när Other gjort ett drag.
		if(currentState == OTHER_TURN) {
			if(gameGrid.move(x, y, GameGrid.OTHER)) {
				if(gameGrid.isWinner(GameGrid.OTHER)) {
					currentState = FINISHED;
					message = "Other person won!";
				} else {
					currentState = MY_TURN;
					message = "Your turn.";
				}
			} 
		} else if(currentState == NOT_STARTED) {
			message = "Not started.";
		} else {
			message = "Not your turn.";
		}
		setChanged();
		notifyObservers();
	}
	/**
	 * This method is called to determine the initial game state when a game starts.
	 */
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();
		
	}
	
}
