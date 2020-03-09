package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class getDominoController {
	
	public static Domino getDominobyId(Integer id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino result = null;
		for(Domino domino: game.getAllDominos()) {
			if(domino.getId() == id) 
				result = domino;
		}
		return result;
	}
	
	 public static List<Domino> getAllDominobyleftTtile(TerrainType left) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Domino> dominosL = null;
		for(Domino domino: game.getAllDominos()) {
			if(domino.getLeftTile() == left) 
				dominosL.add(domino);
		}
		return dominosL;
	}
	
	public static List<Domino> getAllDominobyRightTile(TerrainType right) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Domino> dominosR = null;
		for(Domino domino: game.getAllDominos()) {
			if(domino.getRightTile() == right) 
				dominosR.add(domino);
		}
		return dominosR;
	}
	
	public static List<Domino> getAllDominobyTerrainType(String terrain) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Domino> dominos = null;
		for(Domino domino: game.getAllDominos()) {
			if((domino.getRightTile()).toString() == terrain || (domino.getLeftTile()).toString() == terrain) 
				dominos.add(domino);
		}
		return dominos;
	}
	public static int getDominoTotalCrown(Domino domino) {
		return domino.getLeftCrown() + domino.getRightCrown();
	}
	
	

}
