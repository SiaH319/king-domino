package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import java.util.*;

public class IdentifyPropertiesController {

	//Each player have ONE kingdom. And in each kingdom, there are several properties
	//Returning a list of properties

	public static List<Property> identifyPropertiesController(Player player) {
		
		//getting players kingdom
		Kingdom playersKingdom = player.getKingdom();
		List<Property> allProperties = new ArrayList<Property>();
		allProperties = playersKingdom.getProperties(); //but how is it stores
		return playersKingdom.getProperties();
	
	}
	
	
	
}
