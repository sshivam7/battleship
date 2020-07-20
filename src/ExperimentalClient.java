import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/*
 * Author: Shivam Sood
 * Date: January 16th 2020
 *
 * Descriptions: Class to create client in order to connect and communicate with the server
 *
 * Method						                     Description
 * 
 * public ExperimentalClient(int port) - class constructor to set up client communication
 * public String clientIn()			   - method to read from server
 * public void clientOut(String msg)   - method to write to server
 * public void endClient()			   - method to terminate the client
 * public static void main(String[] args) - method creates a client object to test class
 */

public class ExperimentalClient  {
	
	// private instance data
	private InetAddress ip;
	private Socket s;
	private DataInputStream in;
	private DataOutputStream out;
	
	//default class constructor to set up client
	public ExperimentalClient(int port) {
		
		// setting up the client stuff for server interaction
		try {
			ip = InetAddress.getLocalHost();//getByName("localhost"); //sets ip
			//System.out.println(ip);

			s = new Socket(ip, port); //creates a new socket on the given port

			in = new DataInputStream(s.getInputStream());        //creates new input and output streams
			out = new DataOutputStream(s.getOutputStream());
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	//method to read a message from server
	public String clientIn() throws IOException {
		String i = in.readUTF();
		//System.out.println(i);
		return i;
	}

	//method to send a message to server
	public void clientOut(String msg) throws IOException {
		out.writeUTF(msg);
	}

	//terminates the client
	public void endClient() throws IOException {
		s.close();
		in.close();
		out.close();
	}

	//calls the class constructor to create a client connection (tests the class)
	public static void main(String[] args) throws IOException{
		ExperimentalClient c = new ExperimentalClient(4444); //uses 4444 as a default port

	}
}