package ca.mcgill.ecse223.kingdomino.features;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.controller.CalculateBonusController;
import ca.mcgill.ecse223.kingdomino.controller.CalculationController;
import ca.mcgill.ecse223.kingdomino.controller.DisjointSet;
import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
//import ca.mcgill.ecse223.kingdomino.controller.IdentifyPropertiesController;
import ca.mcgill.ecse223.kingdomino.controller.RepeatedStepsController;
import ca.mcgill.ecse223.kingdomino.controller.Square;
import ca.mcgill.ecse223.kingdomino.controller.VerificationController;
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
import ca.mcgill.ecse223.kingdomino.model.Property;


public class CalculateBonusScores {
	
	@Given("the game is initialized for calculate bonus scores")
	public void the_game_is_initialized_for_calculate_bonus_scores() {
	    // Write code here that turns the phrase above into concrete actions
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
	   // throw new cucumber.api.PendingException();
	}
	
	@Given("the player's kingdom also includes the domino {int} at position {int}:{int} with the direction {string}")
	public void the_player_s_kingdom_also_includes_the_domino_at_position_with_the_direction(Integer int1, Integer int2, Integer int3, String string) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Kingdom kingdom = game.getNextPlayer().getKingdom();
        Domino domino = getdominoByID(int1);
        DominoInKingdom  dominoInKingdom = new DominoInKingdom(int2, int3, game.getNextPlayer().getKingdom(),domino);
        dominoInKingdom.setDirection(getDirection(string));
        domino.setStatus(DominoStatus.PlacedInKingdom);
        
        String player0Name = (game.getPlayer(0).getUser().getName());
        Square[] grid = GameController.getGrid(player0Name);
        int[] pos = Square.splitPlacedDomino(dominoInKingdom, grid);
        DisjointSet s = GameController.getSet(player0Name);
        Castle castle = getCastle(kingdom);
        if (grid[pos[0]].getTerrain() == grid[pos[1]].getTerrain())
            s.union(pos[0], pos[1]);
        GameController.unionCurrentSquare(pos[0],
                VerificationController.getAdjacentSquareIndexesLeft(castle, grid, dominoInKingdom), s);
        GameController.unionCurrentSquare(pos[1],
               VerificationController.getAdjacentSquareIndexesRight(castle, grid, dominoInKingdom), s);
    }
   
	


	@Given("Middle Kingdom is selected as bonus option")
	public void middle_Kingdom_is_selected_as_bonus_option() {
	    // Write code here that turns the phrase above into concrete actions
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		BonusOption bonus = new BonusOption("middle Kingdom", kingdomino);
		game.addSelectedBonusOption(bonus);
		
		
		
	   // throw new cucumber.api.PendingException();
	}

	@When("calculate bonus score is initiated")
	public void calculate_bonus_score_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Player player = game.getNextPlayer();
		Kingdom kingdom = player.getKingdom();
		CalculateBonusController.CalculateBonusScore(game,player);
		
	   // throw new cucumber.api.PendingException();
	}

	@Then("the bonus score should be {int}")
	public void the_bonus_score_should_be(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Player player = game.getNextPlayer();
		int bonus = player.getBonusScore();
		assertEquals(int1.intValue(),bonus);
		
		
	   // throw new cucumber.api.PendingException();
	}

	@Given("Harmony is selected as bonus option")
	public void harmony_is_selected_as_bonus_option() {
	    // Write code here that turns the phrase above into concrete actions
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		BonusOption bonus = new BonusOption("Harmony", kingdomino);
		game.addSelectedBonusOption(bonus);
		
		
	  //  throw new cucumber.api.PendingException();
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