import javax.swing.JFrame;

/**
 * Author Jeevan Opel
 * Description:	CPU / AI for the player to play against. The CPU has a PBoard to place its ships on
 * 				The CPU does not need a Board because the Board is only used for visual representation
 * 
 * Method										Description
 * 
 * CPU()										Constructor
 * void placeShips()							Places the ships on its PBoard randomly
 * int[] shoot()								Randomly selects a row and column to fire, returning array
 * 												with both. Ensures the cell was not previously shot
 * boolean checkHit(int row, int col)			Checks if a shot from the player struck one of the CPU's ships
 * boolean checkLost()							Checks if the CPU lost, returning true if it did
 * main											Self testing main
 *
 */
public class CPU {

	/**
	 * private instance data
	 */
	private PBoard pb;
	private boolean hasShot[][];	// 2d array of positions the CPU already shot
									// so it doesn't shoot there again
	
	// constructor
	public CPU() {
		// TODO Auto-generated constructor stub
		pb = new PBoard();
		hasShot = new boolean[10][10];
		
		placeShips();
	}
	
	// Places ships on the PBoard (pb) randomly
	public void placeShips() {
		while (true) {
			int row = (int)(Math.random()*10);
			int col = (int)(Math.random()*10);
			boolean isVertical = false;
			
			if (Math.random()*10 > 4) {
				isVertical = true;
			}
			
			pb.setIsVertical(isVertical);
			pb.placeShip(row, col);
			
			// because the CPU may try to place a ship in a position that it does not fit,
			// it just keeps placing until all 5 place successfully
			if (pb.getAllShipsPlaced()) {
				break;
			}
		}
		
	}
	
	// returns random row 0-9 and col 0-9 in an array to shoot on
	// ensures the randomly generated spot was not previously shot on
	public int[] shoot() {
		int row = 0;
		int col = 0;
		while (true) {
			row = (int)(Math.random()*10);
			col = (int)(Math.random()*10);
			
			if (!hasShot[row][col]) {
				break;
			}
		}
		return new int[] {row, col};
	}
	
	// checks if a ship was hit by a shot, returning true if it was
	public boolean checkHit(int row, int col) {
		return pb.checkEnemyShot(row, col);
	}
	
	// checks if the CPU has lost
	public boolean checkLost() {
		return pb.hasLost();
	}
	

	/**
	 * @param args
	 * self testing main
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CPU c = new CPU();
		
		int shot[] = c.shoot();
		System.out.println(shot[0] + " " + shot[1]);
		System.out.println(c.checkHit(7, 8));
		System.out.println(c.checkLost());
	
		
	}

}
