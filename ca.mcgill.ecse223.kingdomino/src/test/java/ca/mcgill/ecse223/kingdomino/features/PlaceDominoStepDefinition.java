package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class PlaceDominoStepDefinition{
        /**
         * F13 Step Defs: Place Dominoes
         * @author Ezer, Cecilia Jiang
         */

        private DominoInKingdom placed_domino;
        @Given("the game is initialized for move current domino")
        public void initialze_game(){
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

        @Given("it is {string}'s turn")
        public void set_next_player(String color){
            Kingdomino kingdomino = KingdominoApplication.getKingdomino();
            Game game = kingdomino.getCurrentGame();
            PlayerColor playerColor = getPlayerColor(color);
            List<Player> players = game.getPlayers();
            int i = 0;
            for(Player player: players){
                if(player.getColor() == playerColor){
                 break;
                }
                i++;
            }
            game.setNextPlayer(game.getPlayer(i));
        }

        @Given("the {string}'s kingdom has the following dominoes:")
        public void the_s_kingdom_has_the_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
            PlayerColor playerColor = getPlayerColor(string);
            Kingdomino kingdomino = KingdominoApplication.getKingdomino();
            Game game = kingdomino.getCurrentGame();
           int playerIndex=  getPlayerIndex(game, playerColor);
            Player p = game.getPlayer(playerIndex);


            // Change Datatable to a list of maps (map == column)
            List<Map<String, String>> valueMaps = dataTable.asMaps(String.class, String.class);

            for (Map<String, String> map : valueMaps) {
                // Get values from cucumber table
                Integer id = Integer.decode(map.get("domino"));
                DominoInKingdom.DirectionKind dir = getDirection(map.get("dominodir"));
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


        @Given("{string} has selected domino {int}")
        public void player_has_selected_domino_id(String playercolor, Integer id) {
            PlayerColor playerColor = getPlayerColor(playercolor);
            Kingdomino kingdomino = KingdominoApplication.getKingdomino();
            Game game = kingdomino.getCurrentGame();
            int playerIndex=  getPlayerIndex(game, playerColor);
            Player p = game.getPlayer(playerIndex);
            Domino domino = getdominoByID(id);
            Draft draft = GameController.findDraftByDominoId(game, id);
            if (KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft() == null) {
                draft = new Draft(Draft.DraftStatus.FaceDown, KingdominoApplication.getKingdomino().getCurrentGame());
            }
            game.setCurrentDraft(draft);
            game.getCurrentDraft().addIdSortedDomino(domino);
            new DominoSelection(p,domino,draft);
        }

    @Given("domino {int} is tentatively placed at position {int}:{int} with direction {string}")
    public void domino_is_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string){
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Domino domino = getdominoByID(int1);
        DominoInKingdom  dominoInKingdom = new DominoInKingdom(int2, int3, game.getNextPlayer().getKingdom(),domino);
        dominoInKingdom.setDirection(getDirection(string));
    }

        @Given("domino {int} is in {string} status")
        public void domino_is_in_status(Integer int1, String string) {
            // Set domino status to CorrectlyPreplaced
            Domino dom = getdominoByID(int1);
            dom.setStatus(getDominoStatus(string));
        }

        @When("{string} requests to place the selected domino {int}")
        public void requests_to_place_the_domino(String string, Integer int1) {
            PlayerColor playerColor = getPlayerColor(string);
            Kingdomino kingdomino = KingdominoApplication.getKingdomino();
            Game game = kingdomino.getCurrentGame();
            int playerIndex=  getPlayerIndex(game, playerColor);
            Player p = game.getPlayer(playerIndex);
            DominoController.placeDomino(p,int1);
        }

        @Then("{string}'s kingdom should now have domino {int} at position {int}:{int} with direction {string}")
        public void s_kingdom_should_now_have_domino_at_position_with_direction(String string, Integer int1, Integer int2, Integer int3, String string2) {
            PlayerColor playerColor = getPlayerColor(string);
            Kingdomino kingdomino = KingdominoApplication.getKingdomino();
            Game game = kingdomino.getCurrentGame();
            int playerIndex=  getPlayerIndex(game, playerColor);
            Player p = game.getPlayer(playerIndex);
            Kingdom kingdom =p.getKingdom();
            List<KingdomTerritory> territories = kingdom.getTerritories();
            Integer x_pos = null, y_pos = null;
            DirectionKind dir = null;

            // Loop through every territory in players kingdom
            for (KingdomTerritory territory : territories){

                // If a territory is a Domino and has the same id as dom id then get parameters
                if (territory instanceof DominoInKingdom) {
                    DominoInKingdom dom = (DominoInKingdom) territory;

                    if (dom.getDomino().getId() == int1) {
                        x_pos = dom.getX();
                        y_pos = dom.getY();
                        dir = dom.getDirection();
                    }
                }
            }

            assertEquals(int2, x_pos);
            assertEquals(int3, y_pos);
            assertEquals(getDirection(string2), dir);
        }

    ///////////////////////////////////////
    /// -----Private Helper Methods---- ///
    ///////////////////////////////////////
    private int getPlayerIndex(Game game, PlayerColor playerColor){
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

        private PlayerColor getPlayerColor(String color){
            if(color.equals("pink"))
                return PlayerColor.Pink;
            if(color.equals("blue"))
                return PlayerColor.Blue;
            if(color.equals("green"))
                return PlayerColor.Green;
            if(color.equals("Yellow"))
                return PlayerColor.Yellow;
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
