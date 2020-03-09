package ca.mcgill.ecse223.kingdomino.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.controller.CalculateBonusController;
import ca.mcgill.ecse223.kingdomino.controller.CalculationController;
import ca.mcgill.ecse223.kingdomino.controller.DisjointSet;
import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.controller.IdentifyPropertiesController;
import ca.mcgill.ecse223.kingdomino.controller.RepeatedStepsController;
import ca.mcgill.ecse223.kingdomino.controller.Square;
import ca.mcgill.ecse223.kingdomino.controller.VerificationController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;

public class CalculatePropertyScoreController {
	
	
	
	public static void calculatePropertyScore(List<Property> properties,Player player) {
		int score=0;
		int Totalscore=0;
		
		 for (int i=0; i<properties.size(); i++) {
			 Property p = properties.get(i);
			 score = p.getSize() * p.getCrowns();
			 
			Totalscore += score;
		 }
		
		
		
		player.setPropertyScore(Totalscore);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
