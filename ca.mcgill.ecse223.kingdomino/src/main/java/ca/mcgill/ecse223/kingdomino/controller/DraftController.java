package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;

import java.util.ArrayList;
import java.util.Collections;

public class DraftController {
    /////////////////////////////        //////
    ////////////QueryMethods////        //////
    ///////////////////////////        //////
    /**
     * Tells if there can be more drafts created
     * @param game
     * @return true if you can create more drafts, false otherwise
     */
    public static boolean thereCanBeMoreDrafts(Game game) {
        int numberOfDraftsCreated=game.getAllDrafts().size();
        int numberOfPlayers=game.getNumberOfPlayers();
        if(((numberOfDraftsCreated==12) && (numberOfPlayers==3 || numberOfPlayers==4)) || ((numberOfDraftsCreated==6) && (numberOfPlayers==2))) {
            return false;
        }
        else {
            return true;
        }
    }


    /////////////////////////////        //////
    ///// ///Feature Methods////        //////
    ///////////////////////////        //////
    /**
     * Feature 9: Create Next Draft
     * Create a next draft and do the necessary changes.
     *
     */
    public static void createNewDraftIsInitiated() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        if(game.getNextDraft()==null) {
            System.out.println(game.getAllDrafts().size()-1);
            game.setNextDraft(game.getAllDraft(game.getAllDrafts().size()-1));
        }
        game.setCurrentDraft(game.getNextDraft());

        if(thereCanBeMoreDrafts(game)) {
            Draft newNextDraft = new Draft(Draft.DraftStatus.FaceDown,game);

            for(int i=0;i<newNextDraft.maximumNumberOfIdSortedDominos();i++) {
                Domino Top =game.getTopDominoInPile();
                newNextDraft.addIdSortedDomino(Top);
                Top.setStatus(Domino.DominoStatus.InNextDraft);
                game.setTopDominoInPile(Top.getNextDomino());
            }
            game.getCurrentDraft().setDraftStatus(Draft.DraftStatus.FaceUp);
            game.addAllDraft(newNextDraft);
            game.setNextDraft(newNextDraft);
        }
        else {
            game.setNextDraft(null);
            game.setTopDominoInPile(null);
        }

    }

    /**
     *
     * Method that orders the dominoes in the nextDraft.
     *
     */
    public static void orderInitiated() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        Draft nextDraft =game.getNextDraft();
        ArrayList<Integer> listIDs = new ArrayList<Integer>();
        for(Domino domino : nextDraft.getIdSortedDominos()) {

            listIDs.add(domino.getId());
        }
        Collections.sort(listIDs);

        ArrayList<Domino> newIdSorted = new ArrayList<Domino>();
        for(Integer id : listIDs) {

            newIdSorted.add(getdominoByID(id));
        }
        nextDraft.setIdSortedDominos(newIdSorted.get(0),newIdSorted.get(1),newIdSorted.get(2),newIdSorted.get(3));
        nextDraft.setDraftStatus(Draft.DraftStatus.Sorted);
        game.setNextDraft(nextDraft);
    }

    /**
     *
     * Reveal the draft flips the dominoes up
     *
     */
    public static void revealInitiated() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        Draft nextDraft =game.getNextDraft();
        nextDraft.setDraftStatus(Draft.DraftStatus.FaceUp);
    }

    /////////////////////////////        //////
    ///// ///Helper Methods/////        //////
    ///////////////////////////        //////
    private static Domino getdominoByID(int id) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        for (Domino domino : game.getAllDominos()) {
            if (domino.getId() == id) {
                return domino;
            }
        }
        throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
    }
}
