import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;




public class ShotRecord {

    /*
     * Declaring Private variables
     */
    private int row;
    private int col;
    private boolean hitOrMiss;
    private String userID;

    private Date date;
    private String sDate;
    private SimpleDateFormat format;



    /*
        Object: TransactionRecord
            The TranactionRecord object creates a record of someones transaction. it takes in info such as what account
            the transaction occurred on, what was the type of transaction, the initial amount of the transaction,
            the balance before transaction, balance after transaction, and the date/time the transaction occurred at.

            DO WE NEED THIS ASK JEEVAN

     */
    public ShotRecord() {

        row = 0;
        col = 0;
        hitOrMiss = false;
        userID = "Player 1";


        format = new SimpleDateFormat("HH:mm:ss");
        date = new Date();
        sDate = format.format(date);
    }

    /*
    Object: ShotRecord (Overloaded):
        The overloaded TransactionRecord object allows for the input parameters to create a new transaction
        record. Account type can be either a 'c' or a 's' standing for chequing or savings respectively. The
        transaction type can be "Deposit" or "Withdrawal". The date is taken from the system and therefore, does
        not need to be inputted.
     */
    public ShotRecord(int r, int c, boolean hOrm, String id) {
        row = r;
        col = c;
        hitOrMiss = hOrm;
        userID = id;

        format = new SimpleDateFormat("HH:mm:ss");
        date = new Date();
        sDate = format.format(date);
    }



    /*
        Method: getsDate()
            Getter for the string of the date for when the transaction occurred
     */
    public String getsDate() {
        return sDate;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean getHitOrMiss() {
        return hitOrMiss;
    }

    public void setRow(int r) {
        this.row = r;
    }

    public void setY(int c) {
        this.col = c;
    }

    public void setHitOrMiss(boolean hitOrMiss) {
        this.hitOrMiss = hitOrMiss;
    }

    /*
                Method: processRecord(String record)
                     Takes in the string of a record, and splits it. Thus, initializing the objects individual variables
                     according to the string provided.
                 */
    public void processRecord (String record) {
        //splits the record and stores it into an array of strings
        String[] words;
        words = record.split("/");

        //the date is set to the first of the array. The account type to the second word of the array and so on.
        this.sDate = words[0];
        this.row = Integer.parseInt(words[1]);
        this.col = Integer.parseInt(words[2]);
        this.hitOrMiss = Boolean.parseBoolean(words[3]);
    }




    /*
    Method: toString
         Takes a transaction record and creates a single string
     */
    public String toString() {
    	String loc = "";
    	loc = Character.toString((char)(row + 65));
    	loc += (col +1);
    	
    	String hOm = "";
    	if (hitOrMiss) {
    		hOm = "Hit";
    	}
    	else {
    		hOm = "Miss";
    	}
    	
    	
        return "[" + sDate + "] " + userID + " fired on " + loc + ", " + hOm;
    }


    /*
    Method; Self-Testing main method
        The main method allows for the methods above to be tested
    */
    public static void main(String[] args) {

        //Creates a new Transaction record
        ShotRecord tran = new ShotRecord();

        //A string of the record
        String record = "10:20:45/10/5/Hit";

        //Takes the string above, named record, and turns it into variables for the object
        tran.processRecord(record);


        System.out.println(tran.toString());

        //Testing getters, each prints out the individual variables of the object:
        System.out.println(tran.getsDate());
        System.out.println(tran.getRow());
        System.out.println(tran.getCol());
        System.out.println(tran.getHitOrMiss());

    }
} //ShotRecord
