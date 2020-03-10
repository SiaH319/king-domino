package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.SaveLoadGameController;
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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Feature: Save Game
 * @author Violet Wei
 * As a player, I want to save the current game if the game has not yet been
 * finished so that I can continue it from the last position
 */
public class SaveGameStepDefinitions {

    Kingdomino kingdomino = new Kingdomino();
    Game game = new Game(48, kingdomino);
    private static String filename = "src/test/resources/save_game_test.mov";

    /* Background */ 
    @Given("the game is initialized for save game")
    public void the_game_is_initialized_for_save_game() {
         // Intialize empty game
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
    }

    @Given("the game is still in progress")
    public void the_game_is_still_in_progress() {
        boolean isInProgress = game.hasNextPlayer();
        assertEquals(true, isInProgress);
    }

    /* Scenario Outline: Save game */ 
    @Given("no file named {string} exists in the filesystem")
    public void no_file_named_filename_exists_in_the_filesystem(String filename) {
        SaveLoadGameController.deleteFile(filename);
        File file = new File(filename);
        boolean fileExists = file.exists();
        assertEquals(false, fileExists);
    }

    @When("the user initiates saving the game to a file named {string}")
    public void the_user_initiates_saving_the_game_to_a_file_named_filename(String filename) {
        try {
            boolean isSaved = SaveLoadGameController.saveGame(filename);
            assertEquals(true, isSaved);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("cannot save game");
        }
    }

    @Then("a file named {string} shall be created in the filesystem")
    public void a_file_named_filename_shall_be_created_in_the_filesystem(String filename) {
        File file = new File(filename);
        boolean fileExists = file.exists();
        assertEquals(true, fileExists);
        SaveLoadGameController.deleteFile(filename);
    }

    /* Scenario Outline: Save game overwrites existing file name */
    File oldFile;

    @Given("the file named {string} exists in the filesystem")
    public void the_file_named_filename_exists_in_the_filesystem(String filename) {
        SaveLoadGameController.deleteFile(filename);
        SaveLoadGameController.createFile(filename);
        File file = new File(filename);
        boolean fileExists = file.exists();
        // boolean fileExists = SaveLoadGameController.createFile(filename);
        /*if (SaveLoadGameController.fileExists(filename)) {
            fileExists = true;
        } else {
            fileExists = SaveLoadGameController.createFile(filename);
        }*/
        assertEquals(true, fileExists);
        oldFile = new File(filename);
    }

    
    @When("the user initiates to save the game to a file named {string}")
    public void user_initiates_to_save_the_game_to_a_file_named_filename(String filename) {
        try {
            boolean isSaved = SaveLoadGameController.saveGame(filename);
            assertEquals(true, isSaved);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("cannot save game");
        }
    }
    
    @When("the user agrees to overwrite the existing file named {string}")
    public void the_user_agrees_to_overwrite_the_existing_file_named_filename(String filename) {
        File file = new File(filename);
        boolean agreed = file.canWrite();
        assertEquals(true, agreed);
    }

    @Then("the file named {string} shall be updated in the filesystem")
    public void the_file_named_filename_shall_be_updated_in_the_filesystem(String filename) {
        File file = new File(filename);
        assertEquals(0, oldFile.compareTo(file));
        SaveLoadGameController.deleteFile(filename);
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
}