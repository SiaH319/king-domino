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
     * @author Mohamad, Cecilia Jiang
     * 
     */
    public static void createNewDraftIsInitiated() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        int curDraftSize = game.getAllDrafts().size();
        if(curDraftSize == 1 && game.getNextDraft() == null){
            game.setNextDraft(game.getAllDraft(0));
        }else{
            Domino tmp = game.getTopDominoInPile();
            Draft formerNextDraft = game.getNextDraft();
            game.setCurrentDraft(formerNextDraft);
            int step;
            if(game.getNumberOfPlayers() % 2== 0){
                step =4;
            }else{
                step = 3;
            }
            if (tmp != null && curDraftSize != game.getMaxPileSize()) {
                Draft nextDraft = new Draft(Draft.DraftStatus.FaceDown,game);
                for(int i = 0 ;i<step;i++){
                    tmp.setStatus(Domino.DominoStatus.InNextDraft);
                    nextDraft.addIdSortedDomino(tmp);
                    tmp = tmp.getNextDomino();
                    game.setTopDominoInPile(tmp);
                }
                game.setNextDraft(nextDraft);
                game.addAllDraft(nextDraft);
            }else{
                game.setNextDraft(null);
            }

        }

    }

    /**
     * Feature 9
     * Method that orders the dominoes in the nextDraft by sorting the dominoes in the draft
     * @author Mohamad
     *
     */
    public static void orderNewDraftInitiated() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        Draft nextDraft =game.getNextDraft();
        ArrayList<Integer> listIDs = new ArrayList<Integer>();
        if(game.getNextDraft()!=null) {
        	for(Domino domino : nextDraft.getIdSortedDominos()) {

        		listIDs.add(domino.getId()); // add the ids of the draft to the list of ids
        	}
        }
        
        Collections.sort(listIDs);// sort the ids

        ArrayList<Domino> newIdSorted = new ArrayList<Domino>();
        for(Integer id : listIDs) {
            newIdSorted.add(getdominoByID(id));
        }
        nextDraft.setIdSortedDominos(newIdSorted.get(0),newIdSorted.get(1),newIdSorted.get(2),newIdSorted.get(3));// add them back to the next draft 
        nextDraft.setDraftStatus(Draft.DraftStatus.Sorted);														  //in the right order
        //game.setNextDraft(nextDraft);
    }

    /**
     * Feature 9
     * Reveal the draft flips the dominoes up
     * @author Mohamad
     */
    public static void revealDominoesInitiated() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        Draft nextDraft =game.getNextDraft();
        if(nextDraft == null)
            System.out.println("revealing the next draft");
        nextDraft.setDraftStatus(Draft.DraftStatus.FaceUp);// flip up the dominoes
    }
    /**
     * 
     * once all selections have been made we have to make the next draft the current draft
     * @author Mohamad
     */
    public static void switchDraft() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        game.setCurrentDraft(game.getNextDraft());
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
