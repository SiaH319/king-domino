package ca.mcgill.ecse223.kingdomino.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

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
 * This class corresponds to the controller methods that will be used for
 * the Load Game feature and the Save Game feature for the Kingdomino Game
 * 
 * @author Violet Wei
 */
public class SaveLoadGameController {

    public SaveLoadGameController() {
    }

    /**
     * Controller implemented for Feature 7: Save Game
     * @param fileName
     * @return
     * @author Violet Wei
     */
    public static boolean saveGame(String fileName) {
        return true;
    }

    /**
     * Controller implemented for Feature 6: Load Game
     * @param fileName
     * @return
     * @author Violet Wei
     */
    public static boolean loadGame(String fileName) {
        return true;
    }
    
}