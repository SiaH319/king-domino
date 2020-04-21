package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.CalculationController;
import ca.mcgill.ecse223.kingdomino.controller.DisjointSet;
import ca.mcgill.ecse223.kingdomino.controller.DominoController;
import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.controller.Square;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusInGame;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step Definition Class for Calculating Player Score
 *
 * @author Mohamad. Created Apr 12, 2020.
 */
public class CalculatingPlayerScoreStepDefinitions {
	@Given("the game is initialized for calculating player score")
	public void the_game_is_initialized_for_calculating_player_score() {
		// Intialize empty game
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
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
		if (game.getCurrentDraft() == null) {
			Draft newCurrentDraft = new Draft(DraftStatus.FaceUp, game);
			game.setCurrentDraft(newCurrentDraft);
			newCurrentDraft.addIdSortedDomino(getdominoByID(19));
			newCurrentDraft.addIdSortedDomino(getdominoByID(2));
			newCurrentDraft.addIdSortedDomino(getdominoByID(30));
			newCurrentDraft.addIdSortedDomino(getdominoByID(3));
			game.addAllDraft(newCurrentDraft);
			game.getNextPlayer().setDominoSelection(new DominoSelection(game.getNextPlayer(), getdominoByID(19), newCurrentDraft));
		}
	}

	@Given("the current player has no dominoes in his\\/her kingdom yet")
	public void the_current_player_has_no_dominoes_in_his_her_kingdom_yet() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for(KingdomTerritory t : game.getNextPlayer().getKingdom().getTerritories()) {
			game.getNextPlayer().getKingdom().removeTerritory(t);
		}
		assertEquals(1,game.getNextPlayer().getKingdom().getTerritories().size()); //should only have the castle
	}


	@Given("the score of the current player is {int}")
	public void the_score_of_the_current_player_is(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();

		CalculationController.calculateCurrentPlayerScore();

		assertEquals((int)int1,game.getNextPlayer().getPropertyScore());
	}

	@Then("the score of the current player shall be {int}")
	public void the_score_of_the_current_player_shall_be(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();


		assertEquals((int)int1,game.getNextPlayer().getPropertyScore());
	}

	@Given("the game has no bonus options selected")
	public void the_game_has_no_bonus_options_selected() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for(BonusOption b:game.getSelectedBonusOptions()) {
			game.removeSelectedBonusOption(b);
		}
		assertEquals(0,game.getSelectedBonusOptions().size());
	}

	@Given("the current player is placing his\\/her domino with ID {int}")
	public void the_current_player_is_placing_his_her_domino_with_ID(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getNextPlayer().getKingdom();
		if(game.getNextPlayer().getDominoSelection()==null) {
			game.getNextPlayer().setDominoSelection(new DominoSelection(game.getNextPlayer(), getdominoByID(int1), game.getCurrentDraft()));
		}
		DominoInKingdom dominoInKingdom = new DominoInKingdom(0, 0, kingdom, getdominoByID(int1));
		kingdom.addTerritory(dominoInKingdom);
		dominoInKingdom.setDirection(getDirection("down"));
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

	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
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
