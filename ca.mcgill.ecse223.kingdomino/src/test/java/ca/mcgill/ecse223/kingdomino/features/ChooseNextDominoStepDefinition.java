package ca.mcgill.ecse223.kingdomino.features;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.DominoController;
import ca.mcgill.ecse223.kingdomino.controller.VerificationController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Feature: Choose next domino
 * As a player, I wish to be able to choose a designated domino from the next
 * draft assuming that this domino has not yet been chosen by any other players
 * @author Violet Wei
 */
public class ChooseNextDominoStepDefinition {

    Kingdomino kingdomino = new Kingdomino();
    Game game = new Game(48, kingdomino);

    @Given("the game is initialized for choose next domino")
    public void the_game_is_initialized_for_choose_next_domino() {
        // Intialize empty game
       game.setNumberOfPlayers(4);
        //game.getNextDraft().setDraftStatus(DraftStatus.Sorted);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
    }

    /* Scenario Outline: Player choses a free domino */

    @Given("the next draft is sorted with dominoes {string}")
    public void the_next_draft_is_sorted_with_dominoes_nextdraft(String nextDraft) {
        Draft draft = new Draft(DraftStatus.Sorted, game);
        String[] dominoids = nextDraft.split(",");
        for (int i = 0; i < dominoids.length; i++) {
            draft.addIdSortedDomino(getdominoByID(Integer.parseInt(dominoids[i])));
        }
        draft.setDraftStatus(DraftStatus.Sorted);
        DraftStatus draftStatus = draft.getDraftStatus();
        assertEquals(DraftStatus.Sorted, draftStatus);
    }

    @Given("player's domino selection {string}")
    public void players_domino_selection_(String selection) {

    }

    @Given("the current player is {string}")
    public void the_current_player_is_currentplayer(String player) {

    }

    @When("current player chooses to place king on {int}")
    public void current_player_chooses_to_place_king_on_chosendominoid(int chosendominoid) {

    }

    @Then("current player king now is on {string}")
    public void current_player_king_now_is_on_chosendominoid(String chosendominoid) {

    }

    @Then("the selection for next draft is now equal to {string}")
    public void the_selection_for_next_draft_is_now_equal_to_newselection(String newselection) {

    }

    /* Scenario Outline: Player choses an occupied domino */
    
    @Then("the selection for the next draft selection is still {string}")
    public void the_selection_for_the_next_draft_selection_is_still_selection(String selection) {

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

}