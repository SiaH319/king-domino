package ca.mcgill.ecse223.kingdomino.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
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
	public static void initStatemachine() {
		statemachine = KingdominoApplication.getStateMachine();
	}
	
	//Trigger Relevant Events In SM
	public static void triggerEventsInSM(String methodName) {
		initStatemachine();
		switch(methodName) {
		case "loadGame":
			statemachine.loadGame();
			break;
		case "saveGame":
			statemachine.saveGame();
			break;
		case "draftReady":
			statemachine.draftReady();
			break;
		case "proceed":
			statemachine.proceed();
			break;
		case "place":
			statemachine.place();
			break;
		case "discard":
			statemachine.discard();
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
	
	public static void triggerStartNewGameInSM(int numOfPlayers) {
		System.out.println("triggeringStartNewGame");
		initStatemachine();
		statemachine.startNewGame(numOfPlayers);
	}
	public static void triggerMakeSelectionInSM(int id) {
		statemachine.makeSelection(id);
	}
	public void triggerMoveDominoInSM(String dir) {
		statemachine.moveCurrentDomino(dir);
	}
	public void triggerRotateDominoInSM(int dir) {
		statemachine.rotateCurrentDomino(dir);
	}
	
	//Guards
	public static boolean isCurrentPlayerTheLastInTurn() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player current =game.getNextPlayer();
		System.out.println("Current player has dominoselection"+current.getDominoSelection().getDomino().getId());
		int dominoesInNextDraftSelected=0;
		for(Domino d : game.getNextDraft().getIdSortedDominos()) {
			if(d.hasDominoSelection()) {
				dominoesInNextDraftSelected++;
			}
		}
        if(dominoesInNextDraftSelected==4 && (game.getNumberOfPlayers()==2 || game.getNumberOfPlayers()==4)) {
        	return true;
        }
        if(dominoesInNextDraftSelected==3 && game.getNumberOfPlayers()==3) {
        	return true;
        }
        return false;
    }
        
    public static boolean isCurrentTurnTheLastInGame() {
    	Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    	boolean isLastTurn=false;
    	int numPlayer =game.getNumberOfPlayers();
    	if(numPlayer==2 || numPlayer==4) {
    		if(game.getAllDrafts().size()==12) {
    			isLastTurn=true;
    		}
    	}
    	else {
    		if(game.getAllDrafts().size()==9) {
    			isLastTurn=true;
    		}
    	}
        return isLastTurn;
    }

    public static boolean isCorrectlyPreplaced() {
    	Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    	Player currentPl=game.getNextPlayer();
    	Kingdom kingdom = currentPl.getKingdom();       
        DominoInKingdom dominoInKingdom=(DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size()-1);
        
    	if(dominoInKingdom.getDomino().getStatus()==DominoStatus.CorrectlyPreplaced) {
             return true;
         }
        return false;
    }
    public static boolean areAllDominoesInCurrentDraftSelected() {
    	Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    	int almostNumberOfDominoesSelected=game.getCurrentDraft().getIdSortedDominos().size();
    	int actualNumberOfDominoesSelected=0;
    	for(Domino d: game.getCurrentDraft().getIdSortedDominos()) {
    		if(d.hasDominoSelection()==true) {
    			actualNumberOfDominoesSelected++;
    		}
    	}
    	if((almostNumberOfDominoesSelected-actualNumberOfDominoesSelected)==0) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    public static boolean isLoadedGameValid(){
        // TODO: implement this
        return false;
    }
 
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
    	    System.out.println("couldnt place the domino anywhere");
    	    return true;
        
        
    }
	
	//Accept Action Calls
	public static void acceptCallFromSM(String methodName){
		switch(methodName) {
		case "shuffleDominoPile":
			ShuffleDominoesController.shuffle();
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
			break;
		case "discardDomino":
			break;
		case "calculateCurrentPlayerScore":
			break;
		case "calculateRanking":
			break;
		case "resolveTieBreak":
			break;
		case "switchCurrentPlayer":
			switchCurrentPlayerInitiated();
			break;
		case "save":
			break;
		case "load":
			break;
		}
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
	private static boolean firstPlayerInThisTurn() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		int numberOfDominoesSelectedInCurrentDraft=0;
		for(Domino d:game.getCurrentDraft().getIdSortedDominos()) {
			if(d.hasDominoSelection()) {
				numberOfDominoesSelectedInCurrentDraft++;
			}
		}
		if(numberOfDominoesSelectedInCurrentDraft==4 && (game.getNumberOfPlayers()==2 || game.getNumberOfPlayers()==4)) {
			return true;
		}
		if(numberOfDominoesSelectedInCurrentDraft==3 && game.getNumberOfPlayers()==3) {
			return true;
		}
		return false;
	}

	public static void acceptInitializeGameCallFromSM(int numOfPlayer){
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
