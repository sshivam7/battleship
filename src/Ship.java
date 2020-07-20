/**
 * 
 */

/**
 * Author Jeevan
 * Description:	Ship class. Creates a ship object that has health, length, name, position, etc
 * 
 * Method												Description
 * 
 * Ship(String n, int l)								Constructor for ship, takes name and length of ship
 * 														Initializes attributes.
 * void setPos(int row, int col, boolean isVertical)	Sets the position of the ship by a specified row and col and if 
 * 														the ship is vertical. Sets the corresponding positions occupied
 * 														as true in the 10x10 boolean 2d array pos[][]
 * boolean checkHit(int row, int col)					Returns boolean, will be true if the ship occupies that spot,
 * 														and also loses 1 health. Returns false if the ship does not occupy
 * getters and setters									Getters and setters for instance data
 * main													Self testing main method.
 *
 */
public class Ship {

	/**
	 * private instance data
	 */
	private int health, length;
	private String name;
	private boolean pos[][];
	private int r, c;
	
	//Constructor for ship, takes name and length of ship, initializes attributes.
	public Ship(String n, int l) {
		// TODO Auto-generated constructor stub
		this.name = n;
		this.length = l;
		this.health = l;
		this.pos = new boolean[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				this.pos[i][j] = false;
			}
		}
	}
	
	//Sets the position of the ship by a specified row and col and if 
	//the ship is vertical. Sets the corresponding positions occupied
	//as true in the 10x10 boolean 2d array pos[][]
	public void setPos(int row, int col, boolean isVertical) {
		this.r = row;
		this.c = col;
		
		if (isVertical) {	// if vertical, stays in column and moves down row
			for (int i = 0; i < length; i++) {
				pos[row + i][col] = true;
			}
		}
		else {				// if horizontal stays in row and moves down column
			for (int i = 0; i < length; i++) {
				pos[row][col + i] = true;
			}
		}
	}
	
	//Returns boolean, will be true if the ship occupies that spot,
	//and also loses 1 health. Returns false if the ship does not occupy
	public boolean checkHit(int row, int col) {
		if (pos[row][col]) {
			health--;
			return true;
		}
		else {
			return false;
		}
	}

	public int getHealth() {
		return health;
	}
	
	public int getLength() {
		return length;
	}

	public String getName() {
		return name;
	}
	
	public int getRow() {
		return r;
	}
	
	public int getCol() {
		return c;
	}

	/**
	 * @param args
	 * self testing main method
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Ship s = new Ship("Battleship", 4);
		s.setPos(3, 5, true);
		System.out.println(s.getHealth());
		
		System.out.println(s.checkHit(4, 5));
		
		System.out.println(s.getHealth());
		System.out.println(s.getName());
		System.out.println(s.getLength());
		System.out.println(s.getRow());
		System.out.println(s.getCol());
		

	}

}
