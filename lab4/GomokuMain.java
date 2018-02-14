package lab4;
import lab4.client.*;

public class GomokuMain {
	public static void main(String[] args) {
		//Finns bättre sätt?
		String arg = args[0]; 
		int portNumber = Integer.parseInt(arg);
		if( arg == null || portNumber < 0) {//This should check if more arguments than one.
			portNumber = 4000;
		}
		try {
			GomokuClient client = new GomokuClient(portNumber);
		} catch (Exception e) {
			bla bla bla
		}
		GomokuGameState gameState = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI();
		
		
		
	}
}
