package ca.mcgill.ecse223.kingdomino.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

public class addDefaultController {
	public static void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	
	public static void addDefaultDominoes(Game game) {
		BufferedReader reader;
		boolean hasCrown;
		String left, right, crown =null;
		TerrainType leftTile = null, rightTile =null;
		try {
			reader = new BufferedReader(new FileReader(
					"../../src/main/resources/alldominoes.dat"));
			String line = reader.readLine();
			while (line != null) {
				hasCrown = line.contains("(");
				String[] s1= line.split(":");
				String id = s1[0];
				String[] s2 = s1[1].split("+");
				left = s2[0];
				if (!hasCrown) {
					right = s2[1];
				}
				else {
					String[] s3 = s2[1].split("(");
					right = s3[0];
					String[] s4 = s3[1].split(")");
					crown = s4[0];
				}
			
				if (left == "W") leftTile = TerrainType.WheatField;
				else if (left == "F") leftTile = TerrainType.Forest;
				else if (left == "L") leftTile = TerrainType.Lake;
				else if (left == "G") leftTile = TerrainType.Grass;
				else if (left == "S") leftTile = TerrainType.Swamp;
				else if (left == "M") leftTile = TerrainType.Mountain;
					
			
				if (right == "W") rightTile = TerrainType.WheatField;
				else if (right == "F") rightTile = TerrainType.Forest;
				else if (right == "L") rightTile = TerrainType.Lake;
				else if (right == "G") rightTile = TerrainType.Grass;
				else if (right == "S") rightTile = TerrainType.Swamp;
				else if (right == "M") rightTile = TerrainType.Mountain;
					
			
			game.hasAllDominos();
			game.addAllDomino(Integer.parseInt(id), leftTile, rightTile, Integer.parseInt(crown));
				// read next line
			line = reader.readLine();
		} 
			reader.close();
			}catch (IOException e) {
			e.printStackTrace();
		}
				
	}

}
