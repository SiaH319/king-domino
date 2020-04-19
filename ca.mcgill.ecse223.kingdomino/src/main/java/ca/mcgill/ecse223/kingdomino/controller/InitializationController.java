package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import java.lang.Object.*;
import java.util.List;

public class InitializationController {
	/**
	 *  This class contains controller methods for Initializing Game, Initializing Draft, Initializing
	 *  @author: Sia Ham
	 */
	public static User findUserByName(String name, Kingdomino kingdomino){
		List<User> users = kingdomino.getUsers();
		for(User user: users){
			if(user.getName().equals(name))
				return user;
		}
		return null;
	}

	public static int getNumOfGamesPlayedByUser(User user){
		return user.getPlayedGames();
	}

	public static int getNumOfGamesWonByUser(User user){
		return user.getWonGames();
	}

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

	/**
	 * Create a new user
	 * @author Sia Ham
	 * @param string, user's name
	 * @throws InvalidInputException
	 */
	public static void initializeUser(String string) throws InvalidInputException{
		if (User.getWithName(string) == null &&
				KingdominoApplication.getKingdomino()!=null&&
				string.matches("[a-z0-9]+")) {
			try{
				KingdominoApplication.getKingdomino().addUser(string);
			}catch(Exception e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
		// no duplicate name
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