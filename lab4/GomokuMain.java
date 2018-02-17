package lab4;
import lab4.client.*;
import lab4.data.*;
import lab4.gui.*;
/**
*
* This class simply works as a main function,
* it makes sure we have a port number and then
* it creates the dynamic objects of the different classes
* necessary for the program to run.
*
*/
public class GomokuMain {
	public static void main(String[] args) {
		
		int portNumber = Integer.parseInt(args[0]);//First argument becomes portNumber
		if( args.length != 1 || portNumber < 0) {//This should check if more arguments than one.
			portNumber = 4000;
		}
		GomokuClient client = new GomokuClient(portNumber);
		GomokuGameState gameState = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gameState, client);
		
		
		GomokuClient client2 = new GomokuClient(portNumber+1);
		GomokuGameState gameState2 = new GomokuGameState(client2);
		GomokuGUI gui2 = new GomokuGUI(gameState2, client2);
	
	}
}
