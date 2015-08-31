package cluedo;

import java.util.*;

/**
 * Represents the Actual Game.
 *
 */

public class GameOfCluedo implements Iterable<Player>{
	//make board
	private Board board = new Board();
	//make players
	private ArrayList<Player> players = new ArrayList<Player>();
	//make cards
	private ArrayList<Character> characters = new ArrayList<Character>(); //should we change these to a set or something else?
	private ArrayList<RoomCard> rooms = new ArrayList<RoomCard>();
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    //make solution
	private ArrayList<Card> solution = new ArrayList<Card>();
	
	private boolean hasWon = false; //determines whether a game has been won or not
	
	
	/**
	 * Constructor: creates the players
	 * and determines the solution cards.
	 */
	public GameOfCluedo(){
		createPlayers();
		createCards();
		createSolution();
		addWeapons();
	}
	
	/**
	 * add weapons to the board
	 */
	private void addWeapons(){
		for(Weapon weapon : weapons){
			board.addWeapon(weapon);
		}
	}
	
	/**
	 * creates a random solution for the game
	 */
	private void createSolution(){
		Random rd = new Random();
		solution.add(characters.get(rd.nextInt(characters.size())));
		solution.add(weapons.get(rd.nextInt(characters.size())));
		solution.add(rooms.get(rd.nextInt(characters.size())));
		//uncomment below to see what the actual solution is.
//		System.out.println("Actual solution is: " + Arrays.asList(solution));  
	}
    
	/**
	 * Creates all the players
	 */
	private void createPlayers(){
		Wall wall = new Wall();
		players.add(new Player("Mrs. Scarlet", new Position(24,7), wall, 1));
		players.add(new Player("Col. Mustard", new Position(17,0), wall, 2));
		players.add(new Player("Mrs. White", new Position(0,9), wall, 3));
		players.add(new Player("Rev. Green", new Position(0,14), wall, 4));
		players.add(new Player("Mrs. Peacock", new Position(6,24), wall, 5));
		players.add(new Player("Prof. Plum", new Position(19,24), wall, 6));
		addPlayersToBoard();
	}
	
	/**
	 * creates all the cards (characters, weapons, rooms)
	 */
	private void createCards(){
		characters.add(new Character("Mrs. Scarlet"));
		characters.add(new Character("Col. Mustard"));
		characters.add(new Character("Mrs. White"));
		characters.add(new Character("Rev. Green"));
		characters.add(new Character("Mrs. Peacock"));
		characters.add(new Character("Prof. Plum"));
		weapons.add(new Weapon("CandleStick"));
		weapons.add(new Weapon("Dagger"));
		weapons.add(new Weapon("Lead Pipe"));
		weapons.add(new Weapon("Revolver"));
		weapons.add(new Weapon("Rope"));
		weapons.add(new Weapon("Spanner"));
		rooms.add(new RoomCard("Ball Room"));
		rooms.add(new RoomCard("Conservatory"));
		rooms.add(new RoomCard("Billiard Room"));
		rooms.add(new RoomCard("Library"));
		rooms.add(new RoomCard("Study"));
		rooms.add(new RoomCard("Hall"));
		rooms.add(new RoomCard("Lounge"));
		rooms.add(new RoomCard("Dining Room"));
		rooms.add(new RoomCard("Kitchen"));
	}
	
	/**
	 * add players to the board
	 */
	private void addPlayersToBoard(){
		for(Player p: players){
			board.addPlayer(p);
		}
	}

	
	/**
	 * checks if the game has been won by a player
	 * @return boolean
	 */
	public boolean hasWon(){
		return hasWon;
	}
	
	/**
	 * gets the solution to the murder crime
	 * @return solution
	 */
	public ArrayList<Card> getSolution(){
		return solution;
	}
	

	/**
	 * get the characters
	 * @return characters
	 */
	public ArrayList<Character> getCharacters() {
		return characters;
	}
    
	/**
	 * get the weapons
	 * @return weapons
	 */
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	
	/**
	 * get the rooms
	 * @return room
	 */
	public ArrayList<RoomCard> getRooms(){
		return rooms;
	}
	
	
	/**
	 * print the board
	 */
	public void printBoard(){
		board.printAll();
	}
	
	/**
	 * moves a player to a valid space location on the board
	 * @param player
	 * @param newPosition
	 * @throws InvalidMove
	 */
	public void movePlayer(Player player, Position newPosition) throws InvalidMove{
		//check new position value
		if(0 > newPosition.getxPos() || board.getSize() <= newPosition.getxPos()
				||0 > newPosition.getyPos() || board.getSize() <= newPosition.getyPos())
			throw new InvalidMove("Position is out of bounds");
		//cannot move to a wall
		if(board.getPiece(newPosition) instanceof Wall)
			throw new InvalidMove(player.getName() + " cannot enter wall");
		//cannot move onto a player
		if(board.getPiece(newPosition) instanceof Player){
			throw new InvalidMove(player.getName() + " cannot move onto another piece!");
		}
		
		board.movePlayer(player, newPosition);
	}

	
	
	
	/**
     * Removes unused players so the number of players selected by the human player corresponds to the number of players
     * in the players array
     * Assign cards to the players in the game
     * @param nplayers
     * @return
     */
	public ArrayList<Player> removePlayers(int nplayers) {
		for(int i = 0; i < nplayers; i++){
			board.removePlayer(players.get(players.size() - 1));
			players.remove(players.size() - 1);
		}
		assignCards(); //assigns the cards to the remaining players. giving each player a hand.
		return new ArrayList<Player>(players); //return copy??
	}
	
	/**
	 * Helper method for removePlayers
	 * Assign cards to the players that are actually playing
	 */
	private void assignCards(){
		ArrayList<Card> cards = new ArrayList<Card>(); //collects all cards for distribution
		cards.addAll(characters);
		cards.addAll(rooms);
		cards.addAll(weapons);
		Collections.shuffle(cards); //shuffle cards
        
		int i = 0;
		while(!cards.isEmpty()){
			Card c = cards.remove(0);
			if(!solution.contains(c)){ //do not hand out the solution card
				players.get(i % players.size()).addCard(c);
				i++;
			}
		}
	}
	
	/**
	 * Creates a list representing the current players suggestion
	 * Moves players and weapons that are suggested to player's current room.
	 * @param player
	 * @param character
	 * @param weapon
	 * @return List containing players suggestion
	 * @throws InvalidMove
	 */
	public List<String> playerSuggestion(Player player, int character, int weapon) throws InvalidMove {
		//check if player's onPiece is room and if the character and weapon indexes are valid
		if(!(player.getOnPiece() instanceof Room))
			throw new InvalidMove(player.getName() + " must be inside a room to make a suggestion");
		
		if(0 > character || character >= characters.size())
			throw new InvalidMove("Wrong character selection");
		if(0 > weapon || weapon >= weapons.size())
			throw new InvalidMove("Wrong weapon selection");
		
		Room playerRoom = (Room) player.getOnPiece(); //safe
		Weapon chosenWeapon = weapons.get(weapon);
		
		if(playerRoom.hasWeapon() && !playerRoom.getWeapon().equals(chosenWeapon)){
			throw new InvalidMove(playerRoom.getName() + " already has a " + playerRoom.getWeapon() 
					+ ".\nCannot add " + chosenWeapon);
		}
		
		Character chosenCharacter = characters.get(character);
		//move weapon and player to room
		board.moveWeapon(chosenWeapon, playerRoom);
		for(Player p : players){
			if(p.isActive() && (chosenCharacter.getName()).equals(p.getName()) && !p.equals(player)){
				board.movePlayer(p, playerRoom); //move chosen player to suggested location, provided they are not there already 
				break;
			}
		}
		//for printing
		List<String> suggestionCards = new ArrayList<String>();
		suggestionCards.add(chosenCharacter.getName());
		suggestionCards.add(chosenWeapon.getName());
		suggestionCards.add(player.getOnPiece().getName());
		return suggestionCards;
	}
	
	/**
	 * Checks to see if the accusation made by the player matches the solution to the murder.
	 * Game ends if the player is correct, otherwise the player is removed from the game so they can no longer
	 * take a turn but they can still refute or pass when another player makes a suggestion
	 * @param player
	 * @param character
	 * @param weapon
	 * @param room
	 * @throws InvalidMove
	 */
	public void playerAccusation(Player player, int character, int weapon, int room) throws InvalidMove{
		//if card choices are incorrect then throw error 
		//if player is wrong set the player to inactive
		//if player is right then end game
		if(0 > character || character >= characters.size())
			throw new InvalidMove("Wrong character selection");
		if(0 > weapon || weapon >= weapons.size())
			throw new InvalidMove("Wrong weapon selection");
		if(0 > room || room >= rooms.size())
			throw new InvalidMove("Wrong room selection");
	
		if(solution.contains(characters.get(character))){
			if(solution.contains(weapons.get(weapon))){
				if(solution.contains(rooms.get(room))){
					this.hasWon = true; //game has ended
				}
			}
		}
		player.setInactive(); 
		//player has made wrong selection so we set them to inactive. But they can still refute if wrong accusation is made. 
		board.removePlayer(player); //remove player from board	
	}
	
	/**
	 * Teleports a player in a CornerRoom to the opposite CornerRoom
	 * @param player
	 * @throws InvalidMove
	 */
    public void teleportPlayer(Player player, Piece piece) throws InvalidMove{
    	//check if player onpiece is instance of cornerroom
    	//if it is then find empty location in corner room
    	if(!(piece instanceof CornerRoom)){
    		throw new InvalidMove(player.getName() + " must be in a corner room to teleport.");
    	}
  
    	CornerRoom cornerRoom = ((CornerRoom) piece).getCornerRoom(); //safe  	
    	Position newPosition = board.getEmptySpace(cornerRoom);
    	if(newPosition == null){
    		throw new InvalidMove("No empty space found in " + cornerRoom.getName());
    	}
    	movePlayer(player, newPosition);
    	
    }

	@Override
	public Iterator<Player> iterator() {
		return players.iterator();
	}


	/**
	 * Indicates an attempt to make an invalid move.
	 *
	 */
	public static class InvalidMove extends Exception {
		public InvalidMove(String msg) {
			super(msg);
		}
	}
}