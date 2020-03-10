package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;

import java.util.List;

/***
*This controller class contains methods to get domino of a kingdom by its id number & to get a castle by kingdom.
*
*@author Cecilia
***/

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

    public static Castle getCastle (Kingdom kingdom) {
        for(KingdomTerritory territory: kingdom.getTerritories()){
            if(territory instanceof Castle )
                return (Castle)territory;
        }
        return null;
    }
}
