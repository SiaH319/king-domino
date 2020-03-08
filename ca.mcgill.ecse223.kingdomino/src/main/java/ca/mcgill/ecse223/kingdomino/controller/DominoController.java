package ca.mcgill.ecse223.kingdomino.controller;

import java.io.*;
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;

/**
 * This class implements the controller methods for Domino
 * @author Violet
 */
public class DominoController {

    Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    
    /**
     * Controller implemented for Feature 10: Choose Next Domino
     * @param game
     * @return true if selection for next draft is different
     *         false if selection for next draft remains same
     * @author Violet Wei
     */
    public static boolean chooseNextDomino(Game game, PlayerColor color, String nextDraft, int dominoId) {
        Draft draft = getNextDraft(nextDraft, game);
        List<Domino> nextDraftDominos = getNextDraftDominos(draft);
        List<DominoSelection> dominoSelection = getDominoSelection(draft);
        List<Player> players = game.getPlayers();
        Player currentPlayer = new Player(game);
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
                    draft.addSelectionAt(selection, i);
                    newDominoSelection.add(i, selection);
                } else {
                    newDominoSelection = dominoSelection;
                    return false;
                }
            } else {
                newDominoSelection.add(i, dominoSelection.get(i));
            }
        }
        return true;
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


}