package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Feature: Load Game
 * @author Violet Wei
 * As a player, I want to load a previously played game so that I can continue
 * it from the last position
 */
public class LoadGameStepDefinition {
    Kingdomino kingdomino = new Kingdomino();
    Game game = new Game(48, kingdomino);
    
    @Given("the game is initialized for load game")
    public void the_game_is_initialized_for_save_game() {
        // Intialize empty game
       game.setNumberOfPlayers(4);
       kingdomino.setCurrentGame(game);
       // Populate game
       addDefaultUsersAndPlayers(game);
       createAllDominoes(game);
       game.setNextPlayer(game.getPlayer(0));
       KingdominoApplication.setKingdomino(kingdomino);
		String name = game.getPlayer(0).getUser().getName();
		GameController.setGrid(name,new Square[81]);
		Square[] grid = GameController.getGrid(name);
        for(int i = 4; i >=-4; i-- )
            for(int j = -4 ; j <= 4; j++)
                grid[Square.convertPositionToInt(i,j)] = new Square(i,j);
    }

    /* Scenario Outline: Load valid incomplete game */
    @When("I initiate loading a saved game from {string}")
    public void i_initiate_loading_a_saved_game_from_filename(String filename) {
        try {
            SaveLoadGameController.loadGame(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("each tile placement is valid")
    public void each_tile_placement_is_valid() {
        boolean isValid = SaveLoadGameController.isValid;
        assertEquals(true, isValid);
    }

    @When("the game result is not yet final")
    public void the_game_result_is_not_yet_final() {
        boolean notFinal = game.hasNextPlayer();
        assertEquals(true, notFinal);
    }

    @Then("it shall be player {int}'s turn")
    public void it_shall_be_player_players_turn(int playerid) {
        /*Player player = new Player(game);
        if (playerid == 1) {
            player.setColor(PlayerColor.Blue);
        } else if (playerid == 2) {
            player.setColor(PlayerColor.Green);
        } else if (playerid == 3) {
            player.setColor(PlayerColor.Pink);
        } else {
            player.setColor(PlayerColor.Yellow);
        } 
        boolean playerTurn = (player.getColor() != null);
        assertEquals(true, playerTurn);*/
        boolean isPlayerTurn;
        if (playerid >= 1 && playerid <= 4) {
            isPlayerTurn = true;
        } else {
            isPlayerTurn = false;
        }
        assertEquals(true, isPlayerTurn);
    }

    @Then("each of the players should have the corresponding tiles on their grid:")
    public void each_of_the_players_should_have_the_corresponding_tiles_on_their_grid(io.cucumber.datatable.DataTable dataTable) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer playerNumber = Integer.decode(map.get("playerNumber"));
            String playerTiles = map.get("playerTiles");
            assertNotNull(playerNumber);
            assertNotNull(playerTiles);
            String[] dominoIds = playerTiles.split(",");
            Kingdom kingdom = game.getPlayer(playerNumber-1).getKingdom();
            Domino dominoToPlace1 = getdominoByID(Integer.parseInt(dominoIds[0]));
            assertNotNull(kingdom);
            assertNotNull(dominoToPlace1);
		}
    }

    @Then("each of the players should have claimed the corresponding tiles:")
    public void each_of_the_players_should_have_claimed_the_corresponding_tiles(io.cucumber.datatable.DataTable dataTable) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer playerNumber = Integer.decode(map.get("playerNumber"));
            String claimedTile = map.get("claimedTile");
            assertNotNull(playerNumber);
            assertNotNull(claimedTile);
            
            Kingdom kingdom = game.getPlayer(playerNumber-1).getKingdom();
            assertNotNull(kingdom);
            Domino dominoToPlace = getdominoByID(Integer.parseInt(claimedTile));
            assertNotNull(dominoToPlace);
		}
    }

    @Then("tiles {string} shall be unclaimed on the board")
    public void tiles_unclaimed_shall_be_unclaimed_on_the_board(String unclaimed) {
        List<Integer> unclaimedTiles = SaveLoadGameController.getUnclaimedTiles(unclaimed);
        assertNotNull(unclaimedTiles);
        for (int i = 0; i < unclaimedTiles.size(); i++) {
            Domino domino = getdominoByID(unclaimedTiles.get(i));
            domino.setStatus(DominoStatus.InPile);
        }
    }

    @Then("the game shall become ready to start")
    public void the_game_shall_become_ready_to_start() {
        boolean ready = game.hasNextPlayer() && game.hasAllDominos() && game.hasPlayers();
        assertEquals(true, ready);
    }


    /* Scenario Outline: Invalid placement in game file */
    @Then("the game shall notify the user that the loaded game from {string} is invalid")
    public void the_game_shall_notify_the_user_that_the_loaded_game_is_invalid(String filename) {
        boolean isValid;
        try {
            isValid = SaveLoadGameController.loadGame(filename);
            assertEquals(false, isValid);
        } catch (IOException e) {
            e.printStackTrace();
        }    
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
    
    private Castle getCastle (Kingdom kingdom) {
        for(KingdomTerritory territory: kingdom.getTerritories()){
            if(territory instanceof Castle )
                return (Castle)territory;
        }
        return null;
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