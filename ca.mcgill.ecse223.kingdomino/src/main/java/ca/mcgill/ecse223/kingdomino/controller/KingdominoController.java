package ca.mcgill.ecse223.kingdomino.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;

/**
 * This class corresponds to the Kingdomino controller methods
 * 
 * @author Violet Wei & Cecilia Jiang
 */
public class KingdominoController<priavte> {

    public KingdominoController() {

    }

    /**
     * Retrieve a list of TOUser from kingdomino's complete list of users
     * @author Cecilia Jiang
     * @return A list of TOUser
     */
    public static List<TOUser> getAllTOUsers(){
        List<TOUser> TOUsers = new ArrayList<>();
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();

        for(User user: kingdomino.getUsers()){
            TOUser TOUsertmp = new TOUser(user.getName(),user.getPlayedGames(),user.getWonGames());
            TOUsers.add(TOUsertmp);
        }
        return TOUsers;
    }

    public static List<TODomino> getAllTODominoInCurrentDraft(){
        Draft draft = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft();
        List<TODomino> TODominos = new ArrayList<>();
        for(Domino domino: draft.getIdSortedDominos()){
            TODomino TODominotmp = new TODomino(domino.getId(),getStringFromTerrainType(domino.getLeftTile())
                    ,getStringFromTerrainType(domino.getRightTile()),domino.getLeftCrown(),domino.getRightCrown());
            TODominos.add(TODominotmp);
        }
        return TODominos;
    }

    public static TOPlayer getASelectedDominosPlayer(int id){
        Domino domino = KingdominoApplication.getKingdomino().getCurrentGame().getAllDomino(id);
        DominoSelection dominoSelection = domino.getDominoSelection();
        if(dominoSelection!=null){
            Player player = dominoSelection.getPlayer();

        }
        return null;
    }

    private static String getStringFromTerrainType(TerrainType terrainType){
        String result;
        switch(terrainType){
            case WheatField:
                result = "Wheat";
                break;
            case Mountain:
                result = "Mountain";
                break;
            case Lake:
                result = "Lake";
                break;
            case Forest:
                result = "Forest";
                break;
            case Grass:
                result = "Grass";
                break;
            case Swamp:
                result = "Swamp";
                break;
            default:
                result = "/";
                break;
        }
        return result;
    }

}
