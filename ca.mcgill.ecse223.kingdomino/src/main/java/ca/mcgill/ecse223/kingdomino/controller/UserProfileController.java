package ca.mcgill.ecse223.kingdomino.controller;

import java.util.ArrayList;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.NewGameStartController.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;

public class UserProfileController {
	public static void initializeUser(String string) throws InvalidInputException{
		if ( User.getWithName(string) == null) {
			KingdominoApplication.getKingdomino().addUser(string);
		}
		else {
			throw new InvalidInputException("There already is a same user");
			
		}
	}
	
	public static boolean verifyUserCreation(String name) {
/*
		if(name == null)  throw new java.lang.IllegalArgumentException("No name set for user creation");


		if (!User.getWithName(name).setName(name)) return false; // has duplicate name
		if (User.getWithName(name).getKingdomino() == null) return false; // has no kingdomino
	    else return true;
	    */
		if (User.getWithName(name) == null) return true;
		else return false;
	}
}
