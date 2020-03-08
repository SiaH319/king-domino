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
import ca.mcgill.ecse223.kingdomino.controller.Square;
import ca.mcgill.ecse223.kingdomino.controller.DominoController;

public class IdentifyPropertiesController {

	//Each player have ONE kingdom. And in each kingdom, there are several properties
	//Returning a list of properties
		
		
	
	public static void makeProperties(TerrainType type, Kingdom playersKingdom) {
		Square[] tiles = KingdominoController.getGrid();
		
		for(int i = 0; i < tiles.length; i++) {
			
		List<Square> sameTerrain = new ArrayList<Square>();
		
			if(tiles[i].getTerrain() == type) {
	
			sameTerrain.add(tiles[i]);
			
			}
			
		}
		
		
		
	}
	
	
}
