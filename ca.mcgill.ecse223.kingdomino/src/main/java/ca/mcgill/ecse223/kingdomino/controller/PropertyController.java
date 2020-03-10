package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PropertyController {
    /////////////////////////////        //////
    ////////////QueryMethods////        //////
    ///////////////////////////        //////
    /**
     * Get all domino ids from a single Property
     * @param p
     * @return int array containing dominoIds sorted in ascending order
     */
    public static int[] getDominoIdsFromProperty(Property p){
        List<Domino> dominoes= p.getIncludedDominos();
        List<Domino> copy = new ArrayList<>();
        for(Domino domino: dominoes)
            copy.add(domino);

        copy.sort(new SortbyDominoId());
        int[] arr = new int[copy.size()];
        for(int i = 0;i < arr.length; i++){
            arr[i] = copy.get(i).getId();
        }
        return arr;
    }
    static class SortbyDominoId implements Comparator<Domino>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Domino a, Domino b)
        {
            return a.getId() - b.getId();
        }
    }

    public List<Domino> getIncludedDominos(Property p) {
        return p.getIncludedDominos();
    }
}
