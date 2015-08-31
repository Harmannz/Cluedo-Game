import java.io.*;
import java.util.*;

import cluedo.*;

/**
 * Represents the interaction between the game and the user.
 * Run this class to play the game
 * @author Harman and Miten
 * @version 2015
 *
 */
public class Main {
	
	
	/**
	 * Get string from System.in
	 */
	private static String inputString(String msg) {
		System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				return input.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error ... please try again!");
			}
		}
	}

	/**
	 * Get integer from System.in
	 */
	private static int inputNumber(String msg) {
		System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				String v = input.readLine();
				return Integer.parseInt(v);
			} catch (IOException e) {
				System.out.println("Please enter a valid number!");
			}
			catch (NumberFormatException e) {
				System.out.println("Please enter a valid number!");
			}
		}
	}
			
	/**
	 * Helper method for makeSuggestion method.
	 * prints out the cards that are passes as the parameter.
	 * 
	 * @param cards
	 * @param cardType
	 * @return
	 */
	private static <T extends Card> String printCards(ArrayList<T> cards){ 
		String msg = "[";
		for(int i = 1; i < cards.size() + 1; i++){
			System.out.println(i + ". " + cards.get(i-1));
			msg += i + "/";
		}
		return msg + "]";
		
	}
	
	/**
	 * Print out hand (cards) of the player passes as the parameter
	 */
	private static void listHand(Player player, GameOfCluedo game) {
		System.out.println("Cards of " + player.getName());
		for (Card c : player) {
			System.out.println("* " + c.getName());		
		}
		System.out.println();
	}

	/**
	 * Helper method.
	 * When a player enters a room, prints out room name and any weapons in it
	 * @param player
	 */
	private static void printHelper(Player player){
    	//check if onpiece is instance of room
    	if(player.getOnPiece() instanceof Room){
    		Room currentRoom = (Room) player.getOnPiece(); //safe 
	    	System.out.println("\n********************");
			System.out.println(player.getName() + " has entered: " 
					+ currentRoom.getName());
			if(currentRoom.hasWeapon()){
				System.out.println(currentRoom.getName() + " has " + currentRoom.getWeapon());
			}else{
				System.out.println(currentRoom.getName() + " has no weapon");
			}
			System.out.println("********************\n");
    	}
    }
	

    /**
     * This method can ask players to continue to typing the direction of player movement until their moves end.
     * The option of teleportation is given if the player is on the corner rooms.
     * 
     * @param player
     * @param nsteps
     * @param game
     */
	private static void movePlayer(Player player, int nsteps, GameOfCluedo game) {
		//if player is on cornerRoom then ask if they want to teleport
		Piece piece = player.getOnPiece();
		if(piece instanceof CornerRoom){
			while(true){
				try{
					printHelper(player);
					System.out.println("Do you want to teleport?");
					String cmd = inputString("[y/n]").toLowerCase();
					if (cmd.equals("y")) {
						game.teleportPlayer(player, piece);
						game.printBoard();
						return; //once player has transported we return to player options
					} else if (cmd.equals("n")) {
						break; //break out of loop and continue with normal player movement
					} else {
						System.out.println("Invalid command.  Enter y (yes) / n (no).");
					}
					} catch(GameOfCluedo.InvalidMove e) {
						System.out.println(e.getMessage());
					}
			}
		}
		//loop displaying direction of movement until nsteps
		System.out.println("* UP (w), DOWN (s), LEFT (a), RIGHT (d)");
        for(int i = 0; i < nsteps; i++){
        	Piece onPiece = player.getOnPiece();
        	Position newPosition;
        	Position oldPosition = player.getPosition();
			while (true) {
				try{
					System.out.println(player.getName() + " has " + (nsteps - i) + " move(s) left.");
					String cmd = inputString("[w/a/s/d]").toLowerCase();
					System.out.println();
					//now update the new position of the player based on user input
					if (cmd.equals("w")) {	
						newPosition = oldPosition.moveUp();
					} else if (cmd.equals("a")) {
						newPosition = oldPosition.moveLeft();
					} else if (cmd.equals("s")) {
						newPosition = oldPosition.moveDown();
					} else if (cmd.equals("d")) {
						newPosition = oldPosition.moveRight();					
					} else {
						System.out.println("Invalid command.  Enter w/a/s/d to finish turn.");
						continue;
					}	
					
					game.movePlayer(player, newPosition);
					break;
					
				} catch(GameOfCluedo.InvalidMove e) {
					System.out.println(e.getMessage());
				}
			}
			if(!(onPiece instanceof Room) && player.getOnPiece() instanceof Room){
				//player has entered a room. so must finish their move
				break;
			}	
			game.printBoard();
        }
	}
    
	/**
	 * This method collects the information required for a player to make an Accusation
	 * @param player
	 * @param game
	 * @return boolean: true if successful accusation was made, false if unsuccessful accusation was made.
	 * @throws GameOfCluedo.InvalidMove
	 */
	private static boolean makeAccusation(Player player, GameOfCluedo game)
			throws GameOfCluedo.InvalidMove {
		
		String msg;
		try{
			System.out.println("\nPick one character. Enter the corresponding number.");
			msg = printCards(game.getCharacters()); //print character options
			System.out.println();
			int character = inputNumber(msg) - 1;
			
			System.out.println("\nPick one weapon. Enter the corresponding number.");
			msg = printCards(game.getWeapons()); //print weapons options
			System.out.println();
			int weapon = inputNumber(msg) - 1;
			
			System.out.println("\nPick one room. Enter the corresponding number.");
			msg = printCards(game.getRooms()); //print room options
			System.out.println();
			int room = inputNumber(msg) - 1;
			
			game.playerAccusation(player, character, weapon, room);
            //successful player accusation
			return true;
		} catch (GameOfCluedo.InvalidMove e){
			System.out.println(e.getMessage());
			return false;  //exit method and return to player options
		}
	}

	/**
	 * This method asks the player what suggestion they want to make
	 * @param player
	 * @param game
	 * @return boolean : true if successful suggestion was made, false if unsuccessful suggestion was made.
	 * @throws GameOfCluedo.InvalidMove
	 */
	private static boolean makeSuggestion(Player player, GameOfCluedo game)
			throws GameOfCluedo.InvalidMove {
		String msg;
		try{
			
	        System.out.println("\nPick one character. Enter the corresponding number.");
			msg = printCards(game.getCharacters()); //print character options
			System.out.println();
			int character = inputNumber(msg) - 1;
			
			System.out.println("\nPick one weapon. Enter the corresponding number.");
			msg = printCards(game.getWeapons()); //print weapons options
			System.out.println();
			int weapon = inputNumber(msg) - 1;
			System.out.println();
			List<String> suggestedCards = game.playerSuggestion(player, character, weapon);
			
			//ask other players to refute or pass the suggestion
			int i = 0;
			for(Player p : playersRefute(player, suggestedCards,game)){
				if(i==0){
				//game.printBoard();
				System.out.println("\n********************\n" 
						+ player.getName() + "\n********************");
				}
				System.out.println(p.getName() + " has refuted your suggestion.");
				
				i++;
			}
			if(i == 0){
				game.printBoard();
				System.out.println("\n********************\n" 
						+ player.getName() + "\n********************");
				System.out.println("Every player passed.\n");
			}
            
			return true;
		} catch (GameOfCluedo.InvalidMove e){
			System.out.println(e.getMessage());
			return false;  //exit method and return to player options
		}

	}
	
    /**
     * Helper method for makeSuggestion.
     * Asks each player to refute or pass the suggested cards.
     * @param player
     * @param suggestedCards
     * @param game
     * @return List of players that have refuted
     */
	private static List<Player> playersRefute(Player player, List<String> suggestedCards, GameOfCluedo game){
		ArrayList<Player> refutedPlayers = new ArrayList<Player>();
		//ask players to refute or pass
		for(Player p : game){
			//if not player ask to refute/pass
			if(p != player){
				System.out.println("\n********************");
				System.out.println(player.getName() + " has suggested that: " 
						+ suggestedCards.get(0) + " commited the crime with the "
						+ suggestedCards.get(1) + " in the "
						+ suggestedCards.get(2));
				System.out.println("********************");
				System.out.println("Options for " + p.getName()+" ("+p +") " + ":");
				while(true){
					System.out.println("* Refute (r), Pass (p), List Hand (l)");
					String cmd = inputString("[r/p/l]").toLowerCase();
					if (cmd.equals("r")) {
						refutedPlayers.add(p);
						break;
					} else if (cmd.equals("p")) {
						break;//pass
					}else if(cmd.equals("l")){
						System.out.println();
						listHand(p, game);
					}else {
						System.out.println("Invalid command.  Try again!");
					}
				}
			}
			
		}
		return refutedPlayers;	
	}
	


	/**
	 * Provide player with set of things they can do.
	 */
	private static void playerOptions(Player player, GameOfCluedo game) {
        printHelper(player);
		System.out.println("Options for " + player.getName() + ":");
		System.out.println("* Suggest, Accuse");
		System.out.println("* List Hand");
		System.out.println("* End turn");
		
		
		while (true) {
			try {
				
				String cmd = inputString("[suggest/accuse/list/end]").toLowerCase();
				if (cmd.equals("end")) {
					return;
				} else if (cmd.equals("suggest")) {
					if(makeSuggestion(player, game))
						return;
					//ends turn of player after successful suggestion
				} else if (cmd.equals("accuse")) {
					if(makeAccusation(player, game))
						return;
					 //ends turn of player after successful accusation
				}
				else if (cmd.equals("list")) {
					listHand(player, game);
				} else {
					System.out.println("Invalid command.  Enter 'end' to finish turn.");
				}
				System.out.println("\nOptions for " + player.getName());
			} catch(GameOfCluedo.InvalidMove e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Sets the number of players participating in this game.
	 * The number of players are set based on user input.
	 * 
	 * @param game
	 * @return
	 */
	private static ArrayList<Player> setPlayers(GameOfCluedo game){
		//input number of players. Must be between 3-6.
		while (true){
			int nplayers = inputNumber("How many players? (3-6)");
			if(nplayers < 3 || nplayers > 6)
				System.out.println("Invalid players entered. Must be between 3-6.");
			else
				return game.removePlayers(6 - nplayers);
		}
	}

	/**
	 * Setup and begin the game.
	 * @param args
	 */
	public static void main(String args[]) {
		GameOfCluedo game = new GameOfCluedo();

		// Print banner
		System.out.println("*** Cluedo Version 1.0 ***");
		System.out.println("By Miten and Harman, 2015");

		// setup players by removing unused players from list
		ArrayList<Player> players = setPlayers(game);
		
		// now, begin the game!
		int turn = 1;
		Random dice = new Random();
		
		
		outerloop:
		while (true) { // loop forever
			System.out.println("\n********************");
			System.out.println("***** TURN " + turn + " *******");
			System.out.println("********************");
			game.printBoard();
			boolean firstTime = true;
			Iterator<Player> iter = players.iterator();
			//use iterator since a player could be removed in the for loop
			while(iter.hasNext()) { 
				Player player = iter.next();
				if (!firstTime) {
					System.out.println("\n********************\n");
					game.printBoard();
				}
				firstTime = false;
				int roll = dice.nextInt(10) + 2;

				System.out.println(player.getName() +" ("+player +")"+ " rolls a " + roll + ".");
				//ask player to move
				movePlayer(player, roll, game);
				//now, give player options
				playerOptions(player, game);
		
				
		        if(game.hasWon()){
		        	//end game is a player has won
					System.out.println("\n********************");
					System.out.println("CONGRATULATIONS!");
					System.out.println("********************\n");
					System.out.println(player.getName() + " has won.");
					System.out.println("Solutions was: " + Arrays.asList(game.getSolution()));
					break outerloop;
		        }
				
		        if(!player.isActive()){
		        	//remove inactive players from the player array.
		        	//This prevents them from being able to play the game, but they still participate in refuting a suggestion
					System.out.println("\n********************");
					System.out.println("WRONG ACCUSATION!");
					System.out.println("********************");
					System.out.println("Game over for " + player.getName());					
					iter.remove();

		        }
			}
			turn++;
			if(players.size() == 0){
	            //quit game if all players have lost
				System.out.println("\nAll Players have lost.");
				break;
			}
		}
		System.out.println("\n********************");
		System.out.println("GAME ENDED!");
		System.out.println("********************");
		//game ended
	}
}
