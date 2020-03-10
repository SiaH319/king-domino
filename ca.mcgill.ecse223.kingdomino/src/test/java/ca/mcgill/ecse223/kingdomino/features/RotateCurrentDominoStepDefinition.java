package ca.mcgill.ecse223.kingdomino.features;
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

import static org.junit.Assert.assertEquals;

public class RotateCurrentDominoStepDefinition {
    @Given("the game is initialized for rotate current domino")
    public void initialize_the_game(){
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
        for(int index = 0; index< game.getNumberOfPlayers(); index++) {
            String player0Name = (game.getPlayer(index).getUser().getName());
            GameController.setGrid(player0Name, new Square[81]);
            GameController.setSet(player0Name, new DisjointSet(81));
            Square[] grid = GameController.getGrid(player0Name);
            for (int i = 4; i >= -4; i--)
                for (int j = -4; j <= 4; j++)
                    grid[Square.convertPositionToInt(i, j)] = new Square(i, j);
        }
    }

    @Given("{string}'s kingdom has following dominoes:")
    public void s_kingdom_has_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
        Player.PlayerColor playerColor = getPlayerColor(string);
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        int playerIndex=  getPlayerIndex(game, playerColor);
        Player p = game.getPlayer(playerIndex);


        // Change Datatable to a list of maps (map == column)
        List<Map<String, String>> valueMaps = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> map : valueMaps) {
            // Get values from cucumber table
            Integer id = Integer.decode(map.get("id"));
            DominoInKingdom.DirectionKind dir = getDirection(map.get("dir"));
            Integer posx = Integer.decode(map.get("posx"));
            Integer posy = Integer.decode(map.get("posy"));

            // Add the domino to a player's kingdom
            Domino dominoToPlace = getdominoByID(id);
            Kingdom kingdom = p.getKingdom();
            DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
            domInKingdom.setDirection(dir);
            dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
            String player0Name = p.getUser().getName();
            Square[] grid = GameController.getGrid(player0Name);
            int[] pos = Square.splitPlacedDomino (domInKingdom, grid);
            DisjointSet s = GameController.getSet(player0Name);
            Castle castle = getCastle(kingdom);
            if(grid[pos[0]].getTerrain() == grid[pos[1]].getTerrain())
                s.union(pos[0],pos[1]);
            GameController.unionCurrentSquare(pos[0], VerificationController.getAdjacentSquareIndexesLeft
                    (castle, grid, domInKingdom),s);
            GameController.unionCurrentSquare(pos[1],VerificationController.getAdjacentSquareIndexesRight
                    (castle, grid, domInKingdom),s);
        }
    }

    @When("{string} requests to rotate the domino with {string}")
    public void player_request_to_rotate_domino(String pcolor, String rotation){
        Player.PlayerColor playerColor = getPlayerColor(pcolor);
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        int playerIndex=  getPlayerIndex(game, playerColor);
        Player p = game.getPlayer(playerIndex);
        Domino domino = p.getDominoSelection().getDomino();
        //Castle
        Castle castle = getCastle(p.getKingdom());;
        //Grid
        Square[] grid = GameController.getGrid(p.getUser().getName());
        //Territories
        List<KingdomTerritory> territories = p.getKingdom().getTerritories();
        //DIK
        DominoInKingdom dik= KingdomController.getDominoInKingdomByDominoId(domino.getId(),p.getKingdom());
        int rotationd = convertRotationToInt(rotation);
        DominoController.rotateExistingDomino(castle,grid,territories,dik,rotationd);
    }

    @Then("the domino {int} is still tentatively placed at {int}:{int} but with new direction {string}")
    public void the_domino_is_still_tentatively_placed_at_but_with_new_direction(Integer id, Integer x, Integer y, String dir) {
        // Write code here that turns the phrase above into concrete actions
        Domino domino = getdominoByID(id);
        Player p = domino.getDominoSelection().getPlayer();
        DominoInKingdom dik= KingdomController.getDominoInKingdomByDominoId(domino.getId(),p.getKingdom());
        if(dik == null) throw new java.lang.IllegalArgumentException("Domino In Kingdom not found: ");
        assertEquals((int)x, dik.getX());
        assertEquals((int)y, dik.getY());
        assertEquals(getDirection(dir),dik.getDirection());
    }

    @Then("the domino {int} should have status {string}")
    public void the_domino_should_have_status(Integer id, String dstatus) {
        Domino domino = getdominoByID(id);
        Player p = domino.getDominoSelection().getPlayer();
        DominoInKingdom dik= KingdomController.getDominoInKingdomByDominoId(domino.getId(),p.getKingdom());
        if(dik == null) throw new java.lang.IllegalArgumentException("Domino In Kingdom not found: ");
        assertEquals(getDominoStatus(dstatus),dik.getDomino().getStatus());
    }

    //Scenario Outline: Player attempts to rotate the tentatively placed domino but fails due to board size restrictions
    @Given("domino {int} has status {string}")
    public void domino_has_status(Integer int1, String string) {
        Domino dom = getdominoByID(int1);
        dom.setStatus(getDominoStatus(string));
    }

    @Then("domino {int} is tentatively placed at the same position {int}:{int} with the same direction {string}")
    public void domino_is_tentatively_placed_at_the_same_position_with_the_same_direction(Integer id, Integer x, Integer y, String dir) {
        Domino domino = getdominoByID(id);
        Player p = domino.getDominoSelection().getPlayer();
        DominoInKingdom dik= KingdomController.getDominoInKingdomByDominoId(domino.getId(),p.getKingdom());
        if(dik == null) throw new java.lang.IllegalArgumentException("Domino In Kingdom not found: ");
        assertEquals((int)x, dik.getX());
        assertEquals((int)y, dik.getY());
        assertEquals(getDirection(dir),dik.getDirection());
    }

    @Then("domino {int} should still have status {string}")
    public void domino_should_still_have_status(Integer id, String dstatus) {
        Domino domino = getdominoByID(id);
        Player p = domino.getDominoSelection().getPlayer();
        DominoInKingdom dik= KingdomController.getDominoInKingdomByDominoId(domino.getId(),p.getKingdom());
        if(dik == null) throw new java.lang.IllegalArgumentException("Domino In Kingdom not found: ");
        assertEquals(getDominoStatus(dstatus),dik.getDomino().getStatus());
    }

    ///////////////////////////////////////
    /// -----Private Helper Methods---- ///
    ///////////////////////////////////////
    private int convertRotationToInt(String rotation){
        if(rotation.equals("clockwise"))
            return 1;
        if(rotation.equals("counterclockwise"))
            return -1;
        return 0;
    }
    private int getPlayerIndex(Game game, Player.PlayerColor playerColor){
        List<Player> players = game.getPlayers();
        int i = 0;
        for(Player player: players){
            if(player.getColor() == playerColor){
                return i;
            }
            i++;
        }
        return 0;
    }

    private Player.PlayerColor getPlayerColor(String color){
        if(color.equals("pink"))
            return Player.PlayerColor.Pink;
        if(color.equals("blue"))
            return Player.PlayerColor.Blue;
        if(color.equals("green"))
            return Player.PlayerColor.Green;
        if(color.equals("yellow"))
            return Player.PlayerColor.Yellow;
        return null;
    }
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
            case "ErroneouslyPreplaced":
                return Domino.DominoStatus.ErroneouslyPreplaced;
            case "correctlyPreplaced":
                return Domino.DominoStatus.CorrectlyPreplaced;
            case "CorrectlyPreplaced":
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
