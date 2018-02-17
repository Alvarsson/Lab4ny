﻿package lab4;
import lab4.client.*;
import lab4.data.*;
import lab4.gui.*;

public class GomokuMain {
	public static void main(String[] args) {
		
		int portNumber = Integer.parseInt(args[0]);
		if( args.length != 1 || portNumber < 0) {//This should check if more arguments than one.
			portNumber = 4000;
		}
			GomokuClient client = new GomokuClient(portNumber);
		
		GomokuGameState gameState = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gameState, client);
	
	}
}
