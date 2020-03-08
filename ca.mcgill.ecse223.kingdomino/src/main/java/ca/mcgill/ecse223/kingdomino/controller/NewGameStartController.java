package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

public class NewGameStartController {
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
			throw new InvalidInputException("A drat already exists");
		}
	}
	
	public static void addDefaultUsersAndPlayers(Game game) {
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
	

	//private helper class
	public static class InvalidInputException extends Exception {
		public InvalidInputException(String errorMessage) {
			super(errorMessage);
		}

	}

}
