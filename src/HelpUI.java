import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Author: Shivam Sood
 * Date: January 15th 2020
 * Description: Class creates a new JFrame that contains help info for the battle ship game.
 * The information is loaded form a file.
 *
 *                                       Method List:
 * - public HelpUI() - class constructor to create the help menu GUI menu
 * - public void actionPerformed(ActionEvent evt) - listens for buttons being clicked and
 *    performs actions accordingly
 * - public static int sizeOfFile(String fileName) - method to determine the size of a file in
 *   terms of the number of lines
 * - public static void main(String[] args) - calls class constructor to create help menu GUI
 */
public class HelpUI extends JFrame implements ActionListener{
    //private instance data
    private JButton btnClose;           //variables for components on the GUI
    private JTextArea txaHelpInfo;
    private JLabel lblStudio;

    private String helpText = "";       //variable to hold data read from a file

    //class constructor to create the help gui menu
    public HelpUI() throws IOException {
        super ("Battle Ship Help");   //creates a window and gives it a tile

        getContentPane().setBackground(Color.BLACK);    //sets background colour

        //places the close button in the bottom center of the page
        btnClose = new JButton("Close");
        btnClose.setBounds(250, 650, 75, 25);
        btnClose.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));     //edits format for close button
        btnClose.setForeground(Color.BLACK);
        btnClose.setBackground(Color.GREEN);
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.addActionListener(this);
        add(btnClose);

        //opens up a file to read from
        FileReader fr = new FileReader("src/help.txt");
        BufferedReader input = new BufferedReader(fr);

        //loops through the different lines of the file reading and storing the info within the helpText string
        for (int i = 0; i < sizeOfFile("src/help.txt"); i++) {
            helpText = helpText + input.readLine() + "\n";
        }

        input.close();    //closes the fiel

        //places the text area in the center of the help menu
        txaHelpInfo = new JTextArea(helpText);
        txaHelpInfo.setBounds(25, 100, 550, 600);
        txaHelpInfo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        txaHelpInfo.setLineWrap(true);
        txaHelpInfo.setWrapStyleWord(true);
        txaHelpInfo.setForeground(Color.GREEN);
        txaHelpInfo.setBackground(Color.BLACK);
        txaHelpInfo.setEditable(false);
        this.add(txaHelpInfo);

        //places the help menu title in the top center of the window
        lblStudio = new JLabel("Help");
        lblStudio.setBounds(250, 20, 200, 50);
        lblStudio.setForeground(Color.GREEN);
        lblStudio.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        this.add(lblStudio);

        //sets preferences for the frame
        this.setLayout(null);
		this.setLocation(200,0);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,750);

        //set Window to be visible and so that it can't be resized
        this.setVisible(true);
        this.setResizable(false);
    }

    /**
     * Action listener button to act when buttons are clicked
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnClose) {  //disposes of the frame when the close button is clicked
            this.dispose();
        }
    }

    /**
     * Method to determine the length of the file
     * @param fileName is the name of the file to be read
     * @return an the size of the file as an integer
     */
    public static int sizeOfFile(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);         //opens file to be read from
        BufferedReader input = new BufferedReader(fr);

        int fileSize = 0;    //creates variable to count number of lines

        while (!input.readLine().equalsIgnoreCase("EOF")) {  //while file does not read EOF adds one to file size
            fileSize++;
        }

        input.close();   //closes file
        return fileSize;  //returns value for file size
    }

    /**
     * main method runs the constructor to create the GUI
     */
    public static void main(String[] args) throws IOException {
        HelpUI help = new HelpUI();  //creates HelpUI object
    }

}