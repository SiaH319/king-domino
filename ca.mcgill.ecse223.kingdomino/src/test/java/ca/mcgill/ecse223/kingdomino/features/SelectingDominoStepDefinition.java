package ca.mcgill.ecse223.kingdomino.features;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SelectingDominoStepDefinition {
    @Given("the game has been initialized for selecting domino")
    public void the_game_has_been_initialized_for_selecting_domino() {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = new Game(12,kingdomino);
        kingdomino.setCurrentGame(game);
        createAllDominoes(game);
        GameplayController.initStatemachine();
        GameplayController.setStateMachineState("SelectingNextDomino");
        User user1 = new User("User1",kingdomino);
        User user2 = new User("User2",kingdomino);
        User user3 = new User("User3",kingdomino);
        User user4 = new User("User4",kingdomino);
        addUsersAndPlayers(new String[]{"User1","User2","User3","User4"},game);
        // Write code here that turns the phrase above into concrete actions

    }

    @Given("the order of players is {string}")
    public void the_order_of_players_is(String string) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Draft cDraft = new Draft(Draft.DraftStatus.FaceUp,game);
        game.setCurrentDraft(cDraft);
        String[] colors = string.split(",");
        Domino d11 = getdominoByID(40);
        d11.setStatus(Domino.DominoStatus.InPile);
        Domino d12 = getdominoByID(41);
        d12.setStatus(Domino.DominoStatus.InPile);
        Domino d13 = getdominoByID(42);
        d13.setStatus(Domino.DominoStatus.InPile);
        Domino d14 = getdominoByID(43);
        d14.setStatus(Domino.DominoStatus.InPile);
        cDraft.addIdSortedDominoAt(d11,0);
        cDraft.addIdSortedDominoAt(d12,1);
        cDraft.addIdSortedDominoAt(d11,2);
        cDraft.addIdSortedDominoAt(d12,3);

        int rank = 0;
        for(String color: colors){
            Player.PlayerColor playerColor = getPlayerColor(color);
            for(Player player: game.getPlayers()){
                if(player.getColor() == playerColor){
                    player.setCurrentRanking(rank);
                    new DominoSelection(player,getdominoByID(40+rank),game.getCurrentDraft());
                    rank++;
                    break;
                }
            }
        }

    }

    @Given("the next draft has the dominoes with ID {string}")
    public void the_next_draft_has_the_dominoes_with_ID(String string) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Draft nDraft = new Draft(Draft.DraftStatus.FaceUp,game);
        String[] dominoIds = string.split(",");
        for(int i =0;i<dominoIds.length;i++){
            Domino domino = getdominoByID(Integer.parseInt(dominoIds[i]));
            nDraft.addIdSortedDominoAt(domino,i);
            domino.setStatus(Domino.DominoStatus.InNextDraft);
        }
        game.setNextDraft(nDraft);
        Domino d11 = getdominoByID(11);
        d11.setStatus(Domino.DominoStatus.InPile);
        Domino d12 = getdominoByID(12);
        d12.setStatus(Domino.DominoStatus.InPile);
        Domino d13 = getdominoByID(13);
        d13.setStatus(Domino.DominoStatus.InPile);
        Domino d14 = getdominoByID(14);
        d14.setStatus(Domino.DominoStatus.InPile);
        d11.setNextDomino(d12);
        d12.setNextDomino(d13);
        d13.setNextDomino(d14);
        game.setTopDominoInPile(d11);
    }

    @Given("the {string} is selecting his\\/her domino with ID {int}")
    public void the_is_selecting_his_her_domino_with_ID(String color, Integer int1) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        GameplayController.statemachine.setGamestatus("SelectingNextDomino");
        Draft nDraft = game.getNextDraft();
        Domino domino = getdominoByID(int1);
        Player.PlayerColor playerColor = getPlayerColor(color);
        for(Player player: game.getPlayers()){
            if(player.getColor() == playerColor){
                game.setNextPlayer(player);
            }
        }

        SelectingFirstDominoStepDefinitions.id = int1;
    }

    @Then("the {string} player shall be {string} his\\/her domino")
    public void the_player_shall_be_his_her_domino(String color, String action) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Player acTualnextPlayer = game.getNextPlayer();
        Player.PlayerColor playerColor = getPlayerColor(color);
        assertEquals(playerColor,acTualnextPlayer.getColor());
        Gameplay.GamestatusInGame expectedStatusInGame =
                (action.equals("placing")? Gameplay.GamestatusInGame.PreplacingDomino:Gameplay.GamestatusInGame.SelectingNextDomino);
        Gameplay.GamestatusInGame realStatus = GameplayController.statemachine.getGamestatusInGame();
        assertEquals(expectedStatusInGame,realStatus);
    }

    @Given("the {string} player is selecting his\\/her first domino of the game with ID {int}")
    public void the_player_is_selecting_his_her_first_domino_of_the_game_with_ID(String color, Integer int1) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Draft curDraft = new Draft(Draft.DraftStatus.FaceUp,game);
        Draft nextDraft = new Draft(Draft.DraftStatus.FaceDown,game);
        GameplayController.statemachine.setGamestatus("SelectingFirstDomino");
        game.setCurrentDraft(curDraft);
        game.setNextDraft(nextDraft);
        Domino domino = getdominoByID(int1);
        Player.PlayerColor playerColor = getPlayerColor(color);
        for(Player player: game.getPlayers()){
            if(player.getColor() == playerColor){
                game.setNextPlayer(player);
                break;
            }
        }

        SelectingFirstDominoStepDefinitions.id = int1;
    }

    @When("the {string} player completes his\\/her domino selection of the game")
    public void the_player_completes_his_her_domino_selection_of_the_game(String color) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();
        Player.PlayerColor playerColor = getPlayerColor(color);
        for(Player player: game.getPlayers()){
            if(player.getColor() == playerColor){
                game.setNextPlayer(player);
                break;
            }
        }
        GameplayController.triggerMakeSelectionInSM(SelectingFirstDominoStepDefinitions.id);
        //GameplayController.triggerEventsInSM("proceed");
    }

    private static void addUsersAndPlayers(String[] userNames, Game game) {
        if(userNames.length == 3 || userNames.length == 4) {
            for (int i = 0; i < userNames.length; i++) {
                List<User> users = game.getKingdomino().getUsers();
                User curUser = null;
                for(User user: users) {
                    if(user.getName().equals(userNames[i])) {
                        curUser = user;
                        break;
                    }
                }
                if(curUser != null) {
                    Player player = new Player(game);
                    player.setUser(curUser);
                    player.setColor(Player.PlayerColor.values()[i]);
                    Kingdom kingdom = new Kingdom(player);
                    new Castle(0, 0, kingdom, player);
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                List<User> users = game.getKingdomino().getUsers();
                User curUser = null;
                for(User user: users) {
                    if(user.getName().equals(userNames[i])) {
                        curUser = user;
                        break;
                    }
                }
                if(curUser != null) {
                    Player player1 = new Player(game);
                    player1.setUser(curUser);
                    player1.setColor(Player.PlayerColor.values()[2*i]);
                    Kingdom kingdom = new Kingdom(player1);
                    new Castle(0, 0, kingdom, player1);
                    Player player2 = new Player(game);
                    player2.setUser(curUser);
                    player2.setColor(Player.PlayerColor.values()[2*i]);
                    Kingdom kingdom2 = new Kingdom(player2);
                    new Castle(0, 0, kingdom2, player2);
                }

            }
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

    private Player.PlayerColor getPlayerColor(String color) {
        switch (color) {
            case "pink":
                return Player.PlayerColor.Pink;
            case "green":
                return Player.PlayerColor.Green;
            case "yellow":
                return Player.PlayerColor.Yellow;
            case "blue":
            case "Blue":
                return Player.PlayerColor.Blue;
        }
        return null;
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

