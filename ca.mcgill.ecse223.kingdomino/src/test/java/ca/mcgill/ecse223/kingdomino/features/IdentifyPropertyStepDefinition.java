package ca.mcgill.ecse223.kingdomino.features;

import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import static junit.framework.TestCase.assertEquals;

public class IdentifyPropertyStepDefinition {

    // scenario 1

    @Given("the game is initialized for identify properties")
    public void the_game_is_initialized_for_identify_properties() {
        // Intialize empty game
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
        for (int i = 4; i >= -4; i--)
            for (int j = -4; j <= 4; j++)
                grid[Square.convertPositionToInt(i, j)] = new Square(i, j);
    }
    // Write code here that turns the phrase above into concrete actions

    @Given("the player's kingdom has the following dominoes:")
    public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
        System.out.println("Disjoint Set");
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
            int[] pos = Square.splitPlacedDomino(domInKingdom, grid);
            DisjointSet s = GameController.getSet(player0Name);
            Castle castle = getCastle(kingdom);
            if (grid[pos[0]].getTerrain() == grid[pos[1]].getTerrain())
                s.union(pos[0], pos[1]);
            GameController.unionCurrentSquare(pos[0],
                    VerificationController.getAdjacentSquareIndexesLeft(castle, grid, domInKingdom), s);
            GameController.unionCurrentSquare(pos[1],
                    VerificationController.getAdjacentSquareIndexesRight(castle, grid, domInKingdom), s);
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

    @When("the properties of the player are identified")
    public void the_properties_of_the_player_are_identified() {
        // Write code here that turns the phrase above into concrete actions
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Player player = game.getNextPlayer();
        String player0Name = (player.getUser().getName());
        DisjointSet s =GameController.getSet(player0Name);
        Square[] grid = GameController.getGrid(player0Name);
        CalculationController.identifyPropertoes(s , grid, player.getKingdom());
    }

    @Then("the player shall have the following properties:")
    public void the_player_shall_have_the_following_properties(io.cucumber.datatable.DataTable dataTable) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        List<Property> properties = game.getPlayer(0).getKingdom().getProperties();
        System.out.println("There are "+ properties.size()+ "properties");
        List<Map<String, String>> valueMaps = dataTable.asMaps();
        int successNum = 0;
        for (Map<String, String> map : valueMaps) {
            // Set values to cucumber table?
            TerrainType terrainType = getTerrainTypeFromString(map.get("type"));
            String propertyDominoes = map.get("dominoes");
            String[] indexesFromTest = propertyDominoes.split(",");
            for(Property p: properties){
                int[] dominoIds = PropertyController.getDominoIdsFromProperty(p);
                if(p.getPropertyType() == terrainType && isIdenticalDominoIndexes(indexesFromTest, dominoIds)){
                    System.out.println("Line "+successNum+" passes");
                    successNum++;

                }
            }

        }
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
