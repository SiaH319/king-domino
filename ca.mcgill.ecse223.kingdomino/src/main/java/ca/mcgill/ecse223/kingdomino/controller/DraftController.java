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
     * @author Mohamad
     * @param game
     * @return true if you can create more drafts, false otherwise
     */
    public static boolean thereCanBeMoreDrafts(Game game) {
        int numberOfDraftsCreated=game.getAllDrafts().size();
        int numberOfPlayers=game.getNumberOfPlayers();
        if(((numberOfDraftsCreated==12) && (numberOfPlayers==3 || numberOfPlayers==4)) || ((numberOfDraftsCreated==6) && (numberOfPlayers==2))) {
            return false; // checks arithmatically if there can still be more drafts
        }
        else {
            return true;
        }
    }


    /////////////////////////////        //////
    ///// ///Feature Methods////        //////
    ///////////////////////////        //////
    /**
     * Feature 8: Create Next Draft
     * Create a next draft and the first few dominoes in the pile to it
     * @author Mohamad
     * 
     */
    public static void createNewDraftIsInitiated() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        if(game.getNextDraft()==null) {// if next draft is not there then set  one
            
            game.setNextDraft(game.getAllDraft(game.getAllDrafts().size()-1)); //last draft is the newest so the next one
        }
        game.setCurrentDraft(game.getNextDraft()); // now set it as the new next Draft

        if(thereCanBeMoreDrafts(game)) { // if we can create more drafts
            Draft newNextDraft = new Draft(Draft.DraftStatus.FaceDown,game);

            for(int i=0;i<newNextDraft.maximumNumberOfIdSortedDominos();i++) { // populate the draft with the corresponding number of dominoes from the linked list
                Domino Top =game.getTopDominoInPile();
                newNextDraft.addIdSortedDomino(Top);
                Top.setStatus(Domino.DominoStatus.InNextDraft);// update the linked ist
                game.setTopDominoInPile(Top.getNextDomino());
            }
            game.getCurrentDraft().setDraftStatus(Draft.DraftStatus.FaceUp);
            game.addAllDraft(newNextDraft);
            game.setNextDraft(newNextDraft);
        }
        else {
            game.setNextDraft(null);
            game.setTopDominoInPile(null); // then the pile should be empty
        }

    }

    /**
     * Feature 9
     * Method that orders the dominoes in the nextDraft by sorting the dominoes in the draft
     * @author Mohamad
     *
     */
    public static void orderNewDraftInitiated() {
    	System.out.println("in the draft controller");
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        Draft nextDraft =game.getNextDraft();
        ArrayList<Integer> listIDs = new ArrayList<Integer>();
        for(Domino domino : nextDraft.getIdSortedDominos()) {

            listIDs.add(domino.getId()); // add the ids of the draft
        }
        Collections.sort(listIDs);// sort the ids

        ArrayList<Domino> newIdSorted = new ArrayList<Domino>();
        for(Integer id : listIDs) {

            newIdSorted.add(getdominoByID(id));
        }
        nextDraft.setIdSortedDominos(newIdSorted.get(0),newIdSorted.get(1),newIdSorted.get(2),newIdSorted.get(3));// add them back to the next draft
        nextDraft.setDraftStatus(Draft.DraftStatus.Sorted);
        game.setNextDraft(nextDraft);
    }

    /**
     * Feature 9
     * Reveal the draft flips the dominoes up
     * @author Mohamad
     */
    public static void revealDominoesInitiated() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        Draft nextDraft =game.getNextDraft();
        nextDraft.setDraftStatus(Draft.DraftStatus.FaceUp);// flip up the dominoes
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
