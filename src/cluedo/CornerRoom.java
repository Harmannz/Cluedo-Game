package cluedo;

/**
 * This represents the Room on the corner of the board
 *
 */
public class CornerRoom extends Room{
	
    private CornerRoom room;
    
	public CornerRoom(String name) {
		super(name);
	}
	
	/**
	 * Sets the room that is connected to this corner room
	 * @param CornerRoom
	 */
	public void setCornerRoom(CornerRoom c){
		this.room = c;
	}
	
	/**
	 * Returns the room that is connected to this CornerRoom
	 * @return
	 */
	public CornerRoom getCornerRoom (){
		return room;
	}

}
