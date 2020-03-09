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
import ca.mcgill.ecse223.kingdomino.features.CalculatePlayerScore;
import ca.mcgill.ecse223.kingdomino.features.CalculatePropertyScore;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.controller.CalculateBonusController;
import ca.mcgill.ecse223.kingdomino.controller.CalculatePropertyScoreController;
import ca.mcgill.ecse223.kingdomino.controller.CalculationController;
public class CalculatePlayerScore {
	
	private int playerScore;
	
	@Given("the game is initialized for calculate player score")
	public void the_game_is_initialized_for_calculate_player_score() {
		 Kingdomino kingdomino = KingdominoApplication.getKingdomino();
	        Game game = new Game(48, kingdomino);
	        game.setNumberOfPlayers(4);
	        kingdomino.setCurrentGame(game);
	        // Populate game
	        addDefaultUsersAndPlayers(game);
	        createAllDominoes(game);
	        game.setNextPlayer(game.getPlayer(0));
	        KingdominoApplication.setKingdomino(kingdomino);
	        String player0Name = (game.getPlayer(0).getUser().getName());
	        GameController.setGrid(player0Name, new Square[81]);
	        GameController.setSet(player0Name, new DisjointSet(81));
	        Square[] grid = GameController.getGrid(player0Name);
	        for (int i = 4; i >= -4; i--)
	            for (int j = -4; j <= 4; j++)
	                grid[Square.convertPositionToInt(i, j)] = new Square(i, j);
	}
	
	

	@Given("the game has {string} bonus option")
	public void the_game_has_bonus_option(String string) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		BonusOption selected = new BonusOption(string,kingdomino);
		game.addSelectedBonusOption(selected);
	}

	@When("calculate player score is initiated")
	public void calculate_player_score_is_initiated() {
	 Kingdomino kingdomino = KingdominoApplication.getKingdomino();
      Game game = kingdomino.getCurrentGame();
      Player player = game.getNextPlayer();
      Kingdom kingdom = player.getKingdom();
      String player0Name = (player.getUser().getName());
      DisjointSet s =GameController.getSet(player0Name);
      Square[] grid = GameController.getGrid(player0Name);
      CalculationController.identifyPropertoes(s , grid, player.getKingdom());
	 List<Property> p = kingdom.getProperties();
	 CalculatePropertyScoreController.calculatePropertyScore(p, player);
	 CalculateBonusController.CalculateBonusScore(game, player);
	 
	 int propertyScore = player.getPropertyScore();
	 int bonusScore = player.getBonusScore();
	 this.playerScore = propertyScore + bonusScore;//???
	 System.out.println(propertyScore);
		
	   // throw new cucumber.api.PendingException();
	}

	@Then("the total score should be {int}")
	public void the_total_score_should_be(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(int1.intValue(),playerScore);
		
	   // throw new cucumber.api.PendingException();
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

