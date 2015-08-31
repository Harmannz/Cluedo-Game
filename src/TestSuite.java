
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import cluedo.*;

public class TestSuite {
	
	
/*	----------------------
 *  Move Player Tests: 
 *  ----------------------
 */
		
		/**
	     * Test unsuccessful move 
	     */
		@Test
		public void testMovePlayer_1() {
			try { 
				GameOfCluedo game = new GameOfCluedo();
				Position newPosition = new Position(-1, 0); //xPos is out of bounds
				game.movePlayer(new Player("H", new Position(23,5), null, 1), newPosition);
				fail();
			} catch (GameOfCluedo.InvalidMove e) {
				//pass since newPosition is out of bounds
			}
		}
		
		/**
	     * Test unsuccessful move 
	     */
		@Test
		public void testMovePlayer_2() {
			try { 
				GameOfCluedo game = new GameOfCluedo();
				Position newPosition = new Position(25, 0); //xPos is out of bounds
				game.movePlayer(new Player("H", new Position(23,5), null, 1), newPosition);
				fail();
			} catch (GameOfCluedo.InvalidMove e) {
				//pass since newPosition is out of bounds
			}
		}

		/**
	     * Test unsuccessful move 
	     */
		@Test
		public void testMovePlayer_3() {
			try { 
				GameOfCluedo game = new GameOfCluedo();
				Position newPosition = new Position(0, -1); //yPos is out of bounds
				game.movePlayer(new Player("H", new Position(23,5), null, 1), newPosition);
				fail();
			} catch (GameOfCluedo.InvalidMove e) {
				//pass since newPosition is out of bounds
			}
		}
		
		/**
	     * Test unsuccessful move 
	     */
		@Test
		public void testMovePlayer_4() {
			try { 
				GameOfCluedo game = new GameOfCluedo();
				Position newPosition = new Position(2, 26); //yPos is out of bounds
				game.movePlayer(new Player("H", new Position(23,5), null, 1), newPosition);
				fail();
			} catch (GameOfCluedo.InvalidMove e) {
				//pass since newPosition is out of bounds
			}
		}

		/**
	     * Test unsuccessful move into a wall
	     */
		@Test
		public void testMovePlayer_5() {
			try { 
				GameOfCluedo game = new GameOfCluedo();
				Position newPosition = new Position(0, 0); //0,0 is wall on board
				game.movePlayer(new Player("H", new Position(23,5), null, 1), newPosition);
				fail();
			} catch (GameOfCluedo.InvalidMove e) {
				//pass since newPosition is position of wall
			}
		}
		
		/**
	     * Test unsuccessful move into another player
	     */
		@Test
		public void testMovePlayer_6() {
			try { 
				GameOfCluedo game = new GameOfCluedo();
				Position newPosition = new Position(24,7); //24,7 is Mrs. Scarlet on board
				game.movePlayer(new Player("H", new Position(23,5), null, 1), newPosition);
				fail();
			} catch (GameOfCluedo.InvalidMove e) {
				//pass since newPosition is position of another player
			}
		}
		
		/**
	     * Test successful move to empty position
	     */
		@Test
		public void testMovePlayer_7() {
			try { 
				GameOfCluedo game = new GameOfCluedo();
				Position newPosition = new Position(1,6); //1,6 is null on board
				game.movePlayer(new Player("H", new Position(23,5), null, 1), newPosition);
				//pass
			} catch (GameOfCluedo.InvalidMove e) {
				fail(e.getMessage());
			}
		}
		
/*	----------------------
 *  Board Tests: 
 *  ----------------------
 */
		
		/**
	     * Test getPiece
	     */
		@Test
		public void testBoard_1() { 
			Board board = new Board();
			Piece piece = board.getPiece(new Position(0,0)); //should get wall
			if(piece instanceof Wall)
				;//pass
			else
				fail();
		}
		
		/**
	     * Test moving weapon from one room to other
	     */
		@Test
		public void testBoard_2() { 
			Board board = new Board();
			Room oldRoom = new RoomImpl("Ball Room");
			Room newRoom = new RoomImpl("Hall");
			Weapon weapon = new Weapon("my weapon");
			
			oldRoom.setWeapon(weapon);
			board.moveWeapon(weapon, newRoom);
			
			if(!newRoom.hasWeapon())
				fail();
			if(!newRoom.getWeapon().equals(weapon))
				fail();
		}
		
		
		/**
	     * Test getting empty space in corner room for teleporting
	     */
		@Test
		public void testBoard_3() { 
			Board board = new Board();
			
			CornerRoom study = new CornerRoom("Study");
			Position p = board.getEmptySpace(study); //position returned should be 22,18
			if(p.getxPos() != 22)
				fail();
			if(p.getyPos() != 18)
				fail();
		}

		/**
	     * Test getting empty space if another player is present in the room as well
	     */
		@Test
		public void testBoard_4() { 
			Board board = new Board();
			
			CornerRoom study = new CornerRoom("Study");
			board.addPlayer(new Player("h", new Position(22,18), new CornerRoom("Study"), 4));
			Position p = board.getEmptySpace(study); //position returned should be 22,18
			if(p.getxPos() != 22)
				fail();
			if(p.getyPos() == 18)
				fail();
			//since a player exists at position 22, 18,
			//the position returned by method should be 22, 19 (next empty space in study)
		}


		/**
	     * Test moving player by providing new Position
	     */
		@Test
		public void testBoard_5() { 
			Board board = new Board();
			
			Player player = new Player("h", new Position(22,18), new CornerRoom("Study"), 3);
			board.movePlayer(player, new Position(1,7));
			
			if(player.getPosition().getxPos() == 22 || player.getPosition().getyPos() == 18)
				fail();
			if(board.getPiece(new Position(22,18)).equals(player))
				fail();
			//if player's position didn't change, then fail
			//if piece in board's old location didn't change, then fail
		}

		/**
	     * Test removing player from board
	     */
		@Test
		public void testBoard_6() { 
			Board board = new Board();
			
			Player player = new Player("h", new Position(22,18), new CornerRoom("Study"), 3);
			board.addPlayer(player);
			board.removePlayer(player);
			if(!board.getPiece(new Position(22,18)).getName().equals("Study"))
				fail();
			//if players piece is not replaced, then fail
		}
		
/*	----------------------
 *  Teleporting Tests: 
 *  ----------------------
 */
    /**
     * Test successfully teleporting a player
     */
	@Test
	public void testTeleportPlayer_1() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			CornerRoom c = new CornerRoom("Lounge");
			c.setCornerRoom(new CornerRoom("Conservatory"));
			game.teleportPlayer(new Player("H", new Position(23,5),c, 1), c);
		} catch (GameOfCluedo.InvalidMove e) {
			fail(e.getMessage());
		}
	}

	/**
     * Test unsuccessful teleporting of player 
     */
	@Test
	public void testTeleportPlayer_2() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("Lounge");
			game.teleportPlayer(new Player("H", new Position(23,5),c, 1), c);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since player can only teleport if they are on corner rooms
		}
	}
	
	/**
     * Test unsuccessful teleporting of player 
     */
	@Test
	public void testTeleportPlayer_3() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.teleportPlayer(new Player("H", new Position(23,5),c, 1), c);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since there is no space on board corresponding to the room
		}
	}

/*	----------------------
 *  Accusation Tests: 
 *  ----------------------
 */
	
	/**
     * Test unsuccessful accusation by player 
     */
	@Test
	public void testAccusationPlayer_1() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerAccusation(new Player("H", new Position(23,5),c, 1), 8, 1, 1);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since character index is out of bounds
		}
	}

	/**
     * Test unsuccessful accusation by player 
     */
	@Test
	public void testAccusationPlayer_2() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerAccusation(new Player("H", new Position(23,5),c, 1), 1, 8, 1);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since weapon index is out of bounds
		}
	}

	/**
     * Test unsuccessful accusation by player 
     */
	@Test
	public void testAccusationPlayer_3() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerAccusation(new Player("H", new Position(23,5),c, 1), 1, 1, 99);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since character index is out of bounds
		}
	}
	
	/**
     * Test unsuccessful accusation by player 
     */
	@Test
	public void testAccusationPlayer_4() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerAccusation(new Player("H", new Position(23,5),c, 1), -1, 1, 1);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since character index is out of bounds
		}
	}

	/**
     * Test unsuccessful accusation by player 
     */
	@Test
	public void testAccusationPlayer_5() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerAccusation(new Player("H", new Position(23,5),c, 1), 1, 6, 1);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since weapon < weapons.size()
		}
	}

	/**
     * Test unsuccessful accusation by player 
     */
	@Test
	public void testAccusationPlayer_6() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerAccusation(new Player("H", new Position(23,5),c, 1), 1, 1,-4);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since character index is out of bounds
		}
	}

	/**
     * Test successful accusation by player 
     */
	@Test
	public void testAccusationPlayer_7() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			ArrayList<Card> solution = game.getSolution();
			int character = game.getCharacters().indexOf(solution.get(0));
			int weapon = game.getWeapons().indexOf(solution.get(1));
			int room= game.getRooms().indexOf(solution.get(2));
			Room c = new RoomImpl("RandomRoom");
			game.playerAccusation(new Player("H", new Position(23,5),c, 1), character,weapon,room);
			if(!game.hasWon()){
				fail();
			}
		} catch (GameOfCluedo.InvalidMove e) {
			fail(e.getMessage());
			
		}
	}

	/**
     * Test player is inactive if wrong accusation has been made 
     */
	@Test
	public void testAccusationPlayer_8() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			ArrayList<Card> solution = game.getSolution();
			int character = game.getCharacters().indexOf(solution.get(0));
			//following is to ensure that the wrong character choice is made
			if(character == 0){
				character++;
			}else{
				character--;
			}
			Room c = new RoomImpl("RandomRoom");
			Player p = new Player("H", new Position(23,5),c, 1);
			game.playerAccusation(p, character,0,0);
			if(p.isActive()){
				fail();
			}
			//if player is inactive then pass
		} catch (GameOfCluedo.InvalidMove e) {
			fail(e.getMessage());	
		}
	}

/*	----------------------
 *  Suggestion Tests: 
 *  ----------------------
 */
	/**
     * Test unsuccessful suggestion by player 
     */
	@Test
	public void testSuggestionPlayer_1() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			game.playerSuggestion(new Player("H", new Position(23,5), null, 1), 2, 1);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since player cannot make a suggestion if they are not in a room
		}
	}

	/**
     * Test unsuccessful suggestion by player 
     */
	@Test
	public void testSuggestionPlayer_2() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerSuggestion(new Player("H", new Position(23,5),c, 1), 9, 1);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since character index is out of bounds
		}
	}

	/**
     * Test unsuccessful suggestion by player 
     */
	@Test
	public void testSuggestionPlayer_3() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerSuggestion(new Player("H", new Position(23,5),c, 1), -1, 1);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since character index is out of bounds
		}
	}
	
	/**
     * Test unsuccessful suggestion by player 
     */
	@Test
	public void testSuggestionPlayer_4() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerSuggestion(new Player("H", new Position(23,5),c, 1), 1, 8);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since weapon index is out of bounds
		}
	}

	/**
     * Test unsuccessful suggestion by player 
     */
	@Test
	public void testSuggestionPlayer_5() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerSuggestion(new Player("H", new Position(23,5),c, 1), 1, -2);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since weapon index is out of bounds
		}
	}

	/**
     * Test unsuccessful suggestion by player 
     */
	@Test
	public void testSuggestionPlayer_6() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			c.setWeapon(new Weapon("RandomWeapon"));
			game.playerSuggestion(new Player("H", new Position(23,5),c, 1), 1, 1);
			fail();
		} catch (GameOfCluedo.InvalidMove e) {
			//pass since room already has a weapon, not chosen by the player 
		}
	}
	
	/**
     * Test successful suggestion by player 
     */
	@Test
	public void testSuggestionPlayer_7() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			c.setWeapon(new Weapon("CandleStick"));
			game.playerSuggestion(new Player("H", new Position(23,5),c, 1), 1, 0);
			//pass since room already has a weapon, but it is chosen by the player as well
		} catch (GameOfCluedo.InvalidMove e) {			
			fail(e.getMessage());
		}
	}
	
	/**
     * Test successful suggestion by player 
     */
	@Test
	public void testSuggestionPlayer_8() {
		try { 
			GameOfCluedo game = new GameOfCluedo();
			Room c = new RoomImpl("RandomRoom");
			game.playerSuggestion(new Player("H", new Position(23,5),c, 1), 0,0);
			
		} catch (GameOfCluedo.InvalidMove e) {
			fail(e.getMessage());		
		}
	}
}
