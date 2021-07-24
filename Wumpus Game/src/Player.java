public class Player {
	// constants
	public static final int NUM_GRENADES_PER_WUMPUS = 3;
	
	// data fields
	private int numGrenades;
	private boolean isAlive;
	
	/*
	 * Constructor: creates a Player with NUM_GRENADES_PER_WUMPUS for 
	 * each wumpus and is alive.
	 * 
	 * @param numWumpi - the number of wumpi
	 */
	public Player(int numWumpi) {
		this.numGrenades = NUM_GRENADES_PER_WUMPUS*numWumpi;
		this.isAlive = true;
	}
	
	/*
	 * this method indicates whether the Player is alive or not
	 * 
	 * @return boolean - true if the Player is alive; otherwise, false.
	 */
	public boolean alive() {
		return this.isAlive;
	}
	
	/*
	 * this method sets the Player's alive status to false
	 * 
	 */	
	public void die() {
		this.isAlive = false;
	}
	
	/*
	 * this method returns the player's current number of grenades
	 * 
	 * @return int
	 */
	public int getNumGrenades() {
		return this.numGrenades;
	}
	
	/*
	 * this method throws grenades
	 * if there is a grenade to throw, 
	 * it reduces the number of grenades and returns true.
	 * otherwise, false
	 * 
	 * @return int
	 */
	public boolean throwGrenade() {
		if (!isAlive) {
			return false;
		}
		if (numGrenades <= 0) {
			return false;
		}
		this.numGrenades -= 1;
		return true;
	}
	
	/*
	 * this method returns a String representation of a player 
	 * in the format alive: XXXX, num grenades: XX
	 * 
	 * @return String
	 */
	public String toString() {
		return "alive: " + isAlive + ", num grenades: " + numGrenades;
	}
}
