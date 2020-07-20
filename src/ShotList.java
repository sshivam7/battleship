import javax.swing.JOptionPane;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Luvish
 * Description: Transaction record aims to create a way to access recent transactions
 * according to checking or savings account
 *
 *						-----Methods and Objects List -----
 * 	Object: TransactionList
 * 		Creates a list of transactionRecords by creating a list with a specified maxSize
 *
 * 	Method: Insert
 * 		Allows for the insertion of a transactionRecord
 *
 * 	Method: delete
 * 		Allows for a record to be deleted from the TransactionList
 *
 * 	Method: bubbleSort;
 *		Sorts the transactionList by the date of the transactions
 *
 *	Method: binarySearch:
 *		Search the list for a certain transaction by date
 *
 *	Method: sortUp:
 *		Sorts the transactions by the transaction amount, this is in descending order
 *
 *	Method: sortDown:
 *		Sorts the transactions by the transaction amount, this is in ascending order
 *
 *	Method: getSize:
 *		Getter for the size of the array
 *
 *	Method: getRecord:
 *		Getter for a record at a specified location.
 *
 *	Method: toString
 *		Takes the list and inputs it into a string format, then uses it to display info to user
 *
 *	Method: Self-Testing Main Method:
 *		Allows for the testing of the methods
 */





public class ShotList  {

	private ShotRecord[] list;
	private int maxSize;
	private int size;
	private SimpleDateFormat format;

	/*
	Object: TransactionList
		The transactionList object that utilizes a set max size, an array of transactionRecords and an initial size
	 */
	public ShotList() {
		this.maxSize = 200;
		list = new ShotRecord[maxSize];
		size = 0;
		format = new SimpleDateFormat("HH:mm:ss");
	}

	/*
    Method: Insert
        The insert method allows for a transactionRecord to be inserted into the list
	 */
	public boolean insert(ShotRecord record) {

		// if size is below maxSize
		if (size < maxSize) {
			size++;  // increase the current size
			list[size-1] = record;  // add record to the location just before
			return true;
		}
		return false;
	}

	/*
   Method Delete:
           Used to delete a record from transaction list. There are many input variants to be as specific as possible
           as bank transactions can be close to identical.
	 */


	/*
   Method: Bubble Sort
           This method sorts the list by the dates that the transactions occurred
	 */
	public void bubbleSort() throws ParseException {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - 1; j++) {
				Date date1 = new SimpleDateFormat("HH:mm:ss").parse(list[j].getsDate()); //date1 variable equals the records string that is converted to date. Parse is used to analyze the string and convert it to date.
				Date date2 = new SimpleDateFormat(" HH:mm:ss").parse(list[j+1].getsDate()); //date 2 variable equals the next record string that is converted to date.
				if (date2.before(date1)) { //if date2 is before date 1
					ShotRecord temp;
					temp = list[j]; //move the 1st record into temp
					list[j] = list[j + 1];  //make the record switch with the next record
					list[j+1] = temp; //move the 2nd record into temp
				}
			}
		}
	}

	public void insertionSortUp() throws ParseException{
		for (int i = 1; i < size; i++) {
			ShotRecord temp = list[i]; //sets a temp record
			int k = i; //delcares and intiazlies k into i

			Date date1 = new SimpleDateFormat("HH:mm:ss").parse(temp.getsDate());
			Date date2 = new SimpleDateFormat("HH:mm:ss").parse(list[k-1].getsDate());

			while (k > 0 &&  format.parse(temp.getsDate()).before(format.parse(list[k-1].getsDate()))) { //if k is more than 0 and the temp record has a bigger trans amount then the last trans amount
				list[k] = list[k-1]; //swap records
				k--;
			}

			list[k] = temp; //put record into temp

		}
	}

	public void insertionSortDown() throws ParseException{
		for (int i = 1; i < size; i++) {
			ShotRecord temp = list[i]; //sets a temp record
			int k = i; //delcares and intiazlies k into i

			Date date1 = new SimpleDateFormat("HH:mm:ss").parse(temp.getsDate());
			Date date2 = new SimpleDateFormat("HH:mm:ss").parse(list[k-1].getsDate());

			while (k > 0 &&  format.parse(list[k-1].getsDate()).before(format.parse(temp.getsDate()))) { //if k is more than 0 and the temp record has a bigger trans amount then the last trans amount
				list[k] = list[k-1]; //swap records
				k--;
			}

			list[k] = temp; //put record into temp

		}
	}


	/*
	Method: Binary Search
		Allows for the searching of a record based on date. Binary search is renowned for being an efficient algorithm
		as it divides the list in half that could contain the item until it is narrowed down to only one possible location.
	 */

	public int binarySearch (String searchKey) throws ParseException {
		int low = 0; //the first value of the array
		int high = size -1; //the highest value of the array
		int middle;



		while (low <= high) { //while the low variable is less than the high variable
			middle = (high + low) / 2; //the middle of the low and high variable
			Date date1 = new SimpleDateFormat("HH:mm:ss").parse(searchKey); //Parse the searchKey to find date1
			Date date2 = new SimpleDateFormat("HH:mm:ss").parse(list[middle].getsDate()); //Parse the middle record to find date2

			if (date1.equals(date2)) { //if dates are equal
				return middle;
			}

			else if (date1.before(date2) == true) { //if date1 comes before date2
				high = middle -1;
			}

			else {
				low = middle + 1;
			}

		}
		return -1;
	}

	/*
	Method sortDown:
		The sortDown method sorts the records into descending order based on the transaction amount.
		It utilities the bubble sort algorithm.
	 */
	/*
    public void sortDown() {
        for (int top = 1; top < list.length; top++) {
            double item = list[top].getTransAmount();
            int i = top;
            while (i > 0 && item < list[i -1].getTransAmount()) {
                list[i] = list[i -1];
                i--;
            }
            list[i].setTransAmount(item);
        }
    }
	 */

	/*
	Method sortUp:
    	The sortUp method sorts the records into ascending order based on the transaction amount.
    	It utilities the bubble sort algorithm.
	 */
	/**
     public void sortUp() {
     for (int i = 0; i < size; i++) {
     for (int j = 0; j < size - 1; j++) {
     if (list[j].getTransAmount() < (list[j+1].getTransAmount()) ) { //if the first record is smaller then the next record
     TransactionRecord temp; //take the first record and store it into temp
     temp = list[j]; //take the first record and store it into temp
     list[j] = list[j + 1]; //take the first record and make it equal to the next record
     list[j + 1] = temp; //store the second record into the temp variable
     }
     }
     }
     }
	 */
	/**
    public void sortUp(TransactionRecord[] list, int aMin, int aMax) {
        int rightPos, leftPos;
        double pivot;

        if (aMin > aMax) { //nothin more to sort
            return;
        }

        pivot = list[aMin].getTransAmount();
        leftPos = aMin;
        rightPos = aMax;

        do {
            while (pivot > list[leftPos].getTransAmount()) {
                leftPos++;
            }
            while (pivot < list[rightPos].getTransAmount()) {
                rightPos--;
            }
            if (leftPos <= rightPos) {
                if (list[leftPos].getTransAmount() != list[rightPos].getTransAmount()) {
                    swap (list, leftPos, rightPos);
                }
                leftPos++;
                rightPos--;
            }
        }
        while (leftPos < rightPos);

        sortUp (list, aMin, rightPos);
        sortUp (list, leftPos, aMax);
    }
	 */
	/**
    public void swap (ShotRecord array[], int first, int second) {
        double hold;

        hold = array[first].getX();
        array[first] = array[second];
        array[second].getX(hold);

    }
	 */
	/*
    Method getSize:
        Returns the size of the list

	 */
	public int getSize() {
		return this.size;
	}

	/*
    Method getRecord:
        Returns the record according to the list
	 */
	public ShotRecord getRecord(int i) {
		return list[i];
	}

	/*
FIX
	 */

	public String toString() {
		//finish to String
		String out = "";
		for (int i = 0; i < size; i++) {
			out += list[i].toString() + "\n";
		}
		return out;
	}

	/*
    Method Self-Testing main method
        Allows for the testing of methods listed in the class
	 */
	public static void main(String[] args) throws ParseException {
		ShotList list = new ShotList(); //creates new transaction list

		//inifnite loop
		while (true) {
			//enter input
			String input = JOptionPane.showInputDialog (null, "i - insert \n q - quit \n s - sort \n se - search \n bs - bubble sort");

			switch (input) {

			case "i": {
				//prompt for record
				String record = JOptionPane.showInputDialog(null, "Enter dd-MM-yyyy HH:mm/accountType/transType/transAmount/balance/endBalance", "20-10-2002 11:11/c/withdraw/500/5000/4500");
				//create record object
				ShotRecord tInfo = new ShotRecord();
				tInfo.processRecord(record);
				if (!list.insert(tInfo)) {
					JOptionPane.showMessageDialog(null, "adding failed");
				}
				break;
			}

			case "bs": {
				//bubble Sort
				list.bubbleSort();
				break;
			}

			case "se": {
				//bubble sort
				list.bubbleSort();

				//prompt for record to search
				String name = JOptionPane.showInputDialog(null, "Enter HH:mm:ss");

				//if the int is less than 0
				if (list.binarySearch(name) < 0) {
					JOptionPane.showMessageDialog(null, "Not Found");
				}

				else {
					JOptionPane.showMessageDialog(null, "Found");
				}
				break;
			}

			case "p": {
				//print the list
				System.out.println(list.toString());
			}


			}
		}
	}
} //TransactionList