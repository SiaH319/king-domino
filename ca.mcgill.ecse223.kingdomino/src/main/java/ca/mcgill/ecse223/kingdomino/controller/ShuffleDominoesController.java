package ca.mcgill.ecse223.kingdomino.controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ca.mcgill.ecse223.kingdomino.controller.CalculationController;
import ca.mcgill.ecse223.kingdomino.controller.DisjointSet;
import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Square;
import ca.mcgill.ecse223.kingdomino.controller.VerificationController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;

public class ShuffleDominoesController {
	/**
	 * Feature 5 Shuffle Dominoes
	 * @author Cecilia Jiang, Mohammad Dimassi
	 * @param dominoes, game's list of dominoes
	 * @param game, currentGame
	 * @throws Exception
	 */
    public static void shuffleDomino (List<Domino> dominoes, Game game) throws Exception {
        List <Domino> shuffledDominos = new ArrayList<Domino>();
        shuffledDominos.addAll(dominoes);
        Collections.shuffle(shuffledDominos);
        int playerNum = game.getNumberOfPlayers();
        if(game.numberOfPlayers() % 2 ==0){
        	int counter = 0;
            for( int i=0; i< 3 * playerNum; i++) {

                    Draft drafttmp = new Draft(DraftStatus.FaceDown, game);
                    for (int j = 0; j < 4; j++) {
                        Domino curDomino = shuffledDominos.get(counter);
                        while (curDomino.getStatus() == DominoStatus.Excluded) {
                            counter++;
                            curDomino = shuffledDominos.get(counter);
                        }
                        Domino realDomino = getdominoByID(curDomino.getId());
                        realDomino.setStatus(DominoStatus.InPile);
                        counter++;
                        boolean result = drafttmp.addIdSortedDomino(realDomino);
                        if (!result) throw new Exception("Shuffle Domino Failed");
                    }

            }
        }else{
        	int counter = 0;
            for( int i=0; i< 12; i++) {
                Draft drafttmp = new Draft(DraftStatus.FaceDown, game);
                for (int j = 0; j < 3; j++) {
                	Domino curDomino = shuffledDominos.get(counter);
                    while(curDomino.getStatus() == DominoStatus.Excluded) {
                    	counter++;
                    	curDomino = shuffledDominos.get(counter);
                    }
                    Domino realDomino = getdominoByID(curDomino.getId());
                    realDomino.setStatus(DominoStatus.InPile);
                    counter++;
                    boolean result = drafttmp.addIdSortedDomino(realDomino);
                    if (!result) throw new Exception("Shuffle Domino Failed");
                }
            }
        }


        Draft firstDraft = game.getAllDrafts().get(0);

        ArrayList<Integer> listIDs = new ArrayList<Integer>();
        if(firstDraft!=null) {
            for(Domino domino : firstDraft.getIdSortedDominos()) {

                listIDs.add(domino.getId()); // add the ids of the draft to the list of ids
            }
        }

        Collections.sort(listIDs);// sort the ids

        ArrayList<Domino> newIdSorted = new ArrayList<Domino>();
        for(Integer id : listIDs) {
            newIdSorted.add(getdominoByID(id));
        }
        assert firstDraft != null;
        if(game.getNumberOfPlayers()%2==0) {
            firstDraft.setIdSortedDominos(newIdSorted.get(0), newIdSorted.get(1), newIdSorted.get(2), newIdSorted.get(3));// add them back to the next draft
            firstDraft.setDraftStatus(Draft.DraftStatus.Sorted);
        }else{
            firstDraft.setIdSortedDominos(newIdSorted.get(0), newIdSorted.get(1), newIdSorted.get(2));// add them back to the next draft
            firstDraft.setDraftStatus(Draft.DraftStatus.Sorted);
        }
        for(Domino domino1: firstDraft.getIdSortedDominos()){
            domino1.setStatus(DominoStatus.InCurrentDraft);
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

    public static void fixedArrangement (String string, Game game) {

        String[] dominoIds = string.split(", ");

        List<Domino> tmp = new ArrayList<>();
        for(int i = 0; i< dominoIds.length; i++){
            int id = Integer.parseInt(dominoIds[i]);
            Domino domino = getdominoByID(id);
            domino.setStatus(DominoStatus.InPile);
            tmp.add(domino);
        }

        if(game.numberOfPlayers() % 2 ==0){
            for(int i = 0; i< dominoIds.length / 4; i++){
                Draft drafttmp = new Draft(DraftStatus.FaceDown, game);
                for(int j = 0; j < 4; j++){
                    Domino d = tmp.get(4 * i + j);
                    drafttmp.addIdSortedDomino(d);
                }
            }
        }else{
            for(int i = 0; i< dominoIds.length / 3; i++){
                Draft drafttmp = new Draft(DraftStatus.FaceDown, game);
                for(int j = 0; j < 3; j++){
                    Domino d = tmp.get(3 * i + j);
                    drafttmp.addIdSortedDomino(d);
                }
            }
        }

        //Create first Draft
        Draft firstDraft = game.getAllDrafts().get(0);

        ArrayList<Integer> listIDs = new ArrayList<Integer>();
        if(firstDraft!=null) {
            for(Domino domino : firstDraft.getIdSortedDominos()) {

                listIDs.add(domino.getId()); // add the ids of the draft to the list of ids
            }
        }

        Collections.sort(listIDs);// sort the ids

        ArrayList<Domino> newIdSorted = new ArrayList<Domino>();
        for(Integer id : listIDs) {
            newIdSorted.add(getdominoByID(id));
        }
        assert firstDraft != null;
        if(game.getNumberOfPlayers()%2==0) {
            firstDraft.setIdSortedDominos(newIdSorted.get(0), newIdSorted.get(1), newIdSorted.get(2), newIdSorted.get(3));// add them back to the next draft
            firstDraft.setDraftStatus(Draft.DraftStatus.Sorted);
        }else{
            firstDraft.setIdSortedDominos(newIdSorted.get(0), newIdSorted.get(1), newIdSorted.get(2));// add them back to the next draft
            firstDraft.setDraftStatus(Draft.DraftStatus.Sorted);
        }
        for(Domino domino1: firstDraft.getIdSortedDominos())
            domino1.setStatus(DominoStatus.InCurrentDraft);


    }
}