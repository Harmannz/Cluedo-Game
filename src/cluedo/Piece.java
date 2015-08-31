package cluedo;

/**
 * Represents the Pieces that are displayed on the board
 *
 */
public abstract class Piece {
	//name of the piece
    private String name;

    public Piece (String name){
    	this.name = name;
    }
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public String toString() {
		return name.substring(0, 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
    
}
