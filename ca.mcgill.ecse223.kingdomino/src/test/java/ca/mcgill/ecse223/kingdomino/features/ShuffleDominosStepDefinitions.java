
package ca.mcgill.ecse223.kingdomino.features;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.DisjointSet;
import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.Square;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse223.kingdomino.features.CalculatePlayerScoreStepDefinition;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.controller.CalculateBonusController;
import ca.mcgill.ecse223.kingdomino.controller.CalculatePropertyScoreController;
import ca.mcgill.ecse223.kingdomino.controller.CalculationController;
import ca.mcgill.ecse223.kingdomino.controller.ShuffleDominoController;

public class ShuffleDominosStepDefinitions {
	@Given("the game is initialized for shuffle dominoes")
	public void the_game_is_initialized_for_shuffle_dominoes() {
	    
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);

	}

	@Given("there are {int} players playing")
	public void there_are_players_playing(Integer int1) {
	Kingdomino kingdomino = KingdominoApplication.getKingdomino();
	Game game = kingdomino.getCurrentGame();
	game.setNumberOfPlayers(int1);
		
	}

	@When("the shuffling of dominoes is initiated")
	public void the_shuffling_of_dominoes_is_initiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Domino> allDomino = game.getAllDominos();
		ShuffleDominoController.shuffleDomino(allDomino, game);
	}

	@Then("the first draft shall exist")
	public void the_first_draft_shall_exist() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		int draft = game.numberOfAllDrafts();
		assertEquals(1, draft);//review
	}

	@Then("the first draft should have {int} dominoes on the board face down")
	public void the_first_draft_should_have_dominoes_on_the_board_face_down(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		int numDominoes = game.getCurrentDraft().getIdSortedDominos().size();
		assertEquals(int1.intValue(), numDominoes);
		System.out.println(numDominoes);
	}

	@Then("there should be {int} dominoes left in the draw pile")
	public void there_should_be_dominoes_left_in_the_draw_pile(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();

	    int PiledDomino = 0;
	    for (int i = 0; i < game.getAllDominos().size(); i++) {

	    	if (game.getAllDominos().get(i).getStatus() == DominoStatus.InPile) {

	    		PiledDomino++;
	    		}

	    }

	    assertEquals(int1.intValue(), PiledDomino);
	}

	@When("I initiate to arrange the domino in the fixed order {string}")
	public void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ShuffleDominoController.fixedArrangement(string, game);
		
		
		
	}

	@Then("the draw pile should consist of everything in {string} except the first {int} dominoes with their order preserved")
	public void the_draw_pile_should_consist_of_everything_in_except_the_first_dominoes_with_their_order_preserved(String string, Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
	    
	}
	
	
    ///////////////////////////////////////
    /// -----Private Helper Methods---- ///
    ///////////////////////////////////////
    private char printTerrain(TerrainType terrainType){
        char c;
        switch(terrainType){
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
    private Boolean isIdenticalDominoIndexes(String[] indexesFromTest, int[] indexesFromProperty){
        int[] IdsFromTest = new int[indexesFromTest.length];
        for(int i = 0 ; i < IdsFromTest.length; i++)
            IdsFromTest[i] = Integer.parseInt(indexesFromTest[i]);
        boolean result = true;
        for(int i = 0 ; i < IdsFromTest.length; i++)
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
    private Castle getCastle (Kingdom kingdom) {
        for(KingdomTerritory territory: kingdom.getTerritories()){
            if(territory instanceof Castle )
                return (Castle)territory;
        }
        return null;
    }
}

