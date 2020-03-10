package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/***
*As a player, I want the Kingdomino app to automatically
*check that my current domino is not overlapping with existing dominoes.
*@author Cecilia
*
***/
public class VerifyNoOverlappingStepDefinition {
    private Boolean isValid;

    @Given("the game is initialized to check domino overlapping")
    public void the_game_is_initialized_to_check_domino_overlapping() {
        // Intialize empty game
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
        for(int i = 4; i >=-4; i-- )
            for(int j = -4 ; j <= 4; j++)
                grid[Square.convertPositionToInt(i,j)] = new Square(i,j);
    }

    @Given("the following dominoes are present in a player's kingdom:")
    public void add_domino_to_player_kingdom(io.cucumber.datatable.DataTable dataTable) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        List<Map<String, String>> valueMaps = dataTable.asMaps();
        for (Map<String, String> map : valueMaps) {
            // Get values from cucumber table
            Integer id = Integer.decode(map.get("id"));
            DominoInKingdom.DirectionKind dir = getDirection(map.get("dominodir"));
            Integer posx = Integer.decode(map.get("posx"));
            Integer posy = Integer.decode(map.get("posy"));

            // Add the domino to a player's kingdom
            Domino dominoToPlace = getdominoByID(id);
            Kingdom kingdom = game.getPlayer(0).getKingdom();
            DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
            domInKingdom.setDirection(dir);
            dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
            String player0Name = (game.getPlayer(0).getUser().getName());
            Square[] grid = GameController.getGrid(player0Name);
            int[] pos = Square.splitPlacedDomino (domInKingdom, grid);
            DisjointSet s = GameController.getSet(player0Name);
            Castle castle = getCastle(kingdom);
            if(grid[pos[0]].getTerrain() == grid[pos[1]].getTerrain())
                s.union(pos[0],pos[1]);
            GameController.unionCurrentSquare(pos[0],VerificationController.getAdjacentSquareIndexesLeft
                    (castle, grid, domInKingdom),s);
            GameController.unionCurrentSquare(pos[1],VerificationController.getAdjacentSquareIndexesRight
                    (castle, grid, domInKingdom),s);
        }

        //Print Grid
        String player0Name = (game.getPlayer(0).getUser().getName());
        Square[] grid = GameController.getGrid(player0Name);
        for(int i = 4; i >=-4; i-- ){
            for(int j = -4 ; j <= 4; j++){
                int cur = Square.convertPositionToInt(j,i);
                char c = (grid[cur].getTerrain() == null) ?'/':printTerrain(grid[cur].getTerrain());
                System.out.print(""+cur+""+c+" ");
            }
            System.out.println();
        }
        System.out.println("Disjoint Set");
        System.out.println(GameController.getSet(player0Name).toString(grid));

    }

    @Given("the current player preplaced the domino with ID {int} at position <x>:<y> and direction {string}")
    public void the_current_player_preplaced_domino_with_ID_at_position_and_direction(Integer int1, Integer int2, Integer int3, String string) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Domino domino = getdominoByID(int1);
        DominoInKingdom  dominoInKingdom = new DominoInKingdom(int2, int3, game.getNextPlayer().getKingdom(),domino);
        dominoInKingdom.setDirection(getDirection(string));
    }

    @When("check current preplaced domino overlapping is initiated")
    public void check_no_overlapping (){
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Player player = game.getNextPlayer();
        Castle castle = getCastle(player.getKingdom());
        List<KingdomTerritory> list= player.getKingdom().getTerritories();
        DominoInKingdom dominoInKingdom = (DominoInKingdom)list.get(list.size() - 1);
        String player0Name = (game.getPlayer(0).getUser().getName());
        Square[] grid = GameController.getGrid(player0Name);
        if (castle != null && dominoInKingdom != null && grid !=null)
            isValid = VerificationController.verifyNoOverlapping(castle, grid, dominoInKingdom);
    }

    @Then("the current-domino\\/existing-domino overlapping is {string}")
    public void checkResult(String result) {
        Boolean expectedResult = (!result.equals("invalid"));
        assertEquals(expectedResult,isValid);
    }

    @After
    public void tearDown() {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        kingdomino.delete();
        GameController.clearGrids();
        GameController.clearSets();
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


    private void addDefaultUsersAndPlayers(Game game) {
        String[] userNames = { "User1", "User2", "User3", "User4" };
        for (int i = 0; i < userNames.length; i++) {
            User user = game.getKingdomino().addUser(userNames[i]);
            Player player = new Player(game);
            player.setUser(user);
            player.setColor(Player.PlayerColor.values()[i]);
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

    private Domino.DominoStatus getDominoStatus(String status) {
        switch (status) {
            case "inPile":
                return Domino.DominoStatus.InPile;
            case "excluded":
                return Domino.DominoStatus.Excluded;
            case "inCurrentDraft":
                return Domino.DominoStatus.InCurrentDraft;
            case "inNextDraft":
                return Domino.DominoStatus.InNextDraft;
            case "erroneouslyPreplaced":
                return Domino.DominoStatus.ErroneouslyPreplaced;
            case "correctlyPreplaced":
                return Domino.DominoStatus.CorrectlyPreplaced;
            case "placedInKingdom":
                return Domino.DominoStatus.PlacedInKingdom;
            case "discarded":
                return Domino.DominoStatus.Discarded;
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
