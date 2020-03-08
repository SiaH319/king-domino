package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.User;

public class ValidationController {

	public static boolean verifyUserCreation(String name) {
/*
		if(name == null)  throw new java.lang.IllegalArgumentException("No name set for user creation");


		if (!User.getWithName(name).setName(name)) return false; // has duplicate name
		if (User.getWithName(name).getKingdomino() == null) return false; // has no kingdomino
	    else return true;
	    */
		if (User.getWithName(name) == null) return true;
		else return false;}
}
