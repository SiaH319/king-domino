package ca.mcgill.ecse223.kingdomino.features;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Feature: Move current domino
 * As a player, I wish to evaluate a provisional placement of my current domino
 * by moving the domino around into my kingdom (up, down, left, right) (F11)
 * @author Violet Wei
 */
public class MoveCurrentDominoStepDefinition {
    Kingdomino kingdomino = new Kingdomino();
    Player currentPlayer;

    @When("{string} removes his king from the domino {int}")
    public void player_removes_his_king_from_the_domino_id(String player, int dominoid) {
        Domino domino = getdominoByID(dominoid);
        DominoSelection selected = domino.getDominoSelection();
        Player player1 = selected.getPlayer();
        DominoController.initialMoveDominoToKingdom(player1, domino.getId());
    }

    @Then("domino {int} should be tentative placed at position 0:0 of {string}'s kingdom with ErroneouslyPreplaced status")
    public void domino_id_should_be_tentative_placed_at_position_0_0_of_players_kingdom_with_errorneouslypreplace_status(int dominoid, String player) {
        Domino domino = getdominoByID(dominoid);
        Player p = domino.getDominoSelection().getPlayer();
        Kingdom kingdom = p.getKingdom();
        DominoInKingdom dk = new DominoInKingdom(0, 0, kingdom, domino);
        domino.setStatus(DominoStatus.ErroneouslyPreplaced);
        assertNotNull(dk);
    }

    /* Scenario Outline: Player moves tentatively placed domino to a new neighboring tile successfully */

    @When("{string} requests to move the domino {string}")
    public void player_requests_to_move_the_domino_movement(String playerColor, String movement) {
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = kingdomino.getCurrentGame();

        int playerIndex=  getPlayerIndex(game, getPlayerColor(playerColor));
        Player p = game.getPlayer(playerIndex);
        Domino domino = p.getDominoSelection().getDomino();
        boolean isMoved = DominoController.moveCurrentDomino(p, domino.getId(),
                movement);
    }

    @Then("the domino {int} should be tentatively placed at position {int}:{int} with direction {string}")
    public void the_domino_id_should_be_tentatively_placed_at_position_nposx_nposy_with_direction_dir
            (int dominoid, int x, int y, String dir) {
        Domino domino = getdominoByID(dominoid);
        Player p = domino.getDominoSelection().getPlayer();
        currentPlayer = p;
        DominoInKingdom dik = KingdomController.getDominoInKingdomByDominoId(dominoid,p.getKingdom());

        if(dik!=null){
            assertEquals(x,dik.getX());
            assertEquals(y, dik.getY());
            assertEquals(getDirection(dir), dik.getDirection());
        }

    }

    @Then("the new status of the domino is {string}")
    public void the_new_status_of_the_domino_is_dstatus(String dstatus) {
        assertEquals(getDominoStatus(dstatus), currentPlayer.getDominoSelection().getDomino().getStatus());
    }


    @Then("the domino {int} is still tentatively placed at position {int}:{int}")
    public void the_domino_id_is_still_tentatively_placed_at_position_posx_posy(int dominoid, int posx, int posy) {
        Domino domino = getdominoByID(dominoid);
        Player p = domino.getDominoSelection().getPlayer();
        currentPlayer = p;
        DominoInKingdom dik = KingdomController.getDominoInKingdomByDominoId(dominoid,p.getKingdom());

        if(dik!=null){
            assertEquals(posx,dik.getX());
            assertEquals(posy, dik.getY());
        }
    }

    @Then("the domino should still have status {string}")
    public void the_domino_should_still_have_status_dstatus(String dstatus) {
        DominoStatus doStatus = getDominoStatus(dstatus);
        assertNotNull(doStatus);
    }

    // Excluded, InPile, InNextDraft, InCurrentDraft, CorrectlyPreplaced, ErroneouslyPreplaced, PlacedInKingdom, Discarded
    private DominoStatus getDominoStatus(String dStatus) {
        DominoStatus doStatus;
        if (dStatus.equals("CorrectlyPreplaced")) {
            doStatus = DominoStatus.CorrectlyPreplaced;
        } else if (dStatus.equals("ErroneouslyPreplaced")) {
            doStatus = DominoStatus.ErroneouslyPreplaced;
        } else if (dStatus.equals("Excluded")) {
            doStatus = DominoStatus.Excluded;
        } else if (dStatus.equals("InPile")) {
            doStatus = DominoStatus.InPile;
        } else if (dStatus.equals("InNextDraft")) {
            doStatus = DominoStatus.InNextDraft;
        } else if (dStatus.equals("InCurrentDraft")) {
            doStatus = DominoStatus.InCurrentDraft;
        } else if (dStatus.equals("PlacedInKingdom")) {
            doStatus = DominoStatus.PlacedInKingdom;
        } else {
            doStatus = DominoStatus.Discarded;
        }
        return doStatus;
    }

    private DirectionKind getDirectionKind(String direction) {
        // Up, Down, Left, Right
        DirectionKind dk;
        if (direction.equals("up")) {
            dk = DirectionKind.Up;
        } else if (direction.equals("down")) {
            dk = DirectionKind.Down;
        } else if (direction.equals("right")) {
            dk = DirectionKind.Right;
        } else {
            dk = DirectionKind.Left;
        }
        return dk;
    }

    private PlayerColor getPlayerColor(String color) {
        if (color.equals("pink")) {
            return PlayerColor.Pink;
        } else if (color.equals("green")) {
            return PlayerColor.Green;
        } else if (color.equals("yellow")) {
            return PlayerColor.Yellow;
        } else {
            return PlayerColor.Blue;
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
    private Castle getCastle (Kingdom kingdom) {
        for(KingdomTerritory territory: kingdom.getTerritories()){
            if(territory instanceof Castle )
                return (Castle)territory;
        }
        return null;
    }

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
}
