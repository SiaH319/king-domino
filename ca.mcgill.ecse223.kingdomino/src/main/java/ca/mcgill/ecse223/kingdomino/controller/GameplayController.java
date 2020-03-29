package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;

public class GameplayController {
	public static void acceptCallFromSM(String methodName){
		switch(methodName) {
		case "shuffleDominoPile":
			
			break;
		case "createNextDraft":
			DraftController.createNewDraftIsInitiated();
			break;
		}
	}
	
	public static void initializeGameCallFromSM(int numOfPlayer){
		
	}
	
	public static void moveDominoCallFromSM(String dir){
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		Player p = game.getNextPlayer();
    	DominoController.moveCurrentDomino(p, p.getDominoSelection().getDomino().getId(), dir);
    }
	
	public static void selectDominoCallFromSM(int dominoId){
		
	}
	
	public static void rotateCurrentDomino(int dir){
    	
    }
	
}
