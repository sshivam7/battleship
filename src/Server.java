import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Author: Shivam Sood (Communication), Jeevan Opel (GUI)
 * Date: January 15th 2020
 * Description: Class creates the server to communicate and deal with multiple client connections.
 *              Uses threads to connect and handle multiple clients at once.
 *
 *  Server Class: Handles communication between clients and server
 *                                    Method List:
 * - public void actionPerformed(ActionEvent evt) - listens for buttons being clicked and
 *   performs actions accordingly
 * - public static void main(String[] args) - calls class constructor to create Server GUI
 * - public static void broadcastMsg(String msg) - send a message to all connected clients
 * - public static void clientOneOut (String msg) - sends a message to client one
 * - public static void clientTwoOut (String msg) - sends a message to client two
 * - public static String clientOneIn () - reads information from client one
 * - public static String clientTwoIn () - reads information from client two
 * - public static void addFeed(String in) - method to add text to the text area on the user interface
 * - public static void closeServer() - method to close the server
 *
 *  Client Handler Class: Allows multiple clients to connect to the server (threaded)
 *                                       Method List:
 * - public ClientHandler(Socket s, DataInputStream in, DataOutputStream out) - class constructor to
 *   set socket, input, and output
 * - public void out(String msg) - sends a message out to the current client
 * - public String in() - reads a message from the current client
 * - public void run() - method to run the server client connection
 */

public class Server extends JFrame implements ActionListener {
	//private instance variables
	private static ArrayList<ClientHandler> al = new ArrayList<ClientHandler>();  //list to contain connected clients

	private JButton btnStart, btnEnd;           //frame components to be added to gui
	private JTextField txtPortNum;
	private static JTextArea txaFeed;
	private JScrollPane jscp;
	private JLabel lbl;

	//server class constructor creates and opens GUI
	public Server() {
		super("Server");                 //creates window and sets title
		setSize(400, 300);
		setLayout(null);

		//declares and inititlizes new components
		btnStart = new JButton("Start");
		btnEnd = new JButton("End");
		lbl = new JLabel("Enter 4-digit port #:");
		txtPortNum = new JTextField();

		txaFeed = new JTextArea();      //adds text area
		txaFeed.setEditable(false);

		jscp = new JScrollPane(txaFeed);     //adds scrolling to text area

		btnStart.setBounds(20,20,100,30);  //adds start button
		add(btnStart);
		btnStart.addActionListener(this);

		btnEnd.setBounds(20,60,100,30);   //adds end button
		add(btnEnd);
		btnEnd.addActionListener(this);

		lbl.setBounds(140,30,200,30);     //adds label describing port text field
		add(lbl);

		txtPortNum.setBounds(140,60,100,30);  //adds text field for port number
		add(txtPortNum);
		txtPortNum.setToolTipText("Enter a port between 1024 and 9999");

		jscp.setBounds(20,100,360,150);   //adds the scrollpane
		add(jscp);

		setDefaultCloseOperation(EXIT_ON_CLOSE);  //sets window options
		setResizable(false);
		setVisible(true);


	}
	// overloaded constructor
	public Server(int port) {
		try {
			ServerSocket sSocket = new ServerSocket(port); //creates new socket with port

			while (al.size() < 2) { //checks if less than two clients have connected
				Socket s = null;

				try {
					s = sSocket.accept();  //connects client to server
					//addFeed("Client " + (al.size() + 1) + "/2 joined");

					DataInputStream in = new DataInputStream(s.getInputStream());
					DataOutputStream out = new DataOutputStream(s.getOutputStream());

					//starts a new thread to handle new clients
					ClientHandler t = new ClientHandler(s, in, out);
					t.start();
					al.add(t);

				} catch (Exception e) {
					s.close();
				}
			}
		}
		catch (IOException e) {

		}
		catch (Exception e) {

		}
	}

	/**
	 * Method to listen to button actions to control the server
	 */
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnStart) {
			//reads port number from text area
			int port = 0;
			try {
				port = Integer.parseInt(txtPortNum.getText());
			}
			catch (NumberFormatException e) {
				addFeed("Please enter a port between 1024 and 9999");  //displays error if number is not within set values
			}
			if (port <= 9999 && port >= 1024) {  //checks if port is valid
				btnStart.setEnabled(false);         //disables start button
				btnEnd.setEnabled(true);
				try {
					ServerSocket sSocket = new ServerSocket(Integer.parseInt(txtPortNum.getText())); //creates new socket with port

					while (al.size() < 2) { //checks if less than two clients have connected
						Socket s = null;

						try {
							s = sSocket.accept();  //connects client to server
							addFeed("Client " + (al.size() + 1) + "/2 joined");

							DataInputStream in = new DataInputStream(s.getInputStream());
							DataOutputStream out = new DataOutputStream(s.getOutputStream());

							//starts a new thread to handle new clients
							ClientHandler t = new ClientHandler(s, in, out);
							t.start();
							al.add(t);

						} catch (Exception e) {
							s.close();
						}
					}
				}
				catch (IOException e) {

				}
			}

			else {
				addFeed("Invalid port");
			}

		}
		else if (evt.getSource() == btnEnd) { //if btnEnd is clicked then closes the server
			Server.closeServer();
		}

	}

	/**
	 * Creates a new server object to make the server GUI
	 */
	public static void main(String[] args) throws IOException{
		Server s = new Server();
	}

	//method to send a message to all connected clients
	public static void broadcastMsg(String msg) throws IOException {
		for (int i = 0; i < al.size(); i++) {  //loops through array list sending message to all connected clients
			ClientHandler ch = al.get(i);
			ch.out(msg);
		}
	}

	//method to send a message to client one
	public static void clientOneOut (String msg) throws IOException {
		ClientHandler ch = al.get(0);
		ch.out(msg);
	}

	//method to send a message to client two
	public static void clientTwoOut (String msg) throws IOException {
		ClientHandler ch = al.get(1);
		ch.out(msg);
	}

	//method to read infomration from client one
	public static String clientOneIn () throws IOException {
		ClientHandler ch = al.get(0);
		return ch.in();
	}

	//method to read information from client two
	public static String clientTwoIn () throws IOException {
		ClientHandler ch = al.get(1);
		return ch.in();
	}

	//method to add text to the text area located on the user interface
	public static void addFeed(String in) {
		txaFeed.setText(txaFeed.getText() + "\n" + in);
	}

	//method to close the server
	public static void closeServer() {
		addFeed("CLOSING SERVER");

		try {
			Thread.sleep(1000);
		}
		catch (Exception e){

		}

		System.exit(0);
	}

}

class ClientHandler extends Thread {
	//creates variables to handle connection to clients
	private final DataInputStream in;
	private final DataOutputStream out;
	private final Socket s;

	//class constructor to set socket, input, and output
	public ClientHandler(Socket s, DataInputStream in, DataOutputStream out) {
		this.s = s;
		this.in = in;
		this.out = out;
	}

	//method to send a message out to the current client
	public void out(String msg) throws IOException{
		out.writeUTF(msg);
	}

	//method to read a message from the current client
	public String in() throws IOException {
		return in.readUTF();
	}

	//method to run the server client connection
	public void run() {
		try {
			// Tells the clients their names
			Server.clientOneOut("Player 1");
			// will wait here for second client
			Server.clientTwoOut("Player 2");
			// lets first client know second client connected
			Server.broadcastMsg("Connected");

			//System.out.println(in.readUTF());
			// this waits for client two to be ready, when it sends confirmation
			Server.clientTwoIn();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//Server.addFeed("Game Started");
		while (true) {
			try {
				// tells clients if its their turn or shot
				Server.clientOneOut("Turn");
				Server.clientTwoOut("Wait");

				String received = Server.clientOneIn();
				// if received a shot gets shot position,
				// sends to client two, check hit, send
				// boolean client one
				if (received.equals("clientoneshot")) {
					String row = Server.clientOneIn();
					String col = Server.clientOneIn();

					Server.clientTwoOut(row);
					Server.clientTwoOut(col);

					String hit = Server.clientTwoIn();

					Server.clientOneOut(hit);

					//
					String hasLost = Server.clientTwoIn();
					Server.clientOneOut(hasLost);
				}

				else if(received.equals("Exit")) {
					this.s.close();
					System.exit(0);
					break;
				}

				// flips the turn and continues
				Server.clientOneOut("Wait");
				Server.clientTwoOut("Turn");

				received = Server.clientTwoIn();

				if (received.equals("clientoneshot")) {
					String row = Server.clientTwoIn();
					String col = Server.clientTwoIn();

					Server.clientOneOut(row);
					Server.clientOneOut(col);

					String hit = Server.clientOneIn();

					Server.clientTwoOut(hit);

					//
					String hasLost = Server.clientOneIn();
					Server.clientTwoOut(hasLost);
				}

				else if(received.equals("Exit")) {
					this.s.close();
					System.exit(0);
					break;
				}

			} catch (SocketException e) {
				//Server.addFeed("Player disconneted");
				Server.closeServer();

			} catch (IOException e) {
				//e.printStackTrace();          //CHANGE
			} catch (Exception e) {

			}
		}
		try {
			this.in.close();
			this.out.close();
		} catch (IOException e) {
			//e.printStackTrace();            //CHANGE
		}
	}
}