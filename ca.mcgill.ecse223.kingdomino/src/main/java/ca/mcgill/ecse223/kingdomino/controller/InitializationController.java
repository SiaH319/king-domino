package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

public class InitializationController {
	public static void initializeGame() throws InvalidInputException{
		if (KingdominoApplication.getKingdomino().getCurrentGame() == null) {
			KingdominoApplication.getKingdomino().setCurrentGame(new Game(48, KingdominoApplication.getKingdomino()));
		}
		else {
			throw new InvalidInputException("A game is already running");
		}
	}

	public static void initializeDraft() throws InvalidInputException{
		if (KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft() == null) {
			new Draft(DraftStatus.FaceDown, KingdominoApplication.getKingdomino().getCurrentGame());	
		}
		else {
			throw new InvalidInputException("A draft already exists");
		}
	}
	
	public static void initializeUser(String string) throws InvalidInputException{
		if ( User.getWithName(string) == null) {
			KingdominoApplication.getKingdomino().addUser(string);
		}
		else {
			throw new InvalidInputException("There already is a same user");
		}
	}

	

	//private helper class
	public static class InvalidInputException extends Exception {
		public InvalidInputException(String errorMessage) {
			super(errorMessage);
		}

	}

}
