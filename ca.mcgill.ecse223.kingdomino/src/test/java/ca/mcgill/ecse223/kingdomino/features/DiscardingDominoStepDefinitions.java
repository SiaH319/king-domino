package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.DisjointSet;
import ca.mcgill.ecse223.kingdomino.controller.DominoController;
import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.controller.Square;
import ca.mcgill.ecse223.kingdomino.controller.VerificationController;
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
 * TODO Put here a description of what this class does.
 *
 * @author Mohamad. Created Mar 28, 2020.
 */
public class DiscardingDominoStepDefinitions {
	Player CurrentPlayer;
	DominoInKingdom dominoInKingdom;

	@Given("the game is initialized for discarding domino")
	public void the_game_is_initialized_for_discarding_domino() {
		// Intialize empty game
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		CurrentPlayer = game.getNextPlayer();
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

	@Given("it is not the last turn of the game")
	public void it_is_not_the_last_turn_of_the_game() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if (game.getCurrentDraft() == null) {
			Draft newCurrentDraft = new Draft(DraftStatus.FaceUp, game);
			game.setCurrentDraft(newCurrentDraft);
			newCurrentDraft.addIdSortedDomino(getdominoByID(1));
			newCurrentDraft.addIdSortedDomino(getdominoByID(2));
			newCurrentDraft.addIdSortedDomino(getdominoByID(3));
			newCurrentDraft.addIdSortedDomino(getdominoByID(4));
			game.addAllDraft(newCurrentDraft);
		}
		// if its not the last turn in game then there is a next draft different than
		// null
		Draft newNextDraft = new Draft(DraftStatus.FaceUp, game);
		game.setNextDraft(newNextDraft);
		newNextDraft.addIdSortedDomino(getdominoByID(5));
		newNextDraft.addIdSortedDomino(getdominoByID(9));
		newNextDraft.addIdSortedDomino(getdominoByID(7));
		newNextDraft.addIdSortedDomino(getdominoByID(8));

		game.addAllDraft(newNextDraft);
		assertEquals(false, GameplayController.isCurrentTurnTheLastInGame());
	}

	@Given("the current player is not the last player in the turn")
	public void the_current_player_is_not_the_last_player_in_the_turn() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		// current play is not last so he didnt select the domino with the largst id;
		// the others have already selected a domino from the next draft if there is one
		if (game.getNextDraft() != null) {
			game.getPlayer(2).setDominoSelection(new DominoSelection(game.getPlayer(2),
					game.getNextDraft().getIdSortedDomino(0), game.getNextDraft()));
			game.getPlayer(3).setDominoSelection(new DominoSelection(game.getPlayer(3),
					game.getNextDraft().getIdSortedDomino(1), game.getNextDraft()));

		}
		game.getPlayer(1).setDominoSelection(new DominoSelection(game.getPlayer(1),
				game.getCurrentDraft().getIdSortedDomino(3), game.getCurrentDraft()));// Last Player
		game.getNextPlayer().setDominoSelection(new DominoSelection(game.getNextPlayer(),
				game.getCurrentDraft().getIdSortedDomino(2), game.getCurrentDraft()));// CurrentPlayer
		getdominoByID(1).setStatus(DominoStatus.PlacedInKingdom);// or could have been discarded
		getdominoByID(2).setStatus(DominoStatus.PlacedInKingdom);// or could have been discarded

		assertEquals(false, GameplayController.isCurrentPlayerTheLastInTurn());
		System.out.println("Current player is not the last in turn ");

	}

	@Given("the current player is the last player in the turn")
	public void the_current_player_is_the_last_player_in_the_turn() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();

		Draft curDraft;
		if(game.getCurrentDraft()==null){
			curDraft = new Draft(DraftStatus.FaceUp,game);
		}else{
			curDraft = game.getCurrentDraft();
		}
			game.setCurrentDraft(curDraft);
			Domino domino1 = getdominoByID(15);
			Domino domino2 = getdominoByID(20);
			Domino domino3 = getdominoByID(25);
			Domino domino4 = getdominoByID(30);
			domino1.setStatus(DominoStatus.InCurrentDraft);
		domino2.setStatus(DominoStatus.InCurrentDraft);
		domino3.setStatus(DominoStatus.InCurrentDraft);
		domino4.setStatus(DominoStatus.InCurrentDraft);
			Player lastPlayer = null;
			for(Player player: game.getPlayers()){
				if(player.getColor() == PlayerColor.Yellow){
					new DominoSelection(player,domino1,curDraft);
					player.setCurrentRanking(0);
				}else if(player.getColor() == PlayerColor.Blue){
					new DominoSelection(player,domino2,curDraft);
					player.setCurrentRanking(1);
				}else if(player.getColor() == PlayerColor.Green){
					new DominoSelection(player,domino3,curDraft);
					player.setCurrentRanking(2);
				}else if(player.getColor() == PlayerColor.Pink){
					new DominoSelection(player,domino4,curDraft);
					player.setCurrentRanking(3);
					lastPlayer = player;
				}
			}
			game.setNextPlayer(lastPlayer);

		System.out.println("the domino selection of the current player is: "
				+ game.getNextPlayer().getDominoSelection().getDomino().getId());
		assertEquals(true, GameplayController.isCurrentPlayerTheLastInTurn());
	}

	@Given("the current player is preplacing his\\/her domino with ID {int} at location {int}:{int} with direction {string}")
	public void the_current_player_is_preplacing_his_her_domino_with_ID_3_at_location_with_direction_down(Integer ID,
			Integer x, Integer y, String direction) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getNextPlayer().getKingdom();
		boolean shouldBeLast = GameplayController.isCurrentPlayerTheLastInTurn();
		if ((int) ID != game.getNextPlayer().getDominoSelection().getDomino().getId()) {

			game.getNextPlayer().getDominoSelection().delete();
			game.getNextPlayer().setDominoSelection(
					new DominoSelection(game.getNextPlayer(), getdominoByID(ID), game.getCurrentDraft()));

		}
		boolean nowIsLast = GameplayController.isCurrentPlayerTheLastInTurn();
		if (shouldBeLast && !nowIsLast) {
			game.getCurrentDraft().removeIdSortedDomino(game.getCurrentDraft().getIdSortedDomino(3));

		}

		dominoInKingdom = new DominoInKingdom(x, y, kingdom, getdominoByID(ID));
		kingdom.addTerritory(dominoInKingdom);
		dominoInKingdom.setDirection(getDirection(direction));

	}

	@And("it is impossible to place the current domino in his\\/her kingdom")
	public void it_is_impossible_to_place_the_current_domino_in_his_her_kingdom() {
		boolean expectedImpossible = true;
		boolean actualImpossible = GameplayController.impossibleToPlaceDomino();
		assertEquals(expectedImpossible, actualImpossible);
	}

	@When("the current player discards his\\/her domino")
	public void the_current_player_discards_his_her_domino() {
		GameplayController.initStatemachine();
		GameplayController.setStateMachineState("PreplacingDomino");
		if(dominoInKingdom==null) {
			System.out.println("dominkingdom is null!!");
		}
		GameplayController.triggerDiscardDominoInSM();
		;
	}

	@Then("this player now shall be making his\\/her domino selection")
	public void this_player_now_shall_be_making_his_her_domino_selection() {
		Gamestatus expectedInGame = Gamestatus.InGame;
		GamestatusInGame expectedInGameSelecting = GamestatusInGame.SelectingNextDomino;
		assertEquals(expectedInGame, KingdominoApplication.getStateMachine().getGamestatus());
		assertEquals(expectedInGameSelecting, KingdominoApplication.getStateMachine().getGamestatusInGame());
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
	private Castle getCastle (Kingdom kingdom) {
        for(KingdomTerritory territory: kingdom.getTerritories()){
            if(territory instanceof Castle )
                return (Castle)territory;
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
