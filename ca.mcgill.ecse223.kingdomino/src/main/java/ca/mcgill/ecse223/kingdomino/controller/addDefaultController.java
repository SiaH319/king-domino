package ca.mcgill.ecse223.kingdomino.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

public class addDefaultController {
	/**
	 *  setup the default format
	 *  @author: Sia Ham
	 */
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

	public static void addAllDominoesInOrder(Game game) {
		//Game game = new Game(48, KingdominoApplication.getKingdomino());
		TerrainType W = TerrainType.WheatField;
		TerrainType F = TerrainType.Forest;
		TerrainType L = TerrainType.Lake;
		TerrainType G = TerrainType.Grass;
		TerrainType S = TerrainType.Swamp;
		TerrainType M = TerrainType.Mountain;

		game.addAllDomino(1, W, W, 0);
		game.addAllDomino(2, W, W, 0);
		game.addAllDomino(3, F, F, 0);
		game.addAllDomino(4, F, F, 0);
		game.addAllDomino(5, F, F, 0);
		game.addAllDomino(6, L, L, 0);
		game.addAllDomino(7, L, L, 0);
		game.addAllDomino(8, L, L, 0);
		game.addAllDomino(9, L, L, 0);

		game.addAllDomino(10, G, G, 0);
		game.addAllDomino(11, G, G, 0);
		game.addAllDomino(12, S, S, 0);
		game.addAllDomino(13, W, F, 0);
		game.addAllDomino(14, W, L, 0);
		game.addAllDomino(15, W, G, 0);
		game.addAllDomino(16, W, S, 0);
		game.addAllDomino(17, F, L, 0);
		game.addAllDomino(18, F, G, 0);
		game.addAllDomino(19, F, W, 1);

		game.addAllDomino(20, L, W, 1);
		game.addAllDomino(21, G, W, 1);
		game.addAllDomino(22, S, W, 1);
		game.addAllDomino(23, M, W, 1);
		game.addAllDomino(24, W, F, 1);
		game.addAllDomino(25, W, F, 1);
		game.addAllDomino(26, W, F, 1);
		game.addAllDomino(27, W, F, 1);
		game.addAllDomino(28, L, F, 1);
		game.addAllDomino(29, G, F, 1);

		game.addAllDomino(30, W, L, 1);
		game.addAllDomino(31, W, L, 1);
		game.addAllDomino(32, F, L, 1);
		game.addAllDomino(33, F, L, 1);
		game.addAllDomino(34, F, L, 1);
		game.addAllDomino(35, F, L, 1);
		game.addAllDomino(36, W, G, 1);
		game.addAllDomino(37, L, G, 1);
		game.addAllDomino(38, W, S, 1);
		game.addAllDomino(39, G, S, 1);

		game.addAllDomino(40, W, M, 1);
		game.addAllDomino(41, W, G, 2);
		game.addAllDomino(42, L, G, 2);
		game.addAllDomino(43, W, S, 2);
		game.addAllDomino(44, G, S, 2);
		game.addAllDomino(45, W, M, 2);
		game.addAllDomino(46, S, M, 2);
		game.addAllDomino(47, S, M, 2);
		game.addAllDomino(48, W, M, 3);
	}

	public static void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = DominoController.getTerrainType(dominoString[1]);
				TerrainType rightTerrain = DominoController.getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}

}
