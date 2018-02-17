package lab4.gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;



/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	private GamePanel gameGridPanel;
	private GameGrid gamegrid;
	private static JLabel messageLabel;
	private static JButton connectButton;
	private static JButton newGameButton;
	private static JButton disconnectButton;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		
		
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		gamegrid = gamestate.getGameGrid();
		
		JFrame frame = new JFrame("fishdix");
		GamePanel gameGridPanel = new GamePanel(this.gamestate.getGameGrid());
		gameGridPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int tempArray[] = gameGridPanel.getGridPosition(e.getX(), e.getY());
				gamestate.move(tempArray[0], tempArray[1]);
				
			}
		});
		
		JLabel messageLabel = new JLabel("Welcome To Gomoku");
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnectionWindow connected = new ConnectionWindow(client);
			}
			
		});
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gamestate.newGame();
			}
		});
		JButton disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gamestate.disconnect();
			}
		});
		frame.add(gameGridPanel, BorderLayout.NORTH);
		frame.add(connectButton, BorderLayout.WEST);
		frame.add(newGameButton, BorderLayout.CENTER);
		frame.add(disconnectButton, BorderLayout.EAST);
		frame.add(messageLabel, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane();
		int xSize = gamegrid.getSize() * gameGridPanel.getUnitSize();
		frame.setPreferredSize(new Dimension(xSize,400));
		frame.pack();
		frame.setLocation(100,100);
		frame.setVisible(true);
		
	}
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}

		
	}
	
	
}