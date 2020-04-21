package ca.mcgill.ecse223.kingdomino.features;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse223.kingdomino.controller.CalculationController;
import ca.mcgill.ecse223.kingdomino.controller.DisjointSet;
import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.Square;

public class CalculatePropertyAttributesStepDefinitions {

	/***
	 *
	 * I want the Kingdomino app to automatically calculate
	 * the size of a property and the total number of crowns in that property.
	 * 	(F20)
	 * @author Yuta Youness Bellali 
	 */

	private Player player;

	@Given("the game is initialized for calculate property attributes")
	public void the_game_is_initialized_for_calculate_property_attributes() {

		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);

		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		String player0Name =  getStringFromPlayerColor(game.getPlayer(0));
		GameController.setGrid(player0Name, new Square[81]);
		GameController.setSet(player0Name, new DisjointSet(81));
		Square[] grid = GameController.getGrid(player0Name);
		for (int i = 4; i >= -4; i--)
			for (int j = -4; j <= 4; j++)
				grid[Square.convertPositionToInt(i, j)] = new Square(i, j);

	}

	@When("calculate property attributes is initiated")
	public void calculate_property_attributes_is_initiated() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		this.player = game.getNextPlayer();
		Kingdom playersKingdom = player.getKingdom();
		String player0Name =  getStringFromPlayerColor(game.getPlayer(0));
		DisjointSet s = GameController.getSet(player0Name);
		Square[] grid = GameController.getGrid(player0Name);
		CalculationController.identifyPropertoes(s, grid, player.getKingdom());

	}

	@Then("the player shall have a total of {int} properties")
	public void the_player_shall_have_a_total_of_properties(Integer int1) {

		Kingdom playersKingdom = player.getKingdom();
		int numberOfProperties = playersKingdom.getProperties().size();
		assertEquals(numberOfProperties, int1.intValue());

	}

	@Then("the player shall have properties with the following attributes:")
	public void the_player_shall_have_properties_with_the_following_attributes(
			io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Property> properties = game.getPlayer(0).getKingdom().getProperties();
		System.out.println("There are " + properties.size() + "properties");
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		int successNum = 0;
		for (Map<String, String> map : valueMaps) {

			TerrainType terrainType = getTerrainTypeFromString(map.get("type"));
			Integer size = Integer.decode(map.get("size"));
			Integer crown = Integer.decode(map.get("crowns"));

			for (Property p : properties) {

				if (p.getPropertyType() == terrainType && size.intValue() == p.getSize()
						&& crown.intValue() == p.getCrowns()) {
					System.out.println("Line " + successNum + " passes");
					successNum++;
					System.out.println(p.getSize());
				}
			}
		}

	}

	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////
	private char printTerrain(TerrainType terrainType) {
		char c;
		switch (terrainType) {
		case WheatField:
			c = 'W';
			break;
		case Mountain:
			c = 'M';
			break;
		case Lake:
			c = 'L';
			break;
		case Forest:
			c = 'F';
			break;
		case Grass:
			c = 'G';
			break;
		case Swamp:
			c = 'S';
			break;
		default:
			c = '/';
			break;
		}
		return c;
	}

	private Boolean isIdenticalDominoIndexes(String[] indexesFromTest, int[] indexesFromProperty) {
		int[] IdsFromTest = new int[indexesFromTest.length];
		for (int i = 0; i < IdsFromTest.length; i++)
			IdsFromTest[i] = Integer.parseInt(indexesFromTest[i]);
		boolean result = true;
		for (int i = 0; i < IdsFromTest.length; i++)
			result = result && (IdsFromTest[i] == indexesFromProperty[i]);
		return result;
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

	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	private TerrainType getTerrainTypeFromString(String terrain) {
		switch (terrain) {
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

	private DominoStatus getDominoStatus(String status) {
		switch (status) {
		case "inPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "inCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "inNextDraft":
			return DominoStatus.InNextDraft;
		case "erroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "correctlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}

	private Castle getCastle(Kingdom kingdom) {
		for (KingdomTerritory territory : kingdom.getTerritories()) {
			if (territory instanceof Castle)
				return (Castle) territory;
		}
		return null;
	}

	private static String getStringFromPlayerColor(Player p){
		String result = "";
		switch(p.getColor()){
			case Blue:
				result = "Blue";
				break;
			case Green:
				result = "Green";
				break;
			case Pink:
				result = "Pink";
				break;
			case Yellow:
				result = "Yellow";
				break;
		}
		return result;
	}

}
