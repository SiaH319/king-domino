package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
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
        // TODO: implement this
        return false;
    }
        
    public static boolean isCurrentTurnTheLastInGame() {
        // TODO: implement this
        return false;
    }

    public static boolean isCorrectlyPreplaced() {
        // TODO: implement this
        return false;
    }

    public static boolean isLoadedGameValid(){
        // TODO: implement this
        return false;
    }

    public static boolean impossibleTopPlaceDomino(){
        // TODO: implement this
        return false;
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
	
}
