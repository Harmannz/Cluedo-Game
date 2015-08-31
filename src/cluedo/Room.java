package cluedo;

/**
 * Represents the abstract notion of a room
 *
 */
public abstract class Room extends Piece{
	//using boolean removes chances of null dereference when checking if room has weapon
	private boolean hasWeapon; 
	private Weapon weapon;
	
	public Room(String name){
		super(name);
	}

	/**
	 * Remove the weapon in this room
	 * @return weapon
	 */
	public void removeWeapon(){
		this.weapon = null;
		this.hasWeapon = false;
	}
	
	/**
	 * get the weapon that is in this room
	 * @return weapon
	 */
	public Weapon getWeapon(){
		return this.weapon;
	}
	
	/**
	 * checks to see if there is a weapon in this room
	 * @return boolean
	 */
	public boolean hasWeapon(){
		return this.hasWeapon;
	}
	
	/**
	 * place a weapon in this room
	 * @param weapon
	 */
	public void setWeapon(Weapon weapon){
		this.hasWeapon = true;
		this.weapon = weapon;
	}
	
}
