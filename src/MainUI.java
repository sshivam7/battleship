import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

/*
 Author: Shivam Sood
 Date: January 15th 2020
 Description: Main user interface for the battle ship game. Provides the user with the ability
 to create and connect to servers, to play alone against bots, and access to game help.

                                        Method List:
 - public MainUI() - sets up the graphical user interface that connects the user to the other
   parts of the battle ship game
 - public static void main(String[] args) - method runs the class constructor to create the GUI
 - public boolean validPort(int port) - method to check if the port number entered by the user
   falls within the specified range (1024 - 9999)
 - public void actionPerformed(ActionEvent evt) - method listens to button clicks and performs
   different actions depending on the button clicked
 */

public class MainUI extends JFrame implements ActionListener {
	//private instance data
	private JButton btnStart, btnConnect, btnHelp, btnSinglePlayer;        //window components
	private JTextField txtPortNum;
	private JLabel lblPort, lblTitle, lblShip;

	private final int HEIGHT = 750;                                        //constant variables for window size
	private final int WIDTH = 600;


	//class constructor that sets up the graphical user interface
	public MainUI() {
		super("BattleShip");         //creates window with given title

		//sets the layout to null and the background color to black
		this.setLayout(null);
		this.getContentPane().setBackground(Color.BLACK);

		//places the game title across the top of the window
		lblTitle = new JLabel("Battle Ship");
		lblTitle.setBounds(90,20,600,70);
		lblTitle.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 60));
		lblTitle.setForeground(Color.GREEN);
		this.add(lblTitle);

		//reads an image file and places the image in the centre of the window
		try {
			lblShip = new JLabel(new ImageIcon(ImageIO.read(new FileInputStream("src/ship.png"))));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Image could not load");
		}
		lblShip.setBounds(10, 100, 550,400);
		this.add(lblShip);

		//places the start button (bottom left)
		btnStart = new JButton("Create a Server");
		btnStart.setBounds(30,600,250,30);
		btnStart.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		btnStart.setBackground(Color.GREEN);
		btnStart.setFocusable(false);
		btnStart.setToolTipText("Click to start a server - enter a valid port number first");
		btnStart.addActionListener(this);
		this.add(btnStart);

		//places the connect button (bottom right)
		btnConnect = new JButton("Connect to Server");
		btnConnect.setBounds(310,600,250,30);
		btnConnect.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		btnConnect.setBackground(Color.GREEN);
		btnConnect.setFocusable(false);
		btnConnect.setToolTipText("Click to connect to an existing server - enter a valid port number first");
		btnConnect.addActionListener(this);
		this.add(btnConnect);

		//places the single player button (lower bottom left)
		btnSinglePlayer = new JButton("Singe Player");
		btnSinglePlayer.setBounds(30,650,250,30);
		btnSinglePlayer.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		btnSinglePlayer.setBackground(Color.GREEN);
		btnSinglePlayer.setFocusable(false);
		btnSinglePlayer.setToolTipText("Click to run a single player game against AI");
		btnSinglePlayer.addActionListener(this);
		this.add(btnSinglePlayer);

		//places the help button (lower bottom right)
		btnHelp = new JButton("Help");
		btnHelp.setBounds(310,650,250,30);
		btnHelp.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		btnHelp.setBackground(Color.GREEN);
		btnHelp.setFocusable(false);
		btnHelp.setToolTipText("Click for help");
		btnHelp.addActionListener(this);
		this.add(btnHelp);

		//places the label to explain the need for the text field used for the port number
		lblPort = new JLabel("Port Number:");
		lblPort.setBounds(400,550,120,30);
		lblPort.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		lblPort.setForeground(Color.GREEN);
		this.add(lblPort);

		//places the textfield to get the port number to run LAN games
		txtPortNum = new JTextField();
		txtPortNum.setBounds(500,550,50,30);
		txtPortNum.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		txtPortNum.setBackground(Color.GREEN);
		txtPortNum.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		txtPortNum.setToolTipText("Select a number between 1024 to 9999");
		this.add(txtPortNum);

		//sets window features and makes the window visible to users
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setLocation(200,0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * main method used to run the constructor
	 */

	public static void main(String[] args) {
		MainUI ui = new MainUI();
	}

	/**
	 * method to check if port number falls within the given range
	 * @param port takes integer port and an input
	 * @return true or false depending on whether the port is between the given range
	 */
	public boolean validPort(int port) {
		if(port >= 1024 && port <= 9999)    //number must be between 1024 and 9999
			return true;
		else
			return false;
	}

	/**
	 * method to listen to button actions and perform tasks accordingly
	 */
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnStart) {
			boolean validP = false;
			try {
			validP = validPort(Integer.parseInt(txtPortNum.getText()));
			}
			catch (NumberFormatException e) {
			}
			
			
			if (validP) {
				Thread t = new Thread() {
					public void run() {
						Server s = new Server(Integer.parseInt(txtPortNum.getText()));

					}
				};

				Thread t1 = new Thread() {
					public void run() {
						try {
							ClientUI c = new ClientUI();
							c.runGame(Integer.parseInt(txtPortNum.getText()));
							setVisible(false);
						} catch (IOException e) {
						}

					}
				};

				t.start();
				t1.start();
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Invalid port");
			}
			
			
		}
		else if (evt.getSource() == btnConnect) {
			
			boolean validP = false;
			try {
			validP = validPort(Integer.parseInt(txtPortNum.getText()));
			}
			catch (NumberFormatException e) {
			}
			
			
			if (validP) {
			
			Thread t = new Thread() {
				public void run() {
					ClientUI c;
					try {
						c = new ClientUI();
						c.runGame(4444);
					} catch (IOException e) {
					}

				}
			};
			t.start();
			this.dispose();
			
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Invalid Port");
			}
		}
		else if(evt.getSource() == btnHelp) {
			try {
				HelpUI help = new HelpUI();   //creates a HelpUI object to open help menu
			} catch (IOException e) {
				//displays an error message if help file is unable to load
				JOptionPane.showMessageDialog(null, "Unable to Locate Help File");
			}
		}
		else if (evt.getSource() == btnSinglePlayer) {
			Thread t = new Thread() {
				public void run() {
					try {
						ClientUI c = new ClientUI();
						c.runGameAgainstAI();


					} catch (IOException e) {
						e.printStackTrace();
					}
				}};
				t.start();
				this.dispose();
		}
	}
}