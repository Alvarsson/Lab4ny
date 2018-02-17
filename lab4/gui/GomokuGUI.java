package lab4.gui;
import java.awt.Container;
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
	private static GamePanel gameGridPanel;
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
		/*this.gamestate = g;
		this.gamestate.addObserver(this);*/
		
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		JFrame frame = new JFrame("fishdix");
		GamePanel gameGridPanel = new GamePanel(this.gamestate.getGameGrid());
		gameGridPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
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
		
		Container contentPane = frame.getContentPane();
		SpringLayout layout = new SpringLayout();
		
		contentPane.setLayout(layout);
		contentPane.add(messageLabel);
		contentPane.add(connectButton);
		contentPane.add(newGameButton);
		contentPane.add(disconnectButton);
		contentPane.add(gameGridPanel);
		//INGEN ANING OM DETTA BLIR KORREKT
		
		layout.putConstraint(SpringLayout.WEST, contentPane, 1, SpringLayout.WEST, gameGridPanel);
		layout.putConstraint(SpringLayout.NORTH, contentPane, 1, SpringLayout.NORTH, gameGridPanel);
		
		layout.putConstraint(SpringLayout.WEST, contentPane, 3, SpringLayout.WEST, connectButton);
		layout.putConstraint(SpringLayout.NORTH, gameGridPanel, 3,SpringLayout.NORTH, connectButton);
		layout.putConstraint(SpringLayout.WEST, connectButton, 3, SpringLayout.WEST, newGameButton);
		layout.putConstraint(SpringLayout.WEST, newGameButton, 3, SpringLayout.WEST, disconnectButton);
		
		layout.putConstraint(SpringLayout.WEST, contentPane,3,SpringLayout.WEST, messageLabel);
		layout.putConstraint(SpringLayout.NORTH, connectButton, 5, SpringLayout.NORTH, messageLabel);
		
		/*JPanel pane = new JPanel();
		JPanel pane1 = new JPanel();
		JPanel pane2 = new JPanel();
		JPanel pane3 = new JPanel();
		pane1.add(gameGridPanel);
		pane2.add(connectButton);
		pane2.add(newGameButton);
		pane2.add(disconnectButton);
		pane3.add(messageLabel);*/
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane();
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