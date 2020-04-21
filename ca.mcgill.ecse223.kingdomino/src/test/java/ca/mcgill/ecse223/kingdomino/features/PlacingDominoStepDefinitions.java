package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.DisjointSet;
import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.controller.Square;
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
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
/**
 * TODO Put here a description of what this class does.
 *
 * @author Mohamad.
 *         Created Apr 12, 2020.
 */
public class PlacingDominoStepDefinitions {
	Player CurrentPlayer;
	@Given("the game has been initialized for placing domino")
	public void the_game_has_been_initialized_for_placing_domino() {
		// Intialize empty game
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = new Game(12, kingdomino);
        game.setNumberOfPlayers(4);
        kingdomino.setCurrentGame(game);
        // Populate game
        addDefaultUsersAndPlayers(game);
        createAllDominoes(game);
        game.setNextPlayer(game.getPlayer(0));
        CurrentPlayer=game.getNextPlayer();
        KingdominoApplication.setKingdomino(kingdomino);
        for(int k = 0; k<4;k++){
			String player0Name =  getStringFromPlayerColor(game.getPlayer(k));
			GameController.setGrid(player0Name, new Square[81]);
			GameController.setSet(player0Name, new DisjointSet(81));
			Square[] grid = GameController.getGrid(player0Name);
			for (int i = 4; i >= -4; i--)
				for (int j = -4; j <= 4; j++)
					grid[Square.convertPositionToInt(i, j)] = new Square(i, j);
		}

	}
	
	@And("the preplaced domino has the status {string}")
	public void the_preplaced_domino_has_the_status(String dominoStatusString) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getNextPlayer();
		Domino curDomino = player.getDominoSelection().getDomino();
		curDomino.setStatus(getDominoStatus(dominoStatusString));
		assertTrue(GameplayController.isCorrectlyPreplaced());
		
	}
	@When("the current player places his\\/her domino")
	public void the_current_player_places_his_her_domino() {
		GameplayController.initStatemachine();
		GameplayController.setStateMachineState("PreplacingDomino");
		GameplayController.triggerEventsInSM("place");
		System.out.println("gAME STATUS"+GameplayController.statemachine.getGamestatusInGame().toString());
	}
	
	
	
	
	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////

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
		case "CorrectlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
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
