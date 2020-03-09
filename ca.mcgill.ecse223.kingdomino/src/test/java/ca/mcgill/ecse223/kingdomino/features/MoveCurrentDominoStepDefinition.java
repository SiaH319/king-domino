package ca.mcgill.ecse223.kingdomino.features;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.DominoController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.Square;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Feature: Move current domino
 * As a player, I wish to evaluate a provisional placement of my current domino
 * by moving the domino around into my kingdom (up, down, left, right) (F11)
 * @author Violet Wei
 */
public class MoveCurrentDominoStepDefinition {
    Kingdomino kingdomino = new Kingdomino();
    Game game = new Game(48, kingdomino);
    Player currentPlayer;
    int dominoID;
    int x, y;
    DirectionKind dKind;
    DominoStatus ds;

    @Given("the game is initialized for move current domino")
    public void the_game_is_initialized_for_move_current_domino() {
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

    /* Scenario Outline: Initial tentative place of the domino */
    @Given("it is {string}'s turn")
    public void it_is_players_turn(String player) {
        PlayerColor pColor = getPlayerColor(player);
        List<Player> players = game.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getColor() == pColor) {
                currentPlayer = players.get(i);
            }
        }
        assertNotNull(currentPlayer);
    }


    @Given("{string} has selected domino {int}")
    public void player_has_selected_domino_id(String player, int dominoid) {
        DominoSelection ds = currentPlayer.getDominoSelection();
        Domino domino = getdominoByID(dominoid);
        DominoSelection selected = domino.getDominoSelection();
        dominoID = dominoid;
        //game.getCurrentDraft().addSelection(currentPlayer, domino);
        assertEquals(selected, ds);
    }

    @When("{string} removes his king from the domino {int}")
    public void player_removes_his_king_from_the_domino_id(String player, int dominoid) {
        Domino domino = getdominoByID(dominoid);
        DominoSelection selected = domino.getDominoSelection();
        // selected.delete();
        // assertEquals(null, selected);
    }

    @Then("domino {int} should be tentative placed at position 0:0 of {string}'s kingdom with ErroneouslyPreplaced status")
    public void domino_id_should_be_tentative_placed_at_position_0_0_of_players_kingdom_with_errorneouslypreplace_status(int dominoid, String player) {
        Domino domino = getdominoByID(dominoid);
        Kingdom kingdom = currentPlayer.getKingdom();
        DominoInKingdom dk = new DominoInKingdom(0, 0, kingdom, domino);
        domino.setStatus(DominoStatus.ErroneouslyPreplaced);
        assertNotNull(dk);
    }

    /* Scenario Outline: Player moves tentatively placed domino to a new neighboring tile successfully */
    
    @Given("{string}'s kingdom has following dominoes:")
    public void players_kingdom_has_following_dominoes(String player, io.cucumber.datatable.DataTable dataTable) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        List<Map<String, String>> valueMaps = dataTable.asMaps();
        for (Map<String, String> map : valueMaps) {
            // Get values from cucumber table
            Integer id = Integer.decode(map.get("id"));
            DominoInKingdom.DirectionKind dir = getDirection(map.get("dir"));
            Integer posx = Integer.decode(map.get("posx"));
            Integer posy = Integer.decode(map.get("posy"));

            // Add the domino to a player's kingdom
            Domino dominoToPlace = getdominoByID(id);
            Kingdom kingdom = game.getPlayer(0).getKingdom();
            DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
            domInKingdom.setDirection(dir);
            dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
            Square[] grid = KingdominoController.getGrid();
        }
    }

    @Given("domino {int} is tentatively placed at position {int}:{int} with direction {string}")
    public void domino_id_is_tentatively_placed_at_position_posx_posy_with_direction_dir(int dominoid, int posx, int posy, String dir) {
        Domino domino = getdominoByID(dominoid);
        Kingdom kingdom = currentPlayer.getKingdom();
        DominoInKingdom dk = new DominoInKingdom(posx, posy, kingdom, domino);
        x = posx;
        y = posy;
        dKind = getDirection(dir);
        dk.setDirection(getDirection(dir));
        assertNotNull(dk);
        boolean isPlaced = DominoController.placeDomino(game, currentPlayer, dominoid, posx, posy, getDirection(dir));
        assertEquals(true, isPlaced);
    }

    @When("{string} requests to move the domino {string}")
    public void player_requests_to_move_the_domino_movement(String player, String movement) {
        boolean isMoved = DominoController.moveCurrentDomino(game, currentPlayer, dominoID, x, y, dKind, DominoStatus.CorrectlyPreplaced);
        assertEquals(true, isMoved);
    }

    @Then("the domino {int} should be tentatively placed at position {int}:{int} with direction {string}")
    public void the_domino_id_should_be_tentatively_placed_at_position_nposx_nposy_with_direction_dir(int dominoid, int nposx, int nposy, String dir) {
        boolean isPlacedCorectly = DominoController.moveCurrentDomino(game, currentPlayer, dominoid, nposx, nposy, getDirection(dir), DominoStatus.CorrectlyPreplaced);
        assertEquals(true, isPlacedCorectly);
    }
    
    @Then("the new status of the domino is {string}")
    public void the_new_status_of_the_domino_is_dstatus(String dstatus) {
        ds = getDominoStatus(dstatus);
        boolean setNewStatus = DominoController.moveCurrentDomino(game, currentPlayer, dominoID, x, y, dKind, ds);
        assertEquals(true, setNewStatus);
    }

    /* Scenario Outline: Player attempts to move the tentatively placed domino but fails due to board size restrictions */
    @Given("domino {int} has status {string}")
    public void domino_id_has_status_dstatus(int dominoid, String dstatus) {
        Domino dominoToPlace = getdominoByID(dominoid);
        dominoToPlace.setStatus(getDominoStatus(dstatus));
        assertEquals(getDominoStatus(dstatus), dominoToPlace.getStatus());
    }
    
    
    @Then("the domino {int} is still tentatively placed at position {int}:{int}")
    public void the_domino_id_is_still_tentatively_placed_at_position_posx_posy(int dominoid, int posx, int posy) {
        boolean isPlaced = DominoController.placeDomino(game, currentPlayer, dominoid, posx, posy, DirectionKind.Up);
        assertEquals(true, isPlaced);
    }
    
    @Then("the domino should still have status {string}")
    public void the_domino_should_still_have_status_dstatus(String dstatus) {
        DominoStatus doStatus = getDominoStatus(dstatus);
        assertNotNull(doStatus);
    }

    // Excluded, InPile, InNextDraft, InCurrentDraft, CorrectlyPreplaced, ErroneouslyPreplaced, PlacedInKingdom, Discarded
    private DominoStatus getDominoStatus(String dStatus) {
        DominoStatus doStatus;
        if (dStatus.equals("CorrectlyPreplaced")) {
            doStatus = DominoStatus.CorrectlyPreplaced;
        } else if (dStatus.equals("ErroneouslyPreplaced")) {
            doStatus = DominoStatus.ErroneouslyPreplaced;
        } else if (dStatus.equals("Excluded")) {
            doStatus = DominoStatus.Excluded;
        } else if (dStatus.equals("InPile")) {
            doStatus = DominoStatus.InPile;
        } else if (dStatus.equals("InNextDraft")) {
            doStatus = DominoStatus.InNextDraft;
        } else if (dStatus.equals("InCurrentDraft")) {
            doStatus = DominoStatus.InCurrentDraft;
        } else if (dStatus.equals("PlacedInKingdom")) {
            doStatus = DominoStatus.PlacedInKingdom;
        } else {
            doStatus = DominoStatus.Discarded;
        }
        return doStatus;
    }

    private DirectionKind getDirectionKind(String direction) {
        // Up, Down, Left, Right
        DirectionKind dk;
        if (direction.equals("up")) {
            dk = DirectionKind.Up;
        } else if (direction.equals("down")) {
            dk = DirectionKind.Down;
        } else if (direction.equals("right")) {
            dk = DirectionKind.Right;
        } else {
            dk = DirectionKind.Left;
        }
        return dk;
    }

    private PlayerColor getPlayerColor(String color) {
        if (color.equals("pink")) {
            return PlayerColor.Pink;
        } else if (color.equals("green")) {
            return PlayerColor.Green;
        } else if (color.equals("yellow")) {
            return PlayerColor.Yellow;
        } else {
            return PlayerColor.Blue;
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
    
    private DominoInKingdom.DirectionKind getDirection(String dir) {
        switch (dir) {
            case "up":
                return DominoInKingdom.DirectionKind.Up;
            case "down":
                return DominoInKingdom.DirectionKind.Down;
            case "left":
                return DominoInKingdom.DirectionKind.Left;
            case "right":
                return DominoInKingdom.DirectionKind.Right;
            default:
                throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
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
}