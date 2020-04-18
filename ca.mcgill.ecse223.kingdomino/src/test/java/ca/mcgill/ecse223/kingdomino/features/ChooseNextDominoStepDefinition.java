package ca.mcgill.ecse223.kingdomino.features;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.DominoController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Feature: Choose next domino
 * As a player, I wish to be able to choose a designated domino from the next
 * draft assuming that this domino has not yet been chosen by any other players
 * @author Violet Wei
 */
public class ChooseNextDominoStepDefinition {

    Kingdomino kingdomino = new Kingdomino();
    Game game = new Game(48, kingdomino);
    Draft nDraft;
    String color;

    @Given("the game is initialized for choose next domino")
    public void the_game_is_initialized_for_choose_next_domino() {
        // Intialize empty game
        game.setNumberOfPlayers(4);
        //game.getNextDraft().setDraftStatus(DraftStatus.Sorted);
        kingdomino.setCurrentGame(game);
        // Populate game
        addDefaultUsersAndPlayers(game);
        createAllDominoes(game);
        game.setNextPlayer(game.getPlayer(0));
        KingdominoApplication.setKingdomino(kingdomino);
    }

    /* Scenario Outline: Player choses a free domino */

    @Given("the next draft is sorted with dominoes {string}")
    public void the_next_draft_is_sorted_with_dominoes_nextdraft(String nextDraft) {
        Draft draft = new Draft(DraftStatus.Sorted, game);
        String[] dominoids = nextDraft.split(",");
        for (String dominoid : dominoids) {
            draft.addIdSortedDomino(getdominoByID(Integer.parseInt(dominoid)));
        }
        draft.setDraftStatus(DraftStatus.Sorted);
        game.setNextDraft(draft);
        nDraft = draft;
        DraftStatus draftStatus = draft.getDraftStatus();
        assertEquals(DraftStatus.Sorted, draftStatus);
    }

    @Given("player's domino selection {string}")
    public void players_domino_selection_(String selection) {
        String[] selections = selection.split(",");
        nDraft = game.getNextDraft();
        for(int i= 0; i < selections.length; i++){
            PlayerColor playercolor;
            switch (selections[i]) {
                case "pink":
                    playercolor = PlayerColor.Pink;
                    break;
                case "green":
                    playercolor = PlayerColor.Green;
                    break;
                case "yellow":
                    playercolor = PlayerColor.Yellow;
                    break;
                case "blue":
                    playercolor = PlayerColor.Blue;
                    break;
                default:
                    continue;
            }
            for(Player player: game.getPlayers()){
                if(player.getColor()==playercolor){
                    Domino domino = nDraft.getIdSortedDomino(i);
                    new DominoSelection(player,domino,nDraft);
                    break;
                }
            }
        }
        game.setCurrentDraft(nDraft);
        assertNotNull(selections);
    }

    @Given("the current player is {string}")
    public void the_current_player_is_currentplayer(String playerColor) {
        color = playerColor;
        PlayerColor playercolor;
        switch (playerColor) {
            case "pink":
                playercolor = PlayerColor.Pink;
                break;
            case "green":
                playercolor = PlayerColor.Green;
                break;
            case "yellow":
                playercolor = PlayerColor.Yellow;
                break;
            default:
                playercolor = PlayerColor.Blue;
                break;
        }
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            if (player.getColor().equals(playercolor)) {
                game.setNextPlayer(player);
                break;
            }
        }
    }

    @When("current player chooses to place king on {int}")
    public void current_player_chooses_to_place_king_on_chosendominoid(int chosendominoid) {
        DominoController.chooseNextDomino(game, chosendominoid);
    }

    @Then("current player king now is on {string}")
    public void current_player_king_now_is_on_chosendominoid(String chosendominoid) {
        Player player = game.getNextPlayer();
        Domino chosendomino = getdominoByID(Integer.parseInt(chosendominoid));
        DominoSelection dominoselection = player.getDominoSelection();
        Domino nowdomiono = dominoselection.getDomino();
        assertEquals(nowdomiono, chosendomino);
    }

    @Then("the selection for next draft is now equal to {string}")
    public void the_selection_for_next_draft_is_now_equal_to_newselection(String newselection) {
        String[] newSelections = newselection.split(",");
        List<DominoSelection> selection = game.getNextDraft().getSelections();
        PlayerColor[] cols = new PlayerColor[4];
        int counter = 0;
        for (int i = 0; i < newSelections.length; i++) {
            if (newSelections[i].equals("pink")) {
                cols[i] = PlayerColor.Pink;
            } else if (newSelections[i].equals("blue")) {
                cols[i] = PlayerColor.Blue;
            } else if (newSelections[i].equals("yellow")) {
                cols[i] = PlayerColor.Yellow;
            } else if (newSelections[i].equals("green"))  {
                cols[i] = PlayerColor.Green;
            } else {
                cols[i] = null;
            }

            if(cols[i]!=null){
                assertEquals(cols[i], selection.get(counter).getPlayer().getColor());
                counter++;
            }
        }



    }

    /* Scenario Outline: Player choses an occupied domino */
    @Then("the selection for the next draft selection is still {string}")
    public void the_selection_for_the_next_draft_selection_is_still_selection(String newselection) {
        String[] newSelections = newselection.split(",");
        List<DominoSelection> selection = game.getNextDraft().getSelections();
        PlayerColor[] cols = new PlayerColor[4];
        int counter = 0;
        for (int i = 0; i < newSelections.length; i++) {
            switch (newSelections[i]) {
                case "pink":
                    cols[i] = PlayerColor.Pink;
                    break;
                case "blue":
                    cols[i] = PlayerColor.Blue;
                    break;
                case "yellow":
                    cols[i] = PlayerColor.Yellow;
                    break;
                case "green":
                    cols[i] = PlayerColor.Green;
                    break;
                default:
                    cols[i] = null;
                    break;
            }

            if(cols[i]!=null){
                assertEquals(cols[i], selection.get(counter).getPlayer().getColor());
                counter++;
            }
        }

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
            throw new IllegalArgumentException(
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
        throw new IllegalArgumentException("Domino with ID " + id + " not found.");
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
                throw new IllegalArgumentException("Invalid terrain type: " + terrain);
        }
    }

}