package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;

import java.util.List;

public class KingdomController {
    /////////////////////////////        //////
    ////////////QueryMethods////        //////
    ///////////////////////////        //////
    public static DominoInKingdom getDominoInKingdomByDominoId(int id, Kingdom kingdom){
        List<KingdomTerritory> kingdomTerritoryList = kingdom.getTerritories();
        for(KingdomTerritory territory: kingdomTerritoryList){
            if(territory instanceof DominoInKingdom && ((DominoInKingdom) territory).getDomino().getId() == id)
                return (DominoInKingdom)territory;
        }
        return null;
    }

}
