package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.CalculateRankingController;
import ca.mcgill.ecse223.kingdomino.controller.CreateNextDraftController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Mohamad.
 *         Created Mar 7, 2020.
 */
public class CalculateRankingStepDefinitions {
	
	@Given("the game is initialized for calculate ranking")
	public void the_game_is_initialised_for_calculate_ranking() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	
	
	@Given("the players have the following two dominoes in their respective kingdoms:")
	public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Player current =getPlayer(map.get("player"),game);
			Integer id1 = Integer.decode(map.get("domino1"));
			DirectionKind dir1 = getDirection(map.get("dominodir1"));
			Integer posx1 = Integer.decode(map.get("posx1"));
			Integer posy1 = Integer.decode(map.get("posy1"));
			Integer id2 = Integer.decode(map.get("domino2"));
			DirectionKind dir2 = getDirection(map.get("dominodir2"));
			Integer posx2 = Integer.decode(map.get("posx2"));
			Integer posy2 = Integer.decode(map.get("posy2"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace1 = getdominoByID(id1);
			Kingdom kingdom = current.getKingdom();
			DominoInKingdom domInKingdom1 = new DominoInKingdom(posx1, posy1, kingdom, dominoToPlace1);
			domInKingdom1.setDirection(dir1);
			dominoToPlace1.setStatus(DominoStatus.PlacedInKingdom);
			Domino dominoToPlace2 = getdominoByID(id2);
			
			DominoInKingdom domInKingdom2 = new DominoInKingdom(posx2, posy2, kingdom, dominoToPlace2);
			domInKingdom2.setDirection(dir2);
			dominoToPlace2.setStatus(DominoStatus.PlacedInKingdom);
		}
	}
	
	
	@Given("the players have no tiebreak")
	public void the_players_have_no_tiebreak() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
	}
	

	
	@When("calculate ranking is initiated")
	public void calculate_ranking_is_initiated() {
		CalculateRankingController.calculateRanking();
	}
	
	@Then("player standings shall be the followings:")
	public void player_standing_shall_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			assertEquals(Integer.parseInt(map.get("standing")),getPlayer(map.get("player"),game).getCurrentRanking());
		}
		
	}
	
	
	
	

	
	
	private void addDefaultUsersAndPlayers(Game game) {
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
	
	private void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
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
	
	
	private TerrainType getTerrainType(String terrain) {
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
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}
	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
	
	private ArrayList<Integer> getListOfIDs(String aListOfIDs){
		boolean beforeIsDigit =false;
		ArrayList<Integer> myList = new ArrayList<Integer>();
		String [] ids = aListOfIDs.split(",");
		for(int i=0; i<ids.length;i++) {
			myList.add(Integer.parseInt(ids[i]));
		}

		return myList;
		
	}
	private DirectionKind getDirection(String dir) {
		switch (dir) {
		case "up":
			return DirectionKind.Up;
		case "down":
			return DirectionKind.Down;
		case "left":
			return DirectionKind.Left;
		case "right":
			return DirectionKind.Right;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}
	private Player getPlayer(String color,Game game) {
		switch(color) {
		case "green":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Green)) {
					return p;
				}
			}
		case "pink":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Pink)) {
					return p;
				}
			}
		case "blue":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Blue)) {
					return p;
				}
			}
		case "yellow":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Yellow)) {
					return p;
				}
			}
		case "yelow":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Yellow)) {
					return p;
				}
			}
		
		default:
			throw new java.lang.IllegalArgumentException("Invalid color: " + color);
		}
	}

}
