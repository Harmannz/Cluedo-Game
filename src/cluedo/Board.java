package cluedo;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Creates the board including the non-moveable pieces that make up the board.
 * For example: walls, rooms.
 *
 */
public class Board{
	
	private int SIZE = 25; //size of the board
    private Piece[][] board; //this is the underlying structure of the board
    private List<Room> rooms = new ArrayList<Room>();
    
    /**
     * Constructs game board, create doors with position 
     */
	public Board(){
		this.board = new Piece[SIZE][SIZE];
		Wall wall = new Wall();
		Door door = new Door();
		
		int i,j;
		for(i = 0; i < 25; i++){
				board[0][i] = wall;
		}
		
		for(i=10; i < 17;i++){
			for(j = 10; j < 17;j++){
				board[i][j]=wall;
			}
		}
		
		//draw Kitchen area
		CornerRoom kitchen = new CornerRoom("Kitchen");
		CornerRoom study = new CornerRoom("Study");
		kitchen.setCornerRoom(study);
		study.setCornerRoom(kitchen);
		for(i = 1; i < 7; i++){
			for(j = 0; j < 6;j++){
				if((i == 1 || i == 6) || (j == 0 || j == 5)){
					if(!(i==6 && j==4))
						board[i][j]=wall;
				}
				else{
					board[i][j]=kitchen;
				}
			}
		}
		board[6][4] = new Door();
		rooms.add(kitchen);
		rooms.add(study);
		
		//draw dining room
		Room dining = new RoomImpl("Dining Room");
		for(i = 9; i < 16; i++){
			for(j = 0; j < 8;j++){
				if(!(i==9 && (j >= 5))){
					if((i==9 || i==15) || (j==0 || j==7) || (i==10 && j >= 5)){
						board[i][j]=wall;
					}
					else{
						board[i][j]=dining;
					}
				}
			}
		}
		board[12][7] = door;
		board[15][6] = door;
		rooms.add(dining);
		
		//draw lounge
		CornerRoom lounge = new CornerRoom("Lounge");
		CornerRoom conservatory = new CornerRoom("Conservatory");
		lounge.setCornerRoom(conservatory);
		conservatory.setCornerRoom(lounge);
		
		for(i = 19; i < 25; i++){
			for(j = 0; j < 7;j++){
			    if(i==19||i==24||j==0||j==6)
			    	board[i][j]=wall;
			    else
			    	board[i][j]=lounge;
			}
		}
		board[19][5] = door;
		rooms.add(lounge);
		rooms.add(conservatory);
		
		//draw hall
		Room hall = new RoomImpl("Hall");
		for(i = 18; i < 25; i++){
			for(j = 9; j < 15;j++){
				if(i==18||i==24||j==9||j==14)
					board[i][j] = wall;
				else
					board[i][j]=hall;
			}
		}
		board[18][11] = door;
		board[18][12] = door;
		board[20][14] = door;
		rooms.add(hall);
		
		//study
		for(i = 21; i < 25; i++){
			for(j = 17; j < 25;j++){
				if(i==21||i==24||j==17||j==24)
					board[i][j]=wall;
				else
					board[i][j]=study;
			}
		}
		board[21][18] = door;
		
		//library
		Room library = new RoomImpl("Library");
		
		for(i=14; i<19;i++){
			for(j=18; j<25;j++){
				if(i==14||i==18||j==18||j==24)
					board[i][j] = wall;
				else
				    board[i][j]=library;
			}
		}
		board[14][18]=null;
		board[18][18]=null;
		board[16][18]=door;
		board[14][21]=door;
		rooms.add(library);
		
		//billiard
		Room billiard = new RoomImpl("Billiard Room");
		for(i=8; i<13;i++){
			for(j=19; j<25;j++){
				if(i==8||i==12||j==19||j==24)
					board[i][j]=wall;
				else
				    board[i][j]=billiard;
			}
		}
		board[9][19]=door;
		board[12][23]=door;
		rooms.add(billiard);
		
		//conservatory
		for(i=1; i<6;i++){
			for(j=18; j<25;j++){
				if(i==1||i==5||j==18||j==24)
					board[i][j]=wall;
				else
				    board[i][j]=conservatory;
			}
		}
		board[5][19]=door;

		//ball room
		Room ball = new RoomImpl("Ball Room");
		for(i=1; i<8;i++){
			for(j=8; j<16;j++){
				if(!(i==1 && (j<10 || j>13)))
					if(i==1||i==7||j==8||j==15||(i==2 &&(j==9||j==14)))
						board[i][j]=wall;
					else
						board[i][j]=ball;
			}
		}
		
		board[5][8]=door;
		board[7][9]=door;
		board[7][14]=door;
		board[5][15]=door;
		rooms.add(ball);
	}
	
	/**
	 * Get's size of board
	 * @return size
	 */
	public int getSize(){
		return SIZE;
	}
	
	/**
	 * Places a weapon into a random room
	 * @param weapon
	 */
	public void addWeapon (Weapon weapon){
		//take random empty room and add weapon
		Random rand = new Random();
		Room randomRoom;
		
		ArrayList<Room> emptyRooms = new ArrayList<Room>();
		for(Room room: rooms){
			if(!room.hasWeapon()){
				emptyRooms.add(room);
			}
		}
		if(!emptyRooms.isEmpty()){ //prevent errors
			randomRoom = emptyRooms.get(rand.nextInt(emptyRooms.size()));
			randomRoom.setWeapon(weapon);
		}
	}
	
	/**
	 * Prints the board
	 */
	public void printAll(){
		for(int i = 0; i < 25; i++){
			System.out.print("|");
			for(int j = 0; j < 25; j++){
				if(board[i][j] != null)
					System.out.print(board[i][j].toString() + "|");
				else
					System.out.print("_|");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Gets the piece at a given position on the board
	 * @param pos
	 * @return Piece
	 */
	public Piece getPiece(Position pos){
		Piece p = board[pos.getxPos()][pos.getyPos()];
		return p;
	}
	
    /**
     * moves player to room
     * @param piece
     * @param room
     */
	public void movePlayer(Player player, Room room){
		for( int i = 0; i < 25; i++){
			for(int j = 0; j < 25; j++){
				if(board[i][j] instanceof Room && board[i][j].equals(room)){
					movePlayer(player, new Position(i,j));
				}
			}
		}
	}

    /**
     * moves weapon to room
     * @param piece
     * @param room
     */
	public void moveWeapon(Weapon weapon, Room newRoom){
		//first remove weapon from old room using rooms field
		//set weapon to new room
		for(Room room: rooms){
			if(room.hasWeapon() && room.getWeapon().equals(weapon)){
				//remove weapon from old room
				room.removeWeapon();
			}
		}
		newRoom.setWeapon(weapon);
	}

	/**
	 * Returns the position of an empty space in the CornerRoom that is passes as the parameter.
	 * @param c
	 * @return
	 */
	public Position getEmptySpace(CornerRoom c){
		for( int i = 0; i < 25; i++){
			for(int j = 0; j < 25; j++){
				if(board[i][j] instanceof CornerRoom && board[i][j].equals(c)){
//					System.out.println("Found corner room " + board[i][j].getName());
					return new Position(i,j);
				}
			}
		}
		
		return null; //unreachable. i.e. impossible since the number of players is <= 6
	}
	
	/**
	 * moves a player to a new position
	 * @param player
	 * @param newPos
	 */
	public void movePlayer(Player player, Position newPos){
		Position oldPos = player.getPosition();
		board[oldPos.getxPos()][oldPos.getyPos()] = player.getOnPiece();
		player.setOnPiece(board[newPos.getxPos()][newPos.getyPos()]);
		board[newPos.getxPos()][newPos.getyPos()] = player;
		player.setPosition(newPos);
	}
	
	/**
	 * adds a player to the board
	 * @param p
	 */
	public void addPlayer(Player p){
		board[p.getPosition().getxPos()][p.getPosition().getyPos()] = p;
	}

	/**
	 * removes a player from the board
	 * @param player
	 */
	public void removePlayer(Player player) {
		Position pos = player.getPosition();
		board[pos.getxPos()][pos.getyPos()] = player.getOnPiece();
		
	}
}