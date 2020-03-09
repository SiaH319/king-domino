package ca.mcgill.ecse223.kingdomino.controller;

import java.io.*;
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import com.sun.deploy.security.SelectableSecurityManager;

/**
 * This class implements the controller methods for Domino
 * @author Violet
 */
public class DominoController {

    //Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    static Game game;
    static Player currentPlayer;

    /**
     * Controller implemented for Feature 10: Choose Next Domino
     * @param game
     * @return true if selection for next draft is different
     *         false if selection for next draft remains same
     * @author Violet Wei
     */
    public static boolean chooseNextDomino(Game game, PlayerColor color, Draft draft, int dominoId) {
        //Draft draft = getNextDraft(nextDraft, game);
        game = KingdominoApplication.getKingdomino().getCurrentGame();

        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        game.setNumberOfPlayers(4);
        kingdomino.setCurrentGame(game);
    
        game.setNextPlayer(game.getPlayer(0));
        KingdominoApplication.setKingdomino(kingdomino);
        String name = game.getPlayer(0).getUser().getName();
        GameController.setGrid(name, new Square[81]);

        //Player currentPlayer = null;
        List<Domino> nextDraftDominos = getNextDraftDominos(draft);
        List<DominoSelection> dominoSelection = getDominoSelection(draft);
        List<Player> players = game.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getColor().equals(color)) {
                currentPlayer = players.get(i);
            }
        }
        List<DominoSelection> newDominoSelection = new ArrayList<>();
        for (int i = 0; i < nextDraftDominos.size(); i++) {
            if (nextDraftDominos.get(i).getId() == dominoId) {
                if (nextDraftDominos.get(i).getDominoSelection() == null) {  
                    DominoSelection selection = new DominoSelection(currentPlayer, nextDraftDominos.get(i), draft);
                    currentPlayer.setDominoSelection(selection);
                    draft.addSelectionAt(selection, i);
                    newDominoSelection.add(selection);
                } else {
                    newDominoSelection = dominoSelection;
                    return false;
                }
            } else {
                //newDominoSelection.add(dominoSelection.get(i));
            }
        }
        return true;
    }

    
    private static TerrainType getTerrainType(String terrain) {
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

    private static Domino getdominoByID(int id) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        for (Domino domino : game.getAllDominos()) {
            if (domino.getId() == id) {
                return domino;
            }
        }
        throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
    }

    public static Draft getNextDraft(String nextDraft, Game game) {
        Draft draft = new Draft(DraftStatus.Sorted, game);
        String[] dominoids = nextDraft.split(",");
        for (int i = 0; i < dominoids.length; i++) {
            draft.addIdSortedDomino(getdominoByID(Integer.parseInt(dominoids[i])));
        }
        draft.setDraftStatus(DraftStatus.Sorted);
        return draft;
    }

    public static List<Domino> getNextDraftDominos(Draft draft) {
        List<Domino> nextDraftDominos = draft.getIdSortedDominos();
        return nextDraftDominos;
    }

    public static List<DominoSelection> getDominoSelection(Draft draft) {
        List<DominoSelection> dominoSelection = draft.getSelections();
        return dominoSelection;
    }


    /**
     * Controller implemented for Feature 11: Move Current Domino
     * @param game
     * @param dominoId
     * @return
     * @author Violet Wei
     */
    public static boolean moveCurrentDomino(Game game, Player player, int dominoId) {
        Domino domino = getdominoByID(dominoId);
        Draft draft = game.getCurrentDraft();
        DominoSelection dominoSelection = new DominoSelection(player, domino, draft);
        if (domino.setDominoSelection(null) || draft.removeSelection(dominoSelection)) {
            domino.setStatus(DominoStatus.ErroneouslyPreplaced);
            DominoInKingdom dominoInKingdom = new DominoInKingdom(0, 0, new Kingdom(player), domino);
        }
        return true;
    }

    /**
     * Feature 12: As a player, I wish to evaluate a provisional placement of my current domino in my kingdom
     * by rotating it (clockwise or counter-clockwise).
     * rotationDir 1 for clockwise, -1 for anticlockwise
     * @author Cecilia Jiang
     * @param dominoInKingdom
     * @param rotationDir
     */
    public static void rotateExistingDomino(Castle castle, Square[] grid, List<KingdomTerritory> territories,
                                            DominoInKingdom dominoInKingdom, int rotationDir){
        DominoInKingdom.DirectionKind oldDir = dominoInKingdom.getDirection();
        DominoInKingdom.DirectionKind newDir = findDirAfterRotation(rotationDir,oldDir);
        dominoInKingdom.setDirection(newDir);
        if(VerificationController.verifyGridSize(territories)){
            if(VerificationController.verifyNoOverlapping(castle,grid,dominoInKingdom) &&
                    (VerificationController.verifyCastleAdjacency(castle,dominoInKingdom) ||
                            VerificationController.verifyNeighborAdjacency(castle,grid,dominoInKingdom)))
                dominoInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
            else
                dominoInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
        } else{
            dominoInKingdom.setDirection(oldDir);
        }
    }

    /**
     * Feature 13: As a player, I wish to place my selected domino to my kingdom. If I am satisfied with its placement,
     * and its current position respects the adjacency rules, I wish to finalize the placement.
     * (Actual checks of adjacency conditions are implemented as separate features)
     * @param player
     * @param id which is id of the domino to place
     * @author: Cecilia Jiang
     */
    public static void placeDomino(Player player, int id){
        Domino domino = player.getDominoSelection().getDomino();
        if(domino.getId() == id && domino.getStatus() == DominoStatus.CorrectlyPreplaced){
            domino.setStatus(DominoStatus.PlacedInKingdom);
        } else
            throw new java.lang.IllegalArgumentException("The current domino placement does not respect the " +
                    "adjacency rules");
    }

    private int[] rightTilePositionAfterRotation(int x_right, int y_right, int rotationDir, DominoInKingdom.DirectionKind oldDir){
        int[] pos = new int[2]; //x,y
        switch(oldDir){
            case Left:
                if(rotationDir == 1){
                    pos[0] = x_right + 1;
                    pos[1] = y_right + 1;
                }else if(rotationDir == -1){
                    pos[0] = x_right + 1;
                    pos[1] = y_right - 1;
                }
                break;
            case Up:
                if(rotationDir == 1){
                    pos[0] = x_right + 1;
                    pos[1] = y_right - 1;
                }else if(rotationDir == -1){
                    pos[0] = x_right - 1;
                    pos[1] = y_right - 1;
                }
                break;
            case Down:
                if(rotationDir == 1){
                    pos[0] = x_right - 1;
                    pos[1] = y_right + 1;
                }else if(rotationDir == -1){
                    pos[0] = x_right + 1;
                    pos[1] = y_right - 1;
                }
                break;
            case Right:
                if(rotationDir == 1){
                    pos[0] = x_right - 1;
                    pos[1] = y_right - 1;
                }else if(rotationDir == -1){
                    pos[0] = x_right - 1;
                    pos[1] = y_right + 1;
                }
                break;
        }
        return pos;
    }

    private static DominoInKingdom.DirectionKind findDirAfterRotation(int rotationDir, DominoInKingdom.DirectionKind oldDir){
        DominoInKingdom.DirectionKind dir = DominoInKingdom.DirectionKind.Up;
        switch(oldDir){
            case Left:
                if(rotationDir == 1){
                    dir = DominoInKingdom.DirectionKind.Up;
                }else if(rotationDir == -1){
                    dir = DominoInKingdom.DirectionKind.Down;
                }
                break;
            case Up:
                if(rotationDir == 1){
                    dir = DominoInKingdom.DirectionKind.Right;
                }else if(rotationDir == -1){
                    dir = DominoInKingdom.DirectionKind.Left;
                }
                break;
            case Down:
                if(rotationDir == 1){
                    dir = DominoInKingdom.DirectionKind.Left;
                }else if(rotationDir == -1){
                    dir = DominoInKingdom.DirectionKind.Right;
                }
                break;
            case Right:
                if(rotationDir == 1){
                    dir = DominoInKingdom.DirectionKind.Down;
                }else if(rotationDir == -1){
                    dir = DominoInKingdom.DirectionKind.Up;
                }
                break;
        }
        return dir;
    }

}