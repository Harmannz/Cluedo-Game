package cluedo;

/**
 * Represents the abstract card used in the game. 
 *
 */
public abstract class Card {
	private String name;
	
	/**
	 * get the name of this card
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	public Card(String name){
		this.name = name;
	}
    public String toString(){
    	return name;
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
		if (obj == null || getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}
    
    
}