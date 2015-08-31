package cluedo;

/**
 * This class represents the position of the player on the board
 *
 */
public class Position {
    private int xPos;
    private int yPos;
    
    public Position(int xPos, int yPos){
    	this.xPos = xPos;
    	this.yPos = yPos;
    }

    /**
     * get x position
     * @return x position
     */
	public int getxPos() {
		return xPos;
	}

	/**
	 * get y position
	 * @return y position
	 */
	public int getyPos() {
		return yPos;
	}

	/**
	 * set the x position to be a given x position
	 * @param xPos
	 */
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * set the y position to be a given y position
	 * @param yPos
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	/**
	 * moves the player up one space
	 * @return
	 */
	public Position moveUp(){
		//returns new instances so that it doesn't mess up a player's original position field
		return new Position(xPos-1, yPos);
	}
	
	/**
	 * moves the player down one space
	 * @return
	 */
	public Position moveDown(){
		return new Position(xPos+1, yPos);
	}
	
	/**
	 * moves the player left one space
	 * @return
	 */
	public Position moveLeft(){
		return new Position(xPos, yPos-1);
	}
	
	/**
	 * moves the player right one space
	 * @return
	 */
	public Position moveRight(){
		return new Position(xPos, yPos+1);
	}
	

}
