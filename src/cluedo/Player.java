package cluedo;

import java.util.*;

/**
 * This class represents the player (human) that will be playing the game.
 * This class contains the properties, such as the player's hand, of each player.
 *
 */
public class Player extends Piece implements Iterable<Card>{
	//player's hand
    private ArrayList<Card> cards = new ArrayList<Card>();
    //players position
	private Position position;
	//onPiece represents the piece the player is currently on
	private Piece onPiece;
	//whether or not the player is active
	private boolean isActive = true;
    private int number;
	
	public Player(String name, Position position, Piece onPiece, int number){
		super(name);
		this.position = position;
		this.onPiece = onPiece;
		this.number = number;
	}
	/**
	 * Sets the player to being inactive.
	 */
	public void setInactive(){
		isActive = false;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	/**
	 * get the piece the player is currently sitting on
	 * @return the piece player is on
	 */
	public Piece getOnPiece() {
		return onPiece;
	}

	/**
	 * set the onPiece to be this onPiece
	 * @param onPiece
	 */
	public void setOnPiece(Piece onPiece) {
		this.onPiece = onPiece;
	}

	/**
	 * get the position of the player
	 * @return position
	 */
	public Position getPosition(){
		return position;
	}
	
	/**
	 * set the position of the player to a given position
	 * @param pos
	 */
	public void setPosition(Position pos){
		this.position = pos;
	}
	
	/**
	 * adds card
	 * @param card
	 */
	public void addCard(Card card){
		cards.add(card);
	}
	
	/**
	 * add all the cards
	 * @param cards
	 */
	public void addCards(ArrayList<Card> cards){
		cards.addAll(cards);
	}
	
	/**
	 * gets the cards
	 * @return
	 */
    public ArrayList<Card> getCards(){
    	return cards;
    }
	
    @Override
	public String toString() {
		return ""+number;
	}
	
	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

}
