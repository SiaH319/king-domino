package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import static org.junit.Assert.assertEquals;
import java.util.List;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import java.util.*;

public class IdentifyPropertiesController {

	//Each player have ONE kingdom. And in each kingdom, there are several properties
	//Returning a list of properties

	public static void identifyKingdomProperties(Kingdom playersKingdom) {
		//this method is suppose to make the properties for a given kingdom. 
		
		for (int i = 0; i< playersKingdom.getProperties().size(); i++) {
			playersKingdom.getProperties().remove(i);
		}
			
		
		
	}
	
	public static void identifyProperties(TerrainType type, Kingdom playersKingdom) {
		
		//make a method that finds all
		
		
		
	}
	
	
}
