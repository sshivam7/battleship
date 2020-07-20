import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * 
 */

/**
 * Author Jeevan Opel
 * Description:	This class is a JFrame, and is used as the Game UI for the user. Creating a ClientUI object will draw the 
 * 				JFrame used for playing the game, and running the runGame() method will create a Client and connect to a server,
 * 				whereas running the runGameAgainstAI() will have this Game be played against an AI (CPU) player
 * 
 * Method										Description
 * 
 * ClientUI()									Sets up the JFrame UI for client interaction
 * void runGame()								Creates the a Client object for Server communication. This method runs the game
 * 												to be played against another player on a LAN
 * void runGameAgainstAI()						Creates a CPU object so the player can challenge an AI in a game of battleship
 * void actionPerformed(ActionEvent e)			When an action that is not handled by a board such as rotate, ready, or fire
 * 												occurs
 * main											Main method is an impromptu menu for users
 *
 */
public class ClientUI extends JFrame implements ActionListener{

	/**
	 * private instance data
	 */
	private PBoard pb;
	private Board eb;
	private JPanel cp, tp, sp, bp;

	private JButton btnFire, btnRotate, btnReady, btnSortUp, btnSortDown, btnSearch, btnMenu;

	private JLabel title, servermsg;

	private JTextArea txaSList;
	private JScrollPane scrSList;

	private ShotList sl;

	private int lastRow, lastCol;

	private ExperimentalClient c;

	// cpu stuff
	private CPU cpu;
	private String cpuGameTurn;


	public ClientUI() throws IOException {
		// TODO Auto-generated constructor stub
		super("Battleship v1.0");
		setSize(600, 750);
		setLayout(null);

		// setup for title panel
		tp = new JPanel();
		tp.setBounds(0,0,300,50);

		title = new JLabel("Player #");
		servermsg = new JLabel("Waiting for other player to connect...");

		title.setForeground(Color.GREEN);
		servermsg.setForeground(Color.GREEN);
		title.setFont(new Font("Monospaced", Font.BOLD, 16));
		servermsg.setFont(new Font("Monospaced", Font.PLAIN, 12));

		tp.setBackground(Color.BLACK);

		tp.add(title);
		tp.add(servermsg);

		add(tp);
		//

		// setup for player board and enemy board panels
		pb = new PBoard();
		eb = new Board();

		pb.drawBoard();
		eb.drawBoard();

		pb.setBounds(0,350,300,300);
		eb.setBounds(0,50,300,300);

		pb.setBackground(Color.BLACK);
		eb.setBackground(Color.BLACK);

		add(eb);
		add(pb);
		//

		// setup for control panel
		cp = new JPanel();
		cp.setBounds(0,650,300,100);

		btnFire = new JButton("Fire");
		btnRotate = new JButton("Rotate");
		btnReady = new JButton("Ready");
		btnMenu = new JButton("Menu");

		btnFire.addActionListener(this);
		btnRotate.addActionListener(this);
		btnReady.addActionListener(this);
		btnMenu.addActionListener(this);

		cp.add(btnFire);
		cp.add(btnRotate);
		cp.add(btnReady);
		cp.add(btnMenu);

		btnFire.setVisible(false);
		btnFire.setEnabled(false);
		btnMenu.setVisible(false);

		cp.setBackground(Color.BLACK);

		btnFire.setBackground(Color.BLACK);
		btnReady.setBackground(Color.BLACK);
		btnRotate.setBackground(Color.BLACK);
		btnMenu.setBackground(Color.BLACK);
		btnFire.setForeground(Color.GREEN);
		btnReady.setForeground(Color.GREEN);
		btnRotate.setForeground(Color.GREEN);
		btnMenu.setForeground(Color.GREEN);
		btnFire.setBorder(new LineBorder(Color.GREEN, 1));
		btnReady.setBorder(new LineBorder(Color.GREEN, 1));
		btnRotate.setBorder(new LineBorder(Color.GREEN, 1));
		btnMenu.setBorder(new LineBorder(Color.GREEN, 1));

		btnReady.setFont(new Font("Monospaced", Font.BOLD, 14));
		btnFire.setFont(new Font("Monospaced", Font.BOLD, 14));
		btnRotate.setFont(new Font("Monospaced", Font.BOLD, 14));
		btnMenu.setFont(new Font("Monospaced", Font.BOLD, 14));

		add(cp);
		//

		// Setup for shot list panel
		sp = new JPanel();
		sp.setBounds(320, 50, 260, 400);
		sp.setLayout(null);

		txaSList = new JTextArea();
		txaSList.setEditable(false);

		scrSList = new JScrollPane(txaSList);
		scrSList.setBounds(0,0,260,300);
		scrSList.setAutoscrolls(true);

		sp.add(scrSList);

		sl = new ShotList();

		btnSortUp = new JButton("up");
		btnSortDown = new JButton("down");
		btnSearch = new JButton("search");

		btnSortUp.setBounds(0,320,45,25);
		btnSortDown.setBounds(70,320,45,25);
		btnSearch.setBounds(145,320,45,25);

		btnSortUp.addActionListener(this);
		btnSortDown.addActionListener(this);
		btnSearch.addActionListener(this);

		sp.add(btnSortDown);
		sp.add(btnSortUp);
		sp.add(btnSearch);

		btnSortUp.setBackground(Color.BLACK);
		btnSortDown.setBackground(Color.BLACK);
		btnSearch.setBackground(Color.BLACK);
		btnSortUp.setForeground(Color.GREEN);
		btnSortDown.setForeground(Color.GREEN);
		btnSearch.setForeground(Color.GREEN);
		btnSortUp.setBorder(new LineBorder(Color.GREEN, 1));
		btnSortDown.setBorder(new LineBorder(Color.GREEN, 1));
		btnSearch.setBorder(new LineBorder(Color.GREEN, 1));
		
		btnSortUp.setToolTipText("Sort by date from oldest to newest");
		btnSortDown.setToolTipText("Sort by date from newest to oldest");

		sp.setBackground(Color.BLACK);
		txaSList.setBackground(Color.BLACK);
		txaSList.setForeground(Color.GREEN);
		txaSList.setBorder(new LineBorder(Color.GREEN,1));
		txaSList.setFont(new Font("Monospaced", Font.PLAIN, 11));

		add(sp);
		//

		// setup for background panel
		bp = new JPanel();
		bp.setBackground(Color.BLACK);
		bp.setBounds(0,0,600,750);
		add(bp);
		//

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		this.setLocation(200,0);

		lastRow = -1; lastCol = -1;

	}

	//Creates the a Client object for Server communication. This method runs the game
	//to be played against another player on a LAN
	public void runGame(int port) throws IOException {
		// create the client object
		c = new ExperimentalClient(port);

		title.setText(c.clientIn());
		
		c.clientIn();
		
		servermsg.setText("Place your ships and hit ready!");
		// game loop
		while (true) {
			// get the turn string from the server, the turn can either be "Wait"
			// or "Turn"
			String turn = c.clientIn();
			// if it is not this clients turn, then they must wait until the other client
			// fires, then check if the other client hit and return the corresponding boolean
			// if it hit or not
			if (turn.equals("Wait")) {
				servermsg.setText("Waiting for enemy move...");
				// disable fire button because cannot shoot
				btnFire.setEnabled(false);

				//pb.setHP(1);
				// get the position that was shot row and col
				int row = Integer.parseInt(c.clientIn());
				int col = Integer.parseInt(c.clientIn());
				// sends true if hit, false if not
				boolean hit = pb.checkEnemyShot(row, col);
				c.clientOut(Boolean.toString(hit));
				// create the shot record for the opponents shot
				sl.insert(new ShotRecord(row, col, hit, "Opponent"));
				txaSList.setText(sl.toString());

				// tells the server if the shot destroyed last ship and lost
				c.clientOut(Boolean.toString(pb.hasLost()));
				if(pb.hasLost()) {	// if the player lost
					servermsg.setText("You lost!");

					c.clientOut("Exit");
					c.endClient();
					break;
				}
			}
			// if it is the clients turn, they must select a position and fire
			else if (turn.equals("Turn")) {
				servermsg.setText("Your move!");
				// they can fire now so the button is enabled
				btnFire.setEnabled(true);

				// this is activated AFTER the client shot, because the server
				// is waiting for an input from this client. The input is sent
				// when the client presses btnFire
				boolean hit = Boolean.parseBoolean(c.clientIn());	// boolean representing if it hit

				// places a hit marker or miss marker depending on if it hit
				if (hit) {
					eb.placeHitMarker(eb.getRclick(), eb.getCclick());
				}
				else {
					eb.placeMissMarker(eb.getRclick(), eb.getCclick());
				}

				// create the shot record for this shot
				sl.insert(new ShotRecord(eb.getRclick(), eb.getCclick(), hit, "You"));
				txaSList.setText(sl.toString());

				// gets a true if this shot caused opposing client to lose
				boolean hasWon = Boolean.parseBoolean(c.clientIn());
				if (hasWon) {
					//JOptionPane.showMessageDialog(null, "You won!");
					servermsg.setText("You won!");
					c.clientOut("Exit");
					c.endClient();
					break;
				}
			}

		}
		btnMenu.setVisible(true);
	}

	//Creates a CPU object so the player can challenge an AI in a game of battleship
	public void runGameAgainstAI() throws IOException {

		// create the CPU object and initialize variables for the game
		cpu = new CPU();
		title.setText("Player 1");
		cpuGameTurn = "Picking";
		btnFire.setEnabled(true);

		boolean hasWon = false;
		// game loop
		while (true) {
			// this is reserved for when the human player is selecting a cell
			// by firing a valid shot, cpuGameTurn is set to "Picked"
			if (cpuGameTurn.equals("Picking")) {
				servermsg.setText("Select a cell and fire!");
			} 

			// upon shooting, the cpu checks if the shot hit
			// this condition is met when the btnFire is clicked
			// while selecting a valid cell
			else if (cpuGameTurn.equals("Picked")) {
				// check if the shot hit the cpu ships
				boolean hit = cpu.checkHit(eb.getRclick(), eb.getCclick());
				// place a corresponding marker
				if (hit) {
					eb.placeHitMarker(eb.getRclick(), eb.getCclick());
				} else {
					eb.placeMissMarker(eb.getRclick(), eb.getCclick());
				}
				// create the shot record
				sl.insert(new ShotRecord(eb.getRclick(), eb.getCclick(), hit, "You"));
				txaSList.setText(sl.toString());

				// if the cpu lost break, hasWon will be true
				hasWon = cpu.checkLost();
				if (hasWon) {
					break;
				}
				// set the turn to cpu
				cpuGameTurn = "cpu";
			}

			// when it is the cpu turn
			else if (cpuGameTurn.equals("cpu")) {
				// get the cpu shot, and check if it hit
				int shot[] = cpu.shoot();
				boolean hit = pb.checkEnemyShot(shot[0], shot[1]);
				// create the shot record
				sl.insert(new ShotRecord(shot[0], shot[1], hit, "CPU"));
				txaSList.setText(sl.toString());

				// if the cpu won break, hasWon will be false
				if (pb.hasLost()) {
					break;
				}
				// set the turn back to waiting for human to shoot
				cpuGameTurn = "Picking";
			}


		}
		
		btnMenu.setVisible(true);
		
		if (hasWon) {
			servermsg.setText("You won!");
		} else {
			servermsg.setText("You lost!");
		}



	}

	// when an aciton is performed
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// if btnFire pressed, make sure it is a valid cell (a new cell is
		// selected) and if it is send it to the server
		if (btnFire == e.getSource()) {
			int row = eb.getRclick();
			int col = eb.getCclick();
			// if a new cell was not selected since last shot, tell the client
			// only needs to check the last shot because the buttons are disabled
			// so previously shot buttons cannot be re selected
			if (row == lastRow && col == lastCol) {
				servermsg.setText("Please select a new cell");
			}
			else {
				try {
					// tell the server there is a shot
					// and send the row and column
					c.clientOut("clientoneshot");
					c.clientOut(Integer.toString(row));
					c.clientOut(Integer.toString(col));

					lastRow = row;
					lastCol = col;
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				} 
				// OK idk if this is bad. If the game is run against AI, the client object
				// will be null, thus a NullPointerException will be triggered
				// Since the operations for a AI game are different, if it is in the catch
				// block, then it will run normally if the game is runAgainstAI()
				// and will not run if it is runGame()
				catch (NullPointerException e2) {
					int r = eb.getRclick();
					int c = eb.getCclick();
					// ensure not same cell
					if (r == lastRow && c == lastCol) {
						servermsg.setText("Please select a new cell");
					}
					else {
						// set the turn to picked so it can get the clicked position and check
						cpuGameTurn = "Picked";
						lastRow = r;
						lastCol = c;
					}



				}
			}
		}
		// if the rotate is pressed, change the orientation
		// it is set to !pb.isVertical(), so if false it will become
		// true, and if true become false
		else if (btnRotate == e.getSource()) {
			pb.setIsVertical(!pb.getIsVertical());
		}

		// when ready is pressed
		// enable the buttons if all ships placed
		else if (btnReady == e.getSource()) {
			if (pb.getAllShipsPlaced()) {
				btnFire.setVisible(true);
				btnRotate.setVisible(false);
				btnReady.setVisible(false);

				if (!servermsg.getText().equals("Your move!")) {
					servermsg.setText("Waiting for opponent to be ready...");
				}

				// if this is player 2, tell the server that ready was pressed.
				// the game 'starts' when player 2 readies, but it does not matter
				// if player 1 is not ready because they still have to press ready on their
				// side to access the fire button, since they go first
				if(title.getText().equals("Player 2")) {
					try {
						c.clientOut("Ready");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
				}

			}
			else {

			}

		}
		
		else if(btnSortUp == e.getSource()) {
			try {
				sl.insertionSortUp();
				txaSList.setText(sl.toString());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (btnSortDown == e.getSource()) {
			try {
				sl.insertionSortDown();
				txaSList.setText(sl.toString());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (btnSearch == e.getSource()) {
			String date = JOptionPane.showInputDialog(null, "Search for a record by date", "HH:MM:ss");
			String out = "Record not found";
			try {
				sl.insertionSortUp();
				int index = sl.binarySearch(date);
				if (index != -1) {
					out = sl.getRecord(index).toString();
				}
			}
			catch (ParseException e1) {
				out = "ParseException: Incorrect date format";
			}
			JOptionPane.showMessageDialog(null, out);
		}
		else if (btnMenu == e.getSource()) {
			new MainUI();
			dispose();
		}


	}


	/**
	 * @param args
	 * @throws IOException 
	 * main UI
	 * The menuUI did not work so we had to create an impromptu menu
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientUI cui = null;

		String[] options = {"Single-Player", "Multi-Player"};
		int x = JOptionPane.showOptionDialog(null, "Select a game mode",
				"Battleship",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		if (x == 1) {
			int port = 0;
			while (true) {
				try {
					port = Integer.parseInt(JOptionPane.showInputDialog("Enter a 4-digit port between 1024 and 9999"));
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Invalid port");
				}

				if (port >= 1024 && port <= 9999) {
					break;
				}

			}

			try {
				cui = new ClientUI();
				cui.runGame(port);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Server does not exist");
				System.exit(0);
			}
		} else {
			try {
				cui = new ClientUI();
				cui.runGameAgainstAI();
			}
			catch (IOException e) {

			}
		}

	}


}
