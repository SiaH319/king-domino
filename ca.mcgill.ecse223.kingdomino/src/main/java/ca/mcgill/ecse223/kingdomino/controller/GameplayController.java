package ca.mcgill.ecse223.kingdomino.controller;

import java.util.ArrayList;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;

public class GameplayController {
	private static Gameplay statemachine;
	public static void initStatemachine() {
		statemachine = KingdominoApplication.getStateMachine();
	}
	
	//Trigger Relevant Events In SM
	public static void triggerEventsInSM(String methodName) {
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
		boolean isLast=true;
		for(Player p : game.getPlayers()) {
			if(p.getDominoSelection().getDomino().getId()<current.getDominoSelection().getDomino().getId()) {
				isLast=false;
				break;
			}
		}
        return isLast;
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
        Castle castle = getCastle(kingdom);       
        DominoInKingdom dominoInKingdom=(DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size()-1);
        Square[] grid = GameController.getGrid(currentPl.getUser().getName());
    	if((VerificationController.verifyGridSize(currentPl.getKingdom().getTerritories()))  && (VerificationController.verifyNoOverlapping(castle,grid,dominoInKingdom)) && ((VerificationController.verifyCastleAdjacency(castle,dominoInKingdom)) || (VerificationController.verifyNeighborAdjacency(castle,grid,dominoInKingdom)))) {
             
             return true;
         }
        return false;
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
			break;
		case "createNextDraft":
			DraftController.createNewDraftIsInitiated();
			break;
		case "generateInitialPlayerOrder":
			break;
		case "orderNextDraft":
			break;
		case "revealNextDraft":
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
			break;
		case "save":
			break;
		case "load":
			break;
		}
	}
	
	public static void acceptInitializeGameCallFromSM(int numOfPlayer){
		
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
	
}
