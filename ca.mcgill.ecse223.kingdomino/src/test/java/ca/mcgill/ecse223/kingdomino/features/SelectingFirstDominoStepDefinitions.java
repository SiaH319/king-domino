package ca.mcgill.ecse223.kingdomino.features;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Violet Wei and Mohamad Feature: Selecting First Domino
 */

public class SelectingFirstDominoStepDefinitions {
	private boolean selectionSuccessful;
	/* Background */
	public static int id;
	@Given("the game has been initialized for selecting first domino")
	public void the_game_has_been_initialized_for_selecting_first_domino() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(12,kingdomino);
		kingdomino.setCurrentGame(game);
		createAllDominoes(game);
		GameplayController.initStatemachine();
		GameplayController.setStateMachineState("SelectingNextDomino");
		User user1 = new User("User1",kingdomino);
		User user2 = new User("User2",kingdomino);
		User user3 = new User("User3",kingdomino);
		User user4 = new User("User4",kingdomino);
		addUsersAndPlayers(new String[]{"User1","User2","User3","User4"},game);
		GameplayController.setStateMachineState("SelectingFirstDomino");

	}

	/* Scenario Outline: Select first domino of the game */
	@Given("the initial order of players is {string}")
	public void the_initial_order_of_players_is_playerorder(String playerOrder) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		String[] colors = playerOrder.split(",");
		int rank = 0;
		for(String color: colors){
			Player.PlayerColor playerColor = getPlayerColor(color);
			for(Player player: game.getPlayers()){
				if(player.getColor() == playerColor){
					player.setCurrentRanking(rank);
					rank++;
					break;
				}
			}
		}
	}

	@Given("the current draft has the dominoes with ID {string}")
	public void the_current_draft_has_the_dominoes_with_ID(String string) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		StringTokenizer str = new StringTokenizer(string, ",");
		int [] ids = new int[str.countTokens()];
		Draft newCurrentDraft = new Draft(DraftStatus.FaceUp,game);

		Domino d1 = getdominoByID(1);
		d1.setStatus(DominoStatus.InCurrentDraft);
		Domino d2 = getdominoByID(2);
		Domino d3 = getdominoByID(3);
		Domino d4 = getdominoByID(4);
		d2.setStatus(DominoStatus.InCurrentDraft);
		d3.setStatus(DominoStatus.InCurrentDraft);
		d4.setStatus(DominoStatus.InCurrentDraft);
		newCurrentDraft.addIdSortedDominoAt(getdominoByID(1),0);
		newCurrentDraft.addIdSortedDominoAt(getdominoByID(2),1);
		newCurrentDraft.addIdSortedDominoAt(getdominoByID(3),2);
		newCurrentDraft.addIdSortedDominoAt(getdominoByID(4),3);

		game.addAllDraft(newCurrentDraft);
		game.setCurrentDraft(newCurrentDraft);
		
	}

	@Given("player's first domino selection of the game is {string}")
	public void players_first_domino_selection_of_the_game_is_currentselection(String currentSelection) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Gameplay statemachine = KingdominoApplication.getStateMachine();
		statemachine.setGamestatus("SelectingFirstDomino");
		Draft draft = game.getCurrentDraft();
		String[] playerColors = currentSelection.split(",");
		for(int i=0; i < playerColors.length;i++){
			if(playerColors[i].equals("none")){
				continue;
			}else{
				Player.PlayerColor playerColor = getPlayerColor(playerColors[i]);
				for(Player player: game.getPlayers()){
					if(player.getColor() == playerColor){
						new DominoSelection(player,draft.getIdSortedDomino(i),draft);
						break;
					}
				}
			}
		}
	}

	@Given("the {string} player is selecting his\\/her domino with ID {int}")
	public void the_player_is_selecting_hisher_domino_with_ID(String color, int int1) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft curDraft = new Draft(Draft.DraftStatus.FaceUp,game);
		GameplayController.statemachine.setGamestatus("SelectingFirstDomino");
		game.setCurrentDraft(curDraft);
		Domino domino = getdominoByID(int1);
		Player.PlayerColor playerColor = getPlayerColor(color);
		for(Player player: game.getPlayers()){
			if(player.getColor() == playerColor){
				game.setNextPlayer(player);
				break;
			}
		}
		id = int1;

	}
	@Given("the {string} player is selecting his\\/her first domino with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_with_ID(String color, int int1) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft curDraft = new Draft(Draft.DraftStatus.FaceUp,game);
		Draft nextDraft = new Draft(DraftStatus.FaceDown,game);
		game.setNextDraft(nextDraft);
		GameplayController.statemachine.setGamestatus("SelectingFirstDomino");
		game.setCurrentDraft(curDraft);
		Player.PlayerColor playerColor = getPlayerColor(color);
		for(Player player: game.getPlayers()){
			if(player.getColor() == playerColor){
				game.setNextPlayer(player);

				break;
			}
		}

		this.id = int1;
	}

	@When("the {string} player completes his\\/her domino selection")
	public void the_player_completes_his_her_domino_selection(String color) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Player.PlayerColor playerColor = getPlayerColor(color);
		for(Player player: game.getPlayers()){
			if(player.getColor() == playerColor){
				game.setNextPlayer(player);
				break;
			}
		}
		System.out.println(GameplayController.statemachine.getGamestatusFullName());
		GameplayController.triggerMakeSelectionInSM(id);
		GameplayController.triggerEventsInSM("proceed");

	}

	@Then("the {string} player shall be {string} his/her domino")
	public void the_nextplayer_shall_be_action_his_her_domino(String color, String action) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Player acTualnextPlayer = game.getNextPlayer();
		Player.PlayerColor playerColor = getPlayerColor(color);
		Assert.assertEquals(acTualnextPlayer.getColor(),playerColor);
		Gameplay.GamestatusInGame expectedStatusInGame =
				(action.equals("placing")? Gameplay.GamestatusInGame.PreplacingDomino:Gameplay.GamestatusInGame.SelectingNextDomino);
		Gameplay.GamestatusInGame realStatus = GameplayController.statemachine.getGamestatusInGame();
		Assert.assertEquals(expectedStatusInGame,realStatus);
	}

	// We use the annotation @And to signal precondition check instead of
	// initialization (which is done in @Given methods)
	@And("the validation of domino selection returns {string}")
	public void the_validation_of_domino_selection_returns(String expectedValidationResultString) {
		boolean result = GameplayController.isSelectionValid(id);
		if(expectedValidationResultString.contains("success"))
			assertTrue(result);
		else
			assertFalse(result);

	}

	/* Scenario Outline: Complete first turn of domino selection */

	@Then("a new draft shall be available, face down")
	public void a_new_draft_shall_be_available_face_down() {
		assertEquals(DraftStatus.FaceDown,KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getDraftStatus());
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

	private static void addUsersAndPlayers(String[] userNames, Game game) {
		if(userNames.length == 3 || userNames.length == 4) {
			for (int i = 0; i < userNames.length; i++) {
				List<User> users = game.getKingdomino().getUsers();
				User curUser = null;
				for(User user: users) {
					if(user.getName().equals(userNames[i])) {
						curUser = user;
						break;
					}
				}
				if(curUser != null) {
					Player player = new Player(game);
					player.setUser(curUser);
					player.setColor(Player.PlayerColor.values()[i]);
					Kingdom kingdom = new Kingdom(player);
					new Castle(0, 0, kingdom, player);
				}
			}
		} else {
			for (int i = 0; i < 2; i++) {
				List<User> users = game.getKingdomino().getUsers();
				User curUser = null;
				for(User user: users) {
					if(user.getName().equals(userNames[i])) {
						curUser = user;
						break;
					}
				}
				if(curUser != null) {
					Player player1 = new Player(game);
					player1.setUser(curUser);
					player1.setColor(Player.PlayerColor.values()[2*i]);
					Kingdom kingdom = new Kingdom(player1);
					new Castle(0, 0, kingdom, player1);
					Player player2 = new Player(game);
					player2.setUser(curUser);
					player2.setColor(Player.PlayerColor.values()[2*i]);
					Kingdom kingdom2 = new Kingdom(player2);
					new Castle(0, 0, kingdom2, player2);
				}

			}
		}

	}
	private Player.PlayerColor getPlayerColor(String color) {
		switch (color) {
			case "pink":
				return Player.PlayerColor.Pink;
			case "green":
				return Player.PlayerColor.Green;
			case "yellow":
				return Player.PlayerColor.Yellow;
			default:
				return Player.PlayerColor.Blue;
		}
	}
}
