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
			if((domino.getRightTile()).toString().equalsIgnoreCase(terrain) || (domino.getLeftTile()).toString().equalsIgnoreCase(terrain))
				dominos.add(domino);
		}
		return dominos;
	}
	public static int getDominoTotalCrown(Domino domino) {
		return domino.getLeftCrown() + domino.getRightCrown();
	}
	
	public static TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		case "wheat":
			return TerrainType.WheatField;
		case "forest":
			return TerrainType.Forest;
		case "mountain":
			return TerrainType.Mountain;
		case "grass":
			return TerrainType.Grass;
		case "swamp":
			return TerrainType.Swamp;
		case "lake":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}
	
	public static String getTerrainTypeString(TerrainType terrain) {
		switch (terrain) {
		case WheatField:
			return "wheat";
		case Forest:
			return "forest";
		case Mountain:
			return "mountain";
		case Grass:
			return "grass";
		case Swamp:
			return "swamp";
		case Lake:
			return "lake";
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}

	public static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
}
