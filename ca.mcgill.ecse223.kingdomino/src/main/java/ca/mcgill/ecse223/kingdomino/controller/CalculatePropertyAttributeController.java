package ca.mcgill.ecse223.kingdomino.controller;

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

public class CalculatePropertyAttributeController {
	
	
	/*This properties are already set in the previous method. This method will 
	 * go through all the properties and determine its size as well as the number
	 * of crowns. A domino can be part of two properties so cannot assume that 
	 * one domino = size 2. So basically need to count the number of tiles in each
	 * property.
	 */
	//find size of each property
	
	
	public static void CalculatePropertySize(Property property) {
	
		
		
		TerrainType type = property.getPropertyType();
		
		int sizeProperty=0;
		boolean sameTypeL;
		boolean sameTypeR;
		
		//check the number of tiles in a property with the same type
		
		List<Domino> dominos = property.getIncludedDominos();
		
		for (int j=0; j < dominos.size(); j++) {
			
			Domino tempDomino = dominos.get(j);
			
			//check if left tile is same type as property type
			sameTypeL = (tempDomino.getLeftTile() == type);// not sure
			if (sameTypeL == true) {
			sizeProperty++;
			}
			
			//check if right tile is same type as property type
			sameTypeR = (tempDomino.getRightTile() == type);// not sure
			if (sameTypeR == true) { 
		    sizeProperty++;
		}
		
		
				
		}
		
		property.setSize(sizeProperty);
	}
		
	public void CalculatePropertyCrown(Kingdom currentKingdom) {
		
			for(int i =0; i < currentKingdom.numberOfProperties(); i++) {
				
				List<Property> properties = currentKingdom.getProperties();
				
				Property tempProperty = properties.get(i);
				
			
				//go through all the dominos to get the number of crowns
				
				List<Domino> dominos = tempProperty.getIncludedDominos();
				int totalNumberOfCrown = 0;
				
				for (int j=0; j < dominos.size(); j++) {
					
					Domino tempDomino = dominos.get(j);
					
					totalNumberOfCrown = totalNumberOfCrown + tempDomino.getLeftCrown();
					totalNumberOfCrown = totalNumberOfCrown + tempDomino.getRightCrown();
		
	}
				
				
				tempProperty.setCrowns(totalNumberOfCrown);
				
			}
			
	}
	
}
