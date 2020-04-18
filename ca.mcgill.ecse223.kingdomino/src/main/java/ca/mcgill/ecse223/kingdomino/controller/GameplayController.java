package ca.mcgill.ecse223.kingdomino.controller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;

public class GameplayController {
	private static Gameplay statemachine;
	public Kingdomino kingdomino;
	private static ArrayList<Player> Orders=new ArrayList<Player>();
	private String errorMessage;
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////Helper Methods/////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void initStatemachine() {
		statemachine = KingdominoApplication.getStateMachine();
	}
	
	public static void setStateMachineState(String stateName) {
		statemachine.setGamestatus(stateName);
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////Trigger Relevant Events In SM//////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void triggerEventsInSM(String methodName) {
		initStatemachine();
		switch(methodName) {
		case "saveGame":
			statemachine.saveGame();
			break;
		case "proceed":
			statemachine.proceed();
			break;
		case "place":
			statemachine.place();
			break;
		
		case "order":
			System.out.println("calling statemachine to order");
			statemachine.order();
			break;
		case "reveal":
			statemachine.reveal();
			break;
		default :
			throw new java.lang.IllegalArgumentException("Invalid trigger event: " + methodName);
		}
		
		
	}
	/**
	 * Trigger discard action in statemachine
	 * @author Cecilia Jiang
	 */
	public static void triggerDiscardDominoInSM() {
		statemachine.discard();
	}

	/**
	 * Tigger startANewGame action in statemachine
	 * @autor Cecilia Jiang
	 * @param numOfPlayers, number of players for the new game
	 * @param mkActivated, true if middleKingdom bonus is activated
	 * @param harmonyActivated, true if harmony bonus is activated
	 */
	
	public static void triggerStartNewGameInSM(int numOfPlayers, boolean mkActivated, boolean harmonyActivated) {
		statemachine.startNewGame(numOfPlayers,mkActivated,harmonyActivated);
	}
	/**
	 * 
	 * Given an id of a domino, the current player will try to select it.
	 * @author Mohamad
	 * @param id
	 */
	public static void triggerMakeSelectionInSM(int id) {
		statemachine.makeSelection(id);
	}
	
	public static void triggerLoadGameInSM(String filename) {
		statemachine.loadGame(filename);
	}
	
	public void triggerMoveDominoInSM(String dir) {
		statemachine.moveCurrentDomino(dir);
	}
	public void triggerRotateDominoInSM(int dir) {
		statemachine.rotateCurrentDomino(dir);
	}
	
	//Guards
	/**
	 * 
	 * Checks is the current player is last in turn
	 * If there is a next draft, see how many selection
	 * have been made
	 * 
	 * If there is no next draft, compare selected domino Id to the ones in the current draft
	 * 
	 * @author Mohamad
	 * @return true if curren tplayer is last, false otherwise
	 */
	public static boolean isCurrentPlayerTheLastInTurn() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		int dominoesInNextDraftSelected=0;
		
		if(game.getNextDraft()!=null) {
			

			for(Domino d : game.getNextDraft().getIdSortedDominos()) {
				if(d.hasDominoSelection()) {
					dominoesInNextDraftSelected++;
				}
			}
		}
		else {
			boolean foundBigger=false;
			for(Domino d : game.getCurrentDraft().getIdSortedDominos()) {
				if(d.getId()>game.getNextPlayer().getDominoSelection().getDomino().getId()) {
					foundBigger=true;															// if found an id bigger than that of current player
					break;
				}
			}
			return !foundBigger;     // if no bigger, return true
		}
        if((dominoesInNextDraftSelected==3) && (game.getNumberOfPlayers()==2 || game.getNumberOfPlayers()==4)) {// if almost all dominoes in next draft
            System.out.println("CurrentPlayer is last in turn");												// are sleected

        	return true;
        }
        if((dominoesInNextDraftSelected==2) && game.getNumberOfPlayers()==3) {									//if almost all dominoes in next draft 												// are selected

        	return true;
        }
        
        return false;
    }
    /**
     * Guard In StateMachine.
     * Checks if the current turn is the last turn in game.
     * @author Mohamad
     * @return true if next draft doesn't exist
     */
    public static boolean isCurrentTurnTheLastInGame() {
    	Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    	return (game.getNextDraft()==null);
        
    }
    /**
     * Guard In StateMachine.
     * Checks if the domino status of the current player's domino selection's domino is CorrectlyPreplaced
     * @author Mohamad
     * @return true if it's CorrectlyPreplaced, false otherwise
     */
    public static boolean isCorrectlyPreplaced() {
    	Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    	Player currentPl=game.getNextPlayer();
    	Domino currentlyChosedDomino = currentPl.getDominoSelection().getDomino();
    	DominoStatus curDominoStatus = currentlyChosedDomino.getStatus();
    	return curDominoStatus == DominoStatus.CorrectlyPreplaced;
    }

    /**
     * Guard In StateMachine.
     * checks if all dominoes in current draft have selections
     * @author Mohamad
     * @return true if they all have, false otherwise.
     */
    public static boolean areAllDominoesInCurrentDraftSelected() {
    	Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    	int expectedNumberOfDominoesSelected=game.getCurrentDraft().getIdSortedDominos().size();
    	int actualNumberOfDominoesSelected=0;
    	for(Domino d: game.getCurrentDraft().getIdSortedDominos()) {
    		if(d.hasDominoSelection()) {
    			actualNumberOfDominoesSelected++;
    		}
    	}
    	
    	return ((expectedNumberOfDominoesSelected-actualNumberOfDominoesSelected)==0);
    }

    public static boolean isLoadedGameValid(String filename){
        try {
        	return SaveLoadGameController.loadGame(filename);
        }catch(Exception e) {
        	return false;
        }
        
    }
    /**
     * Guard method in sate machine.
     * Check if it's impossible to place the currently selected domino
     * @author Mohamad
     * @return true if can't place the domino anywhere, false if it can.
     */
    public static boolean impossibleToPlaceDomino(){
    	   Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    	    Player currentPl = game.getPlayer(0);
    	    Kingdom kingdom = currentPl.getKingdom();
    	    Castle castle = getCastle(kingdom);        
    	    Square[] grid = GameController.getGrid(currentPl.getUser().getName());
    	    DominoInKingdom dominoInKingdom=(DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size()-1);
    	    ArrayList<DominoInKingdom.DirectionKind> directions =new ArrayList<DominoInKingdom.DirectionKind>();
    	    directions.add(DominoInKingdom.DirectionKind.Down);
    	    directions.add(DominoInKingdom.DirectionKind.Left);
    	    directions.add(DominoInKingdom.DirectionKind.Up);
    	    directions.add(DominoInKingdom.DirectionKind.Right);
    	    for(int x=-4;x<5;x++) {
    	        for(int y=-4;y<5;y++) {
    	            for(DominoInKingdom.DirectionKind dir :directions ) {
    	                dominoInKingdom.setDirection(dir);
    	                dominoInKingdom.setX(x);
    	                dominoInKingdom.setY(y);

    	                if((VerificationController.verifyGridSize(currentPl.getKingdom().getTerritories()))  && (VerificationController.verifyNoOverlapping(castle,grid,dominoInKingdom)) && ((VerificationController.verifyCastleAdjacency(castle,dominoInKingdom)) || (VerificationController.verifyNeighborAdjacency(castle,grid,dominoInKingdom)))) {
    	                    System.out.println("Found a place where we can place the domino with x= "+x+" y="+y+"direction ="+dir);
    	                    System.out.println("A this x,y there is :"+ grid[Square.convertPositionToInt(x,y)].getTerrain());
    	                    return false; // as we have found a place where we can place the domino
    	                }
    	            }
    	        }
    	    }
    	    //couldn't place the domino anywhere.
    	    dominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
    	    return true;
    }
	
	//Accept Action Calls
	public static void acceptCallFromSM(String methodName){
		switch(methodName) {
		case "shuffleDominoPile":
			System.out.println("Going to shuffle the dominoes");
			ShuffleDominoesController.shuffle(false);
			break;
		case "createNextDraft":
			DraftController.createNewDraftIsInitiated();
			break;
		case "generateInitialPlayerOrder":
			
			break;
		case "orderNextDraft":
			DraftController.orderNewDraftInitiated();
			break;
		case "revealNextDraft":
			DraftController.revealDominoesInitiated();
			break;
		case "placeDomino":
			DominoController.placeDomino(null,0);
			break;
		case "calculateCurrentPlayerScore":
			CalculationController.calculateCurrentPlayerScore();
			break;
		case "calculateRanking":
			break;
		case "resolveTieBreak":
			break;
		case "switchCurrentPlayer":
			switchCurrentPlayerInitiated();
			break;
		case "switchDraft":
			break;
		case "save":
			break;
		case "load":
			break;
		}
		
	}
	public static void acceptPlaceDominoFromSM(Player currentPlayer,int id) {
		DominoController.placeDomino(currentPlayer,id);
	}
	
	public static void acceptDiscardDominoFromSM() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getNextPlayer();
		List<KingdomTerritory> list= player.getKingdom().getTerritories();
        DominoInKingdom dominoInKingdom = (DominoInKingdom)list.get(list.size() - 1);
		DominoController.attemptDiscardSelectedDomino(dominoInKingdom);
	}
	
	/**
	 * Method that switches the current player to find what players'turn it is.
	 *
	 * @author Mohamad
	 */
	private static void switchCurrentPlayerInitiated() {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> listOfIds=new ArrayList<Integer>();
		for(Domino d:game.getCurrentDraft().getIdSortedDominos()) {
			listOfIds.add(d.getId());
		}
		Collections.sort(listOfIds);
		
		for(Integer i : listOfIds) {
			if(getdominoByID((int)i).hasDominoSelection() && getdominoByID((int)i).getStatus()==DominoStatus.InCurrentDraft) {
				System.out.println("switched player");
				game.setNextPlayer(getdominoByID((int)i).getDominoSelection().getPlayer());
				break;
			}
		}
	}
	/**
	 * Given an order of player set it
	 * @author Mohamad
	 */
	public static void setOrderOfPlayer(Player[] order) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for(int i=0;i<game.getPlayers().size();i++) {
			game.addOrMovePlayerAt(order[i],i);
		}
	}
	public static boolean specificPlayerChosesDomino( Player player, Draft draft, int dominoId) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		return DominoController.chooseNextDomino(game,dominoId);
	}
	/**
	 * 
	 * randomly generate the order at the begining of the game
	 * @author Mohamad
	 */
	private static void generateInitialPlayerOrder() {
		 Kingdomino kd = KingdominoApplication.getKingdomino();
			Game currentGame = kd.getCurrentGame();
			int numberOfPlayers = currentGame.getNumberOfPlayers();
			Random r = new Random();
			for(int i =0; i < numberOfPlayers; i++) {
				int IndexOfplayerToTake = r.nextInt(numberOfPlayers);
				int positionInListToInsert = r.nextInt(numberOfPlayers);
				Player playerToTake = currentGame.getPlayers().get(IndexOfplayerToTake);
				currentGame.addOrMovePlayerAt(playerToTake, positionInListToInsert);
			}
			for (int i =0; i < numberOfPlayers; i++) {
				Orders.add(currentGame.getPlayers().get(i));
			}
			KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(Orders.remove(0));
	}
	/**
	 * 
	 * Given the number of players, initialise the game accordingly.
	 * @author Mohamad
	 * @param numOfPlayer, number of player for the new game
	 */
	public static void acceptInitializeGameCallFromSM(int numOfPlayer){
		System.out.println("creating the new game");
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		Game PreviousGame =game;
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayersFour(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		if(numOfPlayer==3) {
			Game game2 = new Game(36, PreviousGame.getKingdomino());
			game.setNumberOfPlayers(3);
		;
			PreviousGame.getKingdomino().setCurrentGame(game);
			addDefaultUsersAndPlayersThree(game);
			createAllDominoes(game);
			game.setNextPlayer(game.getPlayer(0));
			KingdominoApplication.setKingdomino(game.getKingdomino());
		}
		if(numOfPlayer==2) {
			Game game3 = new Game(36, PreviousGame.getKingdomino());
			game.setNumberOfPlayers(3);
		
			PreviousGame.getKingdomino().setCurrentGame(game);
			addDefaultUsersAndPlayersThree(game);
			createAllDominoes(game);
			game.setNextPlayer(game.getPlayer(0));
			KingdominoApplication.setKingdomino(game.getKingdomino());
		}
	}
	
	
	public static void acceptMoveDominoCallFromSM(String dir){
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		Player p = game.getNextPlayer();
    	DominoController.moveCurrentDomino(p, p.getDominoSelection().getDomino().getId(), dir);
    }
	
	
	public static void acceptSelectDominoCallFromSM(int dominoId){
		
	}
	
	public static void acceptRotateCurrentDomino(int dir){
    	
    }
	private static Castle getCastle (Kingdom kingdom) {
        for(KingdomTerritory territory: kingdom.getTerritories()){
            if(territory instanceof Castle )
                return (Castle)territory;
        }
        return null;
    }

	private static void addDefaultUsersAndPlayersFour(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	private static void addDefaultUsersAndPlayersThree(Game game) {
		String[] userNames = { "User5", "User6", "User7"};
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	private void addDefaultUsersAndPlayersTwo(Game game) {
		String[] userNames = { "User8", "User9"};
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	
	private static void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}
	
	
	private static TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}
	private static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
	
	private ArrayList<Integer> getListOfIDs(String aListOfIDs){
		boolean beforeIsDigit =false;
		ArrayList<Integer> myList = new ArrayList<Integer>();
		String [] ids = aListOfIDs.split(", ");
		for(int i=0; i<ids.length;i++) {
			myList.add(Integer.parseInt(ids[i]));
		}

		return myList;
		
	}
}
