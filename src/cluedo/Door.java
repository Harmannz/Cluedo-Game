package cluedo;

/**
 * Represents the door.
 *
 */
public class Door extends Piece{

	Room room; //this is the room that the door leads to
	public Door(Room room){
		super("Door");
		this.room = room;
	}	
	
	public Door(){
		super("Door");
	}	
	
	@Override
	public String toString() {
		return "d";
	}
}
