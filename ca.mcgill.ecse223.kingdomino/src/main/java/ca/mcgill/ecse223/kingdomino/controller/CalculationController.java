package ca.mcgill.ecse223.kingdomino.controller;

import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;


/**
 * @author Violet
 */
public class CalculationController {

    private Game game;

    public static void identifyPropertoes(DisjointSet s, Square[] grid, Kingdom kingdom){
        int pari,countsets=0;
        ArrayList<Properties> properties = new ArrayList<Properties>();
        Boolean[] arr = new Boolean[s.parArraySize()];
        Arrays.fill(arr, false);


        String[] setstrings = new String[s.parArraySize()];
        /* build string for each set */
        for (int i=0; i<s.parArraySize(); i++) {
            pari = s.find(i);
            if(grid[i].getTerrain()!=null){
                if (setstrings[pari]==null) {
                    setstrings[pari] = String.valueOf(i);
                    countsets+=1;
                } else {
                    setstrings[pari] += "," + i;

                }
            }
        }
        /* Add Properties */
        for (int i=0; i<s.parArraySize(); i++) {
            if (setstrings[i] != null) {
                Property p = new Property(kingdom);
                String[] containedSquareIndexes = setstrings[i].split(",");
                int p_Crown = 0;
                int p_size = 0;
                for(String index: containedSquareIndexes){
                    int squareIndex = Integer.parseInt(index);
                    p_Crown += grid[squareIndex].getCrown();
                  p_size++;
                    p.setPropertyType(grid[squareIndex].getTerrain());
                    int id = grid[squareIndex].getDominoId();
                    p.addIncludedDomino(getdominoByID(id));
                }
                p.setCrowns(p_Crown);
                p.setSize(p_size);
            }
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
}