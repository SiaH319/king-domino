package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

/**
 * @author Violet Wei and Mohamad Feature: Selecting First Domino
 */

public class SelectingFirstDominoStepDefinitions {
	private boolean selectionSuccessful;
	/* Background */
	@Given("the game has been initialized for selecting first domino")
	public void the_game_has_been_initialized_for_selecting_first_domino() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		KingdominoApplication.getStateMachine();
		String[] names = {"User1","User2","User3","User4"};
		GameplayController.initStatemachine();
		GameplayController.setStateMachineState("SelectingFirstDomino");

	}

	/* Scenario Outline: Select first domino of the game */
	@Given("the initial order of players is {string}")
	public void the_initial_order_of_players_is_playerorder(String playerOrder) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		StringTokenizer str = new StringTokenizer(playerOrder, ",");
		Player[] orderOfPlayers=new Player[game.getPlayers().size()]; 
		int i = 0;
		while (str.hasMoreTokens()) {
			orderOfPlayers[i] = getPlayerFromColor(str.nextToken()); 
			i++;
		}
		GameplayController.setOrderOfPlayer(orderOfPlayers);
	}

	@Given("the current draft has the dominoes with ID {string}")
	public void the_current_draft_has_the_dominoes_with_ID(String string) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		StringTokenizer str = new StringTokenizer(string, ",");
		int [] ids = new int[str.countTokens()];
		Draft newCurrentDraft = new Draft(DraftStatus.FaceUp,game);
//		int i=0;
//		while (str.hasMoreTokens()) {
//			ids[i]=stringToInt(str.nextToken());
//		}
//		int k=0;
//		while(k<ids.length) {
//			newCurrentDraft.addIdSortedDomino(getdominoByID(ids[k]));
//			k++;
//		}

		newCurrentDraft.addIdSortedDomino(getdominoByID(1));
		newCurrentDraft.addIdSortedDomino(getdominoByID(2));		
		newCurrentDraft.addIdSortedDomino(getdominoByID(3));
		newCurrentDraft.addIdSortedDomino(getdominoByID(4));

		game.addAllDraft(newCurrentDraft);
		game.setNextDraft(newCurrentDraft);
		
	}

	@Given("player's first domino selection of the game is {string}")
	public void players_first_domino_selection_of_the_game_is_currentselection(String currentSelection) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		StringTokenizer st =new StringTokenizer(currentSelection,",");
		String token=null;
		int i=0;
		while(st.hasMoreTokens()) {
			token=st.nextToken();
			if(!(token.equalsIgnoreCase("none"))) {
				GameplayController.specificPlayerChosesDomino(getPlayerFromColor(token),game.getCurrentDraft(),game.getCurrentDraft().getIdSortedDomino(i).getId());
			}
			i++;
		}
	}

	@Given("the {string} player is selecting his\\/her domino with ID {int}")
	public void the_player_is_selecting_hisher_domino_with_ID(String currentPlayer, int chosenDominoId) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		selectionSuccessful=GameplayController.specificPlayerChosesDomino(getPlayerFromColor(currentPlayer),game.getCurrentDraft(),chosenDominoId);
	}
	@Given("the {string} player is selecting his\\/her first domino with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_with_ID(String currentPlayer, int chosenDominoId) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		selectionSuccessful=GameplayController.specificPlayerChosesDomino(getPlayerFromColor(currentPlayer),game.getCurrentDraft(),chosenDominoId);
	}

	@When("the {string} player completes his\\/her domino selection")
	public void the_player_completes_his_her_domino_selection(String currentplayer) {
		
	}

	@Then("the {string} player shall be {string} his/her domino")
	public void the_nextplayer_shall_be_action_his_her_domino(String nextplayer, String action) {

	}

	// We use the annotation @And to signal precondition check instead of
	// initialization (which is done in @Given methods)
	@And("the validation of domino selection returns {string}")
	public void the_validation_of_domino_selection_returns(String expectedValidationResultString) {
		boolean expectedValidationResult = true;
		if ("success".equalsIgnoreCase(expectedValidationResultString.trim())) {
			expectedValidationResult = true;
		} else if ("error".equalsIgnoreCase(expectedValidationResultString.trim())) {
			expectedValidationResult = false;
		} else {
			throw new IllegalArgumentException(
					"Unknown validation result string \"" + expectedValidationResultString + "\"");
		}
		boolean actualValidationResult = false;

		// TODO call here the guard function from the statemachine and store the result
		// actualValidationResult = gameplay.isSelectionValid();

		// Check the precondition prescribed by the scenario
		assertEquals(expectedValidationResult, actualValidationResult);
	}

	/* Scenario Outline: Complete first turn of domino selection */

	@Then("a new draft shall be available, face down")
	public void a_new_draft_shall_be_available_face_down() {

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
	private int stringToInt(String str) {
		switch(str) {
		case "1":
			return 1;
		case "2":
			return 2;
		case "3":
			return 3;
		case "4":
			return 4;
		default:
			System.out.println("Could not convert");
			return 0;
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
	private Player getPlayerFromColor(String color) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		for(Player p:game.getPlayers()) {
			if(p.getColor().toString().equalsIgnoreCase(color)) {
				return p;
			}
		}
		System.out.println("Could not find player with color: "+color);
		return null;
	}
}
